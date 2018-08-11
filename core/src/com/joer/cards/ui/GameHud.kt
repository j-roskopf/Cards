package com.joer.cards.ui

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.utils.Disposable
import com.joer.cards.CardGame
import com.joer.cards.InventoryManager
import com.joer.cards.config.Config
import com.joer.cards.models.Item.Companion.spriteHeight
import com.joer.cards.models.Item.Companion.spriteWidth
import com.joer.cards.models.items.Potion
import com.joer.cards.models.items.SpellBook
import javax.inject.Inject

class GameHud @Inject constructor(private val batch: SpriteBatch, atlas: TextureAtlas): Disposable {

    @Inject lateinit var inventoryManager: InventoryManager
    @Inject lateinit var inventoryHud: InventoryHud

    private val font: BitmapFont = BitmapFont()
    private var cam: OrthographicCamera? = null
    private var textureRegion: TextureRegion


    companion object {
        internal var goldMargin = 16
        internal var goldAmountLayout = GlyphLayout()
    }

    init {
        CardGame.component.inject(this)

        textureRegion = TextureRegion(atlas.findRegion("rpg_items"),spriteWidth * 15,  spriteHeight * 6, spriteWidth, spriteHeight)
    }

    fun resize(screenWidth: Int, screenHeight: Int) {
        cam = OrthographicCamera(screenWidth.toFloat(), screenHeight.toFloat())
        cam!!.translate((screenWidth / 2).toFloat(), (screenHeight / 2).toFloat())
        cam!!.update()
        batch.projectionMatrix = cam!!.combined
    }

    fun update() {
        inventoryManager.items.forEachIndexed { index, item ->
            val itemX = (Config.GAME_WIDTH - spriteWidth.toFloat() - goldAmountLayout.width - goldMargin) - ((index + 1) * spriteWidth)
            val itemY = (Config.GAME_HEIGHT - 50).toFloat()

            item.x = itemX
            item.y = itemY
        }
    }

    fun render(delta: Float) {
        batch.begin()
        goldAmountLayout.setText(font, inventoryManager.goldAmount.toString())

        font.draw(batch, inventoryManager.goldAmount.toString(), (Config.GAME_WIDTH - goldAmountLayout.width - goldMargin), (Config.GAME_HEIGHT - (spriteHeight / 2) + 4).toFloat())
        batch.draw(textureRegion, (Config.GAME_WIDTH - spriteWidth.toFloat() - goldAmountLayout.width - goldMargin), (Config.GAME_HEIGHT - 50).toFloat(), spriteWidth.toFloat(), spriteHeight.toFloat())

        inventoryManager.items.forEachIndexed { index, item ->
            item.draw(batch)

            if(item.turnsActive > 0) {
                font.draw(batch, item.turnsActive.toString(), item.x, item.y)
            } else {
               when(item) {
                   is SpellBook -> {
                       font.draw(batch, item.damage.toString(), item.x, item.y)
                   }
                   is Potion -> {
                       font.draw(batch, item.health.toString(), item.x, item.y)
                   }
               }
            }
        }

        batch.end()
    }

    override fun dispose() {
        font.dispose()
        batch.dispose()
    }
}