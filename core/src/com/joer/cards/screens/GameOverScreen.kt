package com.joer.cards.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.joer.cards.CardGame
import com.joer.cards.config.Config
import javax.inject.Inject



class GameOverScreen @Inject constructor(
        private val spriteBatch: SpriteBatch,
        private val assetManager: AssetManager,
        private val cardGame: CardGame) : Screen, InputAdapter() {

    @Inject
    lateinit var camera: OrthographicCamera

    private var viewport: Viewport

    private var stage: Stage

    companion object {
        private val BUTTON_WIDTH = 300f
        private val BUTTON_HEIGHT = 60f
    }

    init {
        CardGame.component.inject(this)

        viewport = FitViewport(Config.GAME_WIDTH, Config.GAME_HEIGHT, camera)

        stage = Stage(viewport, spriteBatch)

        camera.position.set(viewport.worldWidth / 2f, viewport.worldHeight / 2f, 0f)
        camera.update()

        stage = Stage()

        createLayout()

        Gdx.input.inputProcessor = stage
    }

    private fun createLayout() {
        val table = Table()
        table.setFillParent(true)

        val welcomeLabel = Label("Game over fucko!", getSkin())
        welcomeLabel.setAlignment(Align.center)
        table.top().add(welcomeLabel).pad(8f)

        table.row()

        val startGameButton = TextButton("Start game", getSkin())
        startGameButton.addListener(object: ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Gdx.app.log("D","clicked")
                cardGame.screen = PlayScreen(cardGame)
            }
        })
        table.center().add(startGameButton).height(BUTTON_HEIGHT).width(BUTTON_WIDTH)

        stage.addActor(table)
    }

    override fun hide() {
        Gdx.app.log("D","hide")
    }

    override fun show() {
        Gdx.app.log("D","show")
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        camera.update()
    }

    private fun getSkin(): Skin? {
        return assetManager.get("skins/uiskin.json", Skin::class.java)
    }

    override fun dispose() {
    }
}