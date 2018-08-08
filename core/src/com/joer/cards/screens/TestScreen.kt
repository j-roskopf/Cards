package com.joer.cards.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup




class TestScreen() : Screen {

    private var batch: SpriteBatch? = null
    private var skin: Skin? = null
    private var stage: Stage? = null

    init {
        batch = SpriteBatch()
        skin = Skin(Gdx.files.internal("skin/test.json"))
        stage = Stage()

        val vg = VerticalGroup()
        vg.setFillParent(true)
        val resumeButton = TextButton("Resume", skin)
        val resumeButton1 = TextButton("Resume1", skin)
        val resumeButton2 = TextButton("Resume2", skin)
        vg.addActor(resumeButton)
        vg.addActor(resumeButton2)
        vg.addActor(resumeButton1)

        stage?.addActor(vg)
    }
    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch?.begin()
        stage?.draw()
        batch?.end()
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