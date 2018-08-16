package com.joer.cards.screens

import com.badlogic.gdx.*
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.joer.cards.CardGame
import com.joer.cards.config.Config
import com.joer.cards.managers.CardManager
import com.joer.cards.managers.InputManager
import com.joer.cards.managers.InventoryManager
import com.joer.cards.ui.CardHud
import com.joer.cards.ui.FXManager
import com.joer.cards.ui.GameHud
import com.joer.cards.ui.InventoryHud
import com.joer.cards.ui.inventory.Inventory
import com.joer.cards.util.FrameRate
import javax.inject.Inject

class PlayScreen @Inject constructor() : Screen, InputAdapter() {

    private lateinit var viewport: Viewport

    @Inject lateinit var frameRate: FrameRate
    @Inject lateinit var gameHud: GameHud
    @Inject lateinit var inputManager: InputManager
    @Inject lateinit var inventoryHud: InventoryHud
    @Inject lateinit var inventoryManager: InventoryManager
    @Inject lateinit var fxManager: FXManager
    @Inject lateinit var spriteBatch: SpriteBatch
    @Inject lateinit var camera: OrthographicCamera
    @Inject lateinit var cardManager: CardManager
    @Inject lateinit var inventory: Inventory
    @Inject lateinit var logger: ApplicationLogger
    @Inject lateinit var cardHud: CardHud

    private var inputMultiplexer = InputMultiplexer()

    companion object {
        internal var showInventory = false
    }

    init {
        CardGame.component.inject(this)

        viewport = FitViewport(Config.GAME_WIDTH, Config.GAME_HEIGHT, camera)

        camera.position.set(viewport.worldWidth / 2f, viewport.worldHeight / 2f, 0f)
        camera.update()

        cardManager.createInitialCards()

        inputMultiplexer.addProcessor(this)
        inputMultiplexer.addProcessor(inventory.stage)

        Gdx.input.inputProcessor = inputMultiplexer
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if(showInventory) {
            inventory.render(delta)
        } else {

            update(delta)

            camera.update()
            spriteBatch.projectionMatrix = camera.combined

            frameRate.render()
            gameHud.render(delta)

            inventoryHud.draw()

            spriteBatch.begin()

            for((_, value) in cardManager.cards) {
                value.draw(spriteBatch, delta)
            }

            fxManager.draw(delta)

            spriteBatch.end()

            cardHud.drawBorder()
        }
    }

    private fun update(delta: Float) {
        if(showInventory) {

        } else {
            gameHud.update()

            inventoryManager.update()

            spriteBatch.projectionMatrix = camera.combined

            cardManager.update(delta)

            for((_, value) in cardManager.cards) {
                value.update(delta)
            }

            fxManager.update(delta)

            frameRate.update()
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        gameHud.resize(width, height)
        frameRate.resize(width, height)
        viewport.update(width, height)
        inventory.stage.viewport.update(width, height)
        camera.update()
    }

    override fun dispose() {

    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val touchPos = Vector3(screenX.toFloat(), screenY.toFloat(), 0f)
        viewport.unproject(touchPos)

        return if(showInventory) {
            false
        } else {
            inputManager.detectClickedCard(touchPos.x, touchPos.y, pointer, button)
            gameHud.dispatchClick(touchPos.x, touchPos.y)
            true
        }
    }

    override fun keyUp(keycode: Int): Boolean {
        if(keycode == LEFT || keycode == RIGHT || keycode == UP || keycode == DOWN) {
            return if(showInventory) {
                false
            } else {
                inputManager.handleKeyboard(keycode)
                true
            }
        }

        if(keycode == I) {
            showInventory = !showInventory
            inventory.initialize()
        }

        return super.keyUp(keycode)
    }

}