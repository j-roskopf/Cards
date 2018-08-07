package com.joer.cards.screens

import com.badlogic.gdx.ApplicationLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.joer.cards.CardManager
import com.joer.cards.config.Config
import com.joer.cards.models.CardHud
import javax.inject.Inject

class PlayScreen @Inject constructor(private val spriteBatch: SpriteBatch,
                                     private val camera: OrthographicCamera,
                                     private val cardManager: CardManager,
                                     private val logger: ApplicationLogger,
                                     private val cardHud: CardHud) : Screen, InputAdapter() {

    private var viewport: Viewport = FitViewport(Config.GAME_WIDTH, Config.GAME_HEIGHT, camera)

    init {
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

        camera.update()
        spriteBatch.projectionMatrix = camera.combined

        update(delta)

        spriteBatch.begin()

        for((_, value) in cardManager.cards) {
            value?.draw(spriteBatch, delta)
        }

        spriteBatch.end()

        cardHud.drawBorder(cardManager)

    }

    private fun update(delta: Float) {
        spriteBatch.projectionMatrix = camera.combined

        cardManager.update(delta)

        for((_, value) in cardManager.cards) {
            value?.update(delta)
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        camera.update()
    }

    override fun dispose() {

    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val touchPos = Vector3(screenX.toFloat(), screenY.toFloat(), 0f)
        viewport.unproject(touchPos)
        cardManager.detectClickedCard(touchPos.x, touchPos.y, pointer, button)
        return true
    }
}