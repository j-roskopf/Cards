package com.joer.cards.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.joer.cards.CardGame
import com.joer.cards.config.Config
import com.joer.cards.managers.InventoryManager
import com.joer.cards.models.Item.Companion.spriteHeight
import com.joer.cards.models.Item.Companion.spriteWidth
import com.joer.cards.models.TURNS_ACTIVE
import com.joer.cards.models.items.SpellBook
import com.joer.cards.models.items.inventory.Chest
import com.joer.cards.models.items.inventory.CloseButton
import com.joer.cards.models.items.inventory.InventoryGoldBar
import com.joer.cards.models.items.potion.DamagePotion
import com.joer.cards.models.items.potion.HealthPotion
import com.joer.cards.screens.PlayScreen
import com.joer.cards.ui.inventory.Inventory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameHud @Inject constructor(private val batch: SpriteBatch, atlas: TextureAtlas) : Disposable {

    @Inject
    lateinit var inventoryManager: InventoryManager

    @Inject lateinit var inventory: Inventory

    private val font: BitmapFont = BitmapFont()
    private var cam: OrthographicCamera? = null
    private var selectedItemFrame: TextureRegion
    private var chestItem = Chest(0, 0, atlas)
    private var goldItem = InventoryGoldBar(0, 0, atlas)
    private var closeButton = CloseButton(0, 0, atlas)
    private val selectedItemPosition = Vector2(0f, 0f)
    private val selectedItemBorderPosition = Vector2(0f, 0f)

    companion object {
        internal var goldAmountLayout = GlyphLayout()
        internal const val margin = 8
    }

    init {
        CardGame.component.inject(this)
        selectedItemFrame = TextureRegion(Texture(Gdx.files.internal("item_border.png")), 0, 0, spriteWidth, spriteHeight)

        val goldX = (Config.GAME_WIDTH - spriteWidth.toFloat() - goldAmountLayout.width - margin)
        val goldY = (Config.GAME_HEIGHT - 50)

        goldItem.x = goldX
        goldItem.y = goldY

        val chestX = (Config.GAME_WIDTH - spriteWidth.toFloat() - margin)
        val chestY = (Config.GAME_HEIGHT - spriteHeight * 2) - margin
        chestItem.x = chestX
        chestItem.y = chestY

        selectedItemBorderPosition.set(chestItem.x - spriteWidth - margin, chestItem.y)

        closeButton.x = selectedItemBorderPosition.x - spriteWidth
        closeButton.y = selectedItemBorderPosition.y + margin

        selectedItemPosition.set(chestItem.x + margin / 2 - spriteWidth - margin, chestItem.y + margin / 2)
    }

    fun resize(screenWidth: Int, screenHeight: Int) {
        cam = OrthographicCamera(screenWidth.toFloat(), screenHeight.toFloat())
        cam!!.translate((screenWidth / 2).toFloat(), (screenHeight / 2).toFloat())
        cam!!.update()
        batch.projectionMatrix = cam!!.combined
    }

    fun update() {
        var index = 0

        inventoryManager.items.forEach { item ->
            if(item.turnsActive != TURNS_ACTIVE.FOREVER.getValue()) {
                val itemX = (Config.GAME_WIDTH - margin  - (index + 1) * spriteWidth)
                val itemY = (Config.GAME_HEIGHT - spriteHeight * 3 - margin * 3)
                item.x = itemX
                item.y = itemY

                index++
            }
        }
    }

    fun render(delta: Float) {
        batch.begin()

        drawGold()

        drawItems()

        drawInventoryArea()

        batch.end()
    }

    private fun drawItems() {
        inventoryManager.items.forEach { item ->
            if(item.turnsActive != TURNS_ACTIVE.FOREVER.getValue()) {
                item.draw(batch)

                if(item.turnsActive > 0) {
                    font.draw(batch, item.turnsActive.toString(), item.x, item.y)
                } else {
                    when(item) {
                        is SpellBook -> {
                            font.draw(batch, item.damage.toString(), item.x, item.y)
                        }
                        is HealthPotion -> {
                            font.draw(batch, item.health.toString(), item.x, item.y)
                        }
                        is DamagePotion -> {
                            font.draw(batch, item.health.toString(), item.x, item.y)
                        }
                    }
                }
            }
        }
    }

    private fun drawGold() {
        goldAmountLayout.setText(font, inventoryManager.goldAmount.toString())

        font.draw(batch,
                inventoryManager.goldAmount.toString(),
                goldItem.x - margin  - goldAmountLayout.width,
                (Config.GAME_HEIGHT - (spriteHeight / 2) + 4))

        goldItem.draw(batch)
    }

    private fun drawInventoryArea() {

        //draw chest
        chestItem.draw(batch)

        //draw selected inventory item if it exists
        inventoryManager.selectedItem?.let {

            batch.draw(it.textureRegion,
                    selectedItemPosition.x,
                    selectedItemPosition.y,
                    spriteWidth.toFloat() - margin,
                    spriteHeight.toFloat() - margin)

            //draw close button
            closeButton.draw(batch)

            //draw selected item border
            batch.draw(selectedItemFrame,
                    selectedItemBorderPosition.x,
                    selectedItemBorderPosition.y,
                    spriteWidth.toFloat(), spriteHeight.toFloat())
        }
    }

    override fun dispose() {
        font.dispose()
        batch.dispose()
    }

    fun dispatchClick(x: Float, y: Float) {
        if(closeButton.boundingRectangle.contains(x, y)) {
            inventoryManager.selectedItem = null
        }

        if(chestItem.boundingRectangle.contains(x, y)) {
            inventory.initialize()
            PlayScreen.showInventory = true
        }
    }
}