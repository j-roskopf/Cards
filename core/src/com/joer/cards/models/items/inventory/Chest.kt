package com.joer.cards.models.items.inventory

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.models.Item
import javax.inject.Inject

class Chest @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Item(xCord, yCord, atlas) {

    //give player gold to spend in store?

    var spriteWidth = 16
    var spriteHeight = 16

    override var textureRegion: TextureRegion =
            TextureRegion(atlas.findRegion("dungeon"),
                    14 * spriteWidth,
                    12 * spriteHeight,
                    spriteWidth,
                    spriteHeight)

    override fun draw(batch: Batch) {
        batch.draw(textureRegion, x, y, Item.spriteWidth.toFloat(), Item.spriteHeight.toFloat())
    }
}