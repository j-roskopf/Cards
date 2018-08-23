package com.joer.cards.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.joer.cards.CardGame
import com.joer.cards.config.Config
import javax.inject.Inject

class GameOverScreen @Inject constructor() : Screen, InputAdapter() {

    private var viewport: Viewport

    @Inject
    lateinit var camera: OrthographicCamera

    init {
        CardGame.component.inject(this)

        viewport = FitViewport(Config.GAME_WIDTH, Config.GAME_HEIGHT, camera)

        camera.position.set(viewport.worldWidth / 2f, viewport.worldHeight / 2f, 0f)
        camera.update()
    }

    override fun hide() {

    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
    }


}