package com.joer.cards.models

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.config.Config
import javax.inject.Inject

class Sword @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Card(xCord, yCord) {

    private var texture: TextureRegion

    override var health: Int = ((Math.random() + 1) * 5).toInt()

    private val spriteWidth = 16
    private val spriteHeight = 16

    private val index = (Math.random() * 30).toInt()

    init {
        texture = TextureRegion(atlas.findRegion("swords"), 0, index * spriteHeight, spriteWidth, spriteHeight)
    }

    override fun draw(batch: Batch) {
        super.draw(batch)
        batch.draw(texture, x + Config.CARD_WIDTH / 4, y + Config.CARD_HEIGHT / 4, Config.CARD_WIDTH / 2, Config.CARD_HEIGHT / 2)
    }

}