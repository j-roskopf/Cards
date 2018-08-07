package com.joer.cards.models

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import com.joer.cards.config.Config
import javax.inject.Inject

class Enemy @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Card(xCord, yCord) {

    private var enemyIdle: Animation<TextureRegion>

    private val spriteWidth = 32
    private val spriteHeight = 32

    override var health: Int = ((Math.random() + 1) * 5).toInt()

    private var indexInSheet = (Math.random() * 25).toInt()

    init {

        val frames = Array<TextureRegion>()
        for (i in 0 until 3) {
            frames.add(TextureRegion(atlas.findRegion("enemies"), i * spriteWidth, indexInSheet * spriteHeight, spriteWidth, spriteHeight))
        }

        enemyIdle = Animation(0.3f, frames)
    }

    override fun update(delta: Float) {
        super.update(delta)
        //setRegion(enemyIdle.getKeyFrame(stateTimer, true))
        //setBounds(x, y, spriteWidth.toFloat(), spriteHeight.toFloat())
    }

    override fun draw(batch: Batch) {
        super.draw(batch)
        batch.draw(getCurrentFrame(), x + Config.CARD_WIDTH / 4, y + Config.CARD_HEIGHT / 4, Config.CARD_WIDTH / 2, Config.CARD_HEIGHT / 2)
    }

    private fun getCurrentFrame(): TextureRegion {
        return enemyIdle.getKeyFrame(stateTimer, true)
    }

}