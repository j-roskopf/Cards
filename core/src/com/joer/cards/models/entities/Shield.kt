package com.joer.cards.models.entities

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.config.Config
import com.joer.cards.models.Card
import javax.inject.Inject

class Shield @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Card(xCord, yCord, atlas) {

    private var texture: TextureRegion

    override var health: Int = ((Math.random() + 1) * 5).toInt()

    private val spriteWidth = 16
    private val spriteHeight = 16

    private val index = (Math.random() * 30).toInt()

    init {
        texture = TextureRegion(atlas.findRegion("shields"), 0, index * spriteHeight, spriteWidth, spriteHeight)
    }

    override fun draw(batch: Batch) {
        super.draw(batch)
        batch.draw(texture, x + Config.CARD_WIDTH / 2 - spriteWidth, y + Config.CARD_HEIGHT / 2 - spriteHeight * 3, Config.CARD_WIDTH / 4, Config.CARD_HEIGHT / 4)
    }

}