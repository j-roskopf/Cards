package com.joer.cards.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.TimeUtils
import com.joer.cards.config.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FrameRate @Inject constructor(): Disposable {
    internal var lastTimeCounted: Long = 0
    private var sinceChange: Float = 0.toFloat()
    private var frameRate: Float = 0.toFloat()
    private val font: BitmapFont
    private val batch: SpriteBatch
    private var cam: OrthographicCamera? = null

    init {
        lastTimeCounted = TimeUtils.millis()
        sinceChange = 0f
        frameRate = Gdx.graphics.framesPerSecond.toFloat()
        font = BitmapFont()
        batch = SpriteBatch()
        cam = OrthographicCamera(Config.GAME_WIDTH, Config.GAME_HEIGHT)
    }

    fun resize(screenWidth: Int, screenHeight: Int) {
        cam = OrthographicCamera(screenWidth.toFloat(), screenHeight.toFloat())
        cam!!.translate((screenWidth / 2).toFloat(), (screenHeight / 2).toFloat())
        cam!!.update()
        batch.projectionMatrix = cam!!.combined
    }

    fun update() {
        val delta = TimeUtils.timeSinceMillis(lastTimeCounted)
        lastTimeCounted = TimeUtils.millis()

        sinceChange += delta.toFloat()
        if (sinceChange >= 1000) {
            sinceChange = 0f
            frameRate = Gdx.graphics.framesPerSecond.toFloat()
        }
    }

    fun render() {
        batch.begin()
        font.draw(batch, frameRate.toInt().toString() + " fps", 3f, (Config.GAME_HEIGHT - 3))
        batch.end()
    }

    override fun dispose() {
        font.dispose()
        batch.dispose()
    }
}