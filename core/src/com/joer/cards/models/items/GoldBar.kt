package com.joer.cards.models.items

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.config.Config
import com.joer.cards.models.Item
import javax.inject.Inject

class GoldBar @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Item(xCord, yCord, atlas) {

    private val textureRegion: TextureRegion = TextureRegion(atlas.findRegion("rpg_items"),  spriteWidth * 15,  spriteHeight * 6, spriteWidth, spriteHeight)

    var worth: Int = ((Math.random() + 1) * 10).toInt()

    init {
        x = xCord * Config.CARD_WIDTH
        y = yCord * Config.CARD_HEIGHT
    }

    override fun draw(batch: Batch) {
        super.draw(batch)
        batch.draw(textureRegion, x + Config.CARD_WIDTH / 2 - spriteWidth / 2 , y + Config.CARD_HEIGHT / 2 - spriteHeight / 2, spriteWidth.toFloat(), spriteHeight.toFloat())
    }
}