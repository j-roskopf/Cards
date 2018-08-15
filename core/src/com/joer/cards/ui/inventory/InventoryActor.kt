package com.joer.cards.ui.inventory

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

class InventoryActor(private val textureRegion: TextureRegion, xCord: Float, yCord: Float) : Actor() {
    init {
        setSize(Inventory.INVENTORY_ITEM_HEIGHT, Inventory.INVENTORY_ITEM_WIDTH)
        setPosition(xCord, yCord)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(textureRegion, x, y, width, height)
    }
}