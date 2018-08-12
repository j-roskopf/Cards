package com.joer.cards.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.joer.cards.CardGame
import com.joer.cards.config.Config
import java.util.*
import javax.inject.Inject

class InventoryOld @Inject constructor(private val camera: OrthographicCamera): ScreenAdapter() {

    private lateinit var viewport: Viewport

    @Inject lateinit var assetManager: AssetManager

    internal var stage: Stage = Stage()

    internal var inventoryActor: InventoryActor? = null

    init {
        CardGame.component.inject(this)
        viewport = FitViewport(Config.GAME_WIDTH, Config.GAME_HEIGHT, camera)

        Timer().schedule(
                object : java.util.TimerTask() {
                    override fun run() {
                        Gdx.app.log("D","init " + (assetManager.isLoaded("skins/uiskin.json")))

                        val skin = assetManager.get("skins/uiskin.json", Skin::class.java)
                        val dragAndDrop = DragAndDrop()
                        inventoryActor = InventoryActor(Inventory(), dragAndDrop, skin, stage)
                        stage.addActor(inventoryActor)
                    }
                },
                1000
        )
    }

    override fun hide() {
        super.hide()
    }

    override fun show() {
        super.show()
        Gdx.app.log("D","show")

    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
    }

    override fun pause() {
        super.pause()
    }

    override fun resume() {
        super.resume()

        Gdx.app.log("D","onResume")
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height)
        Gdx.app.log("D","resize")

    }

    override fun dispose() {
        super.dispose()
    }
}

