package com.joer.cards.animations

import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.utils.Array
import com.joer.cards.CardGame
import com.joer.cards.config.Config

class Explosion(atlas: TextureAtlas, xCord: Int, yCord: Int): Sprite() {

    val spriteWidth = 100f
    val spriteHeight = 100f
    internal var stateTimer = 0f
    internal var animation: Animation<TextureRegion>


    init {
        CardGame.component.inject(this)

        val frames: Array<TextureRegion> = Array()
        for(i in 0..9) {
            for(j in 0..6) {
                frames.add(TextureRegion(atlas.findRegion("round_explosion"), j * spriteWidth.toInt(), i * spriteHeight.toInt(), spriteWidth.toInt(), spriteHeight.toInt()))
            }
        }
        animation = Animation(0.025f, frames)

        x = xCord * Config.CARD_WIDTH
        y = yCord * Config.CARD_HEIGHT
    }

    override fun draw(batch: Batch) {
        batch.draw(animation.getKeyFrame(stateTimer, false), x, y, Config.CARD_WIDTH, Config.CARD_HEIGHT)
    }

    fun update(delta: Float) {
        stateTimer += delta
    }
}