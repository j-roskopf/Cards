package com.joer.cards.ui.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.joer.cards.config.Config
import com.joer.cards.managers.InventoryManager
import com.joer.cards.models.Item
import com.joer.cards.models.TURNS_ACTIVE
import com.joer.cards.models.entities.Player
import com.joer.cards.models.items.SpellBook
import com.joer.cards.models.items.potion.DamagePotion
import com.joer.cards.models.items.potion.HealthPotion
import com.joer.cards.screens.PlayScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Inventory @Inject constructor(spriteBatch: SpriteBatch,
                                    private val assetManager: AssetManager,
                                    private val inventoryManager: InventoryManager,
                                    private val player: Player) {

    companion object {
        const val INVENTORY_ITEM_WIDTH = 48f
        const val INVENTORY_ITEM_HEIGHT = 48f
    }

    private var orthographicCamera = OrthographicCamera()
    private var viewport: Viewport = FitViewport(Config.GAME_WIDTH, Config.GAME_HEIGHT, orthographicCamera)
    internal var stage: Stage = Stage(viewport, spriteBatch)
    private var table: Table = Table()

    val maxPerRow = 6

    init {
        table.top()
        table.setFillParent(true)

        val skin = assetManager.get("skins/uiskin.json", Skin::class.java)
        createLayout(skin)

        stage.addActor(table)

        table.debug = true
    }

    fun initialize() {

        table = Table()

        table.top()
        table.setFillParent(true)

        val skin = assetManager.get("skins/uiskin.json", Skin::class.java)
        createLayout(skin)

        stage.addActor(table)

        table.debug = true
    }

    private fun createLayout(skin: Skin) {
        val currentTable = Table()
        currentTable.top()

        val closeButton = CloseActor(
                TextureRegion(
                        Texture(Gdx.files.internal("close_button.png")),
                        0,
                        0,
                        INVENTORY_ITEM_WIDTH.toInt(),
                        INVENTORY_ITEM_HEIGHT.toInt()),
                0f,
                Config.GAME_HEIGHT
        )

        closeButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                PlayScreen.showInventory = false
            }
        })

        currentTable.add(closeButton).top().left().pad(16f).colspan(maxPerRow).growX().row()

        var mod: Int

        inventoryManager.items.forEachIndexed { index, item ->
            if(item.turnsActive == TURNS_ACTIVE.FOREVER.getValue()) {
                val itemX = index * INVENTORY_ITEM_WIDTH
                val itemY = INVENTORY_ITEM_HEIGHT

                val itemPosition = Vector3(itemX, itemY, 0f)
                orthographicCamera.unproject(itemPosition)

                val actor = InventoryActor(item.textureRegion, itemX, itemY)
                actor.addListener(object: ClickListener() {
                    override fun clicked(event: InputEvent?, x: Float, y: Float) {
                        if(handleItem(item)) {
                            PlayScreen.showInventory = false
                        }
                    }
                })

                currentTable.add(actor).top().pad(4f)

                mod = index + 1
                if(mod % maxPerRow == 0 && mod > 0) {
                    currentTable.row()
                }
            }
        }

        currentTable.pack()
        currentTable.isTransform = true

        val scrollPane = ScrollPane(currentTable, skin)

        table.add(scrollPane).expandX().fill().expandY().fill()
    }

    fun render(delta: Float) {
        stage.act(delta)
        stage.draw()
    }

    private fun handleItem(item: Item): Boolean {
        var handled = false
        var remove = false

        when(item) {
            is HealthPotion -> {
                player.addAmountToHealth(item.health)
                handled = true
                remove = true
            }
            is DamagePotion -> {
                player.addAmountToAttack(item.damage)
                handled = true
                remove = true
            }
            is SpellBook -> {
                inventoryManager.selectedItem = item
                handled = true
            }
        }

        if(remove) {
            item.markedForRemoval = true
        }

        return handled
    }
}
