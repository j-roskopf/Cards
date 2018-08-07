package com.joer.cards.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.joer.cards.config.Config
import kotlin.math.roundToInt



abstract class Card(xCord: Int, yCord: Int): Sprite() {

    internal var needToSwap = false
    internal var positionToGoTo = Vector2(-1f, -1f)
    internal var stateTimer = 0f
    private var font: BitmapFont = BitmapFont()
    private var textureRegion: TextureRegion

    internal open var health = 10
    internal open var attack = 10

    init {
        x = xCord * Config.CARD_WIDTH
        y = yCord * Config.CARD_HEIGHT
        this.setPosition(x, y)
        updateBounds()

        font.color = Color.RED
        font.data.scale(0.5f)

        texture = Texture(Gdx.files.internal("card_bg.png"))
        textureRegion = TextureRegion(texture)
    }

    open fun update(delta: Float) {
        stateTimer += delta

        if(needToSwap) {
            if(x.roundToInt() == positionToGoTo.x.roundToInt() && y.roundToInt() == positionToGoTo.y.roundToInt()) {
                needToSwap = false
                positionToGoTo = Vector2(-1f, -1f)
                return
            }
            x = MathUtils.lerp(x, positionToGoTo.x, 0.1f)
            y = MathUtils.lerp(y, positionToGoTo.y, 0.1f)
            updatePosition(x, y)
        }
    }

    private fun updatePosition(x: Float, y: Float) {
        this.x = x
        this.y = y
        setPosition(x, y)
        updateBounds()
    }

    private fun updateBounds() {
        Gdx.app.log("D", "updating bounds to x + y $x $y $needToSwap")
        this.setBounds(x, y  , Config.CARD_WIDTH, Config.CARD_HEIGHT)
    }

    override fun draw(batch: Batch) {
        //draw bg
        batch.draw(textureRegion, x + 16, y + 16, Config.CARD_WIDTH - 32, Config.CARD_HEIGHT - 32)

        if(this is Player) {
            //draw attack
            font.draw(batch, attack.toString(), x + 32, y + Config.CARD_HEIGHT - 22 )
        }

        //draw health
        font.draw(batch, health.toString(), x + Config.CARD_WIDTH - 58, y + Config.CARD_HEIGHT - 22 )
    }

    fun dispose() {

    }

}