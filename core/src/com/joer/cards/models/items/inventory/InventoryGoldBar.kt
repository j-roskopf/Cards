package com.joer.cards.models.items.inventory

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.models.Item
import javax.inject.Inject

class InventoryGoldBar @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Item(xCord, yCord, atlas) {

    //give player gold to spend in store?

    override var textureRegion: TextureRegion = TextureRegion(atlas.findRegion("rpg_items"),  spriteWidth * 15,  spriteHeight * 6, spriteWidth, spriteHeight)

    override fun draw(batch: Batch) {
        batch.draw(textureRegion, x, y, spriteWidth.toFloat(), spriteHeight.toFloat())
    }
}