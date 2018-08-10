package com.joer.cards.screens

import com.badlogic.gdx.ApplicationLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.joer.cards.CardGame
import com.joer.cards.CardManager
import com.joer.cards.InputManager
import com.joer.cards.config.Config
import com.joer.cards.ui.CardHud
import com.joer.cards.ui.FXManager
import com.joer.cards.ui.GameHud
import com.joer.cards.ui.InventoryHud
import com.joer.cards.util.FrameRate
import javax.inject.Inject

class PlayScreen @Inject constructor(private val spriteBatch: SpriteBatch,
                                     private val camera: OrthographicCamera,
                                     private val cardManager: CardManager,
                                     private val logger: ApplicationLogger,
                                     private val cardHud: CardHud) : Screen, InputAdapter() {

    private var viewport: Viewport = FitViewport(Config.GAME_WIDTH, Config.GAME_HEIGHT, camera)

    @Inject lateinit var frameRate: FrameRate
    @Inject lateinit var gameHud: GameHud
    @Inject lateinit var inputManager: InputManager
    @Inject lateinit var inventoryHud: InventoryHud
    @Inject lateinit var fxManager: FXManager

    init {
        CardGame.component.inject(this)

        camera.position.set(viewport.worldWidth / 2f, viewport.worldHeight / 2f, 0f)
        camera.update()

        cardManager.createInitialCards()

        Gdx.input.inputProcessor = this
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

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

    private fun update(delta: Float) {
        frameRate.update()
        gameHud.update()

        spriteBatch.projectionMatrix = camera.combined

        cardManager.update(delta)

        for((_, value) in cardManager.cards) {
            value.update(delta)
        }

        fxManager.update(delta)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        gameHud.resize(width, height)
        viewport.update(width, height)
        camera.update()
    }

    override fun dispose() {

    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val touchPos = Vector3(screenX.toFloat(), screenY.toFloat(), 0f)
        viewport.unproject(touchPos)
        inputManager.detectClickedCard(touchPos.x, touchPos.y, pointer, button)
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        if(keycode == LEFT || keycode == RIGHT || keycode == UP || keycode == DOWN) {
            inputManager.handleKeyboard(keycode)
            return true
        }
        return super.keyUp(keycode)
    }

}