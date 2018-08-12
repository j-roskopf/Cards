package com.joer.cards.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.joer.cards.managers.InventoryManager
import javax.inject.Inject

class FXManager @Inject constructor(private val inventoryManager: InventoryManager, private val spriteBatch: SpriteBatch) {

    fun draw(delta: Float) {
        //draw explosions
        inventoryManager.explosions.forEach {
            it.draw(spriteBatch)
        }
    }

    fun update(delta: Float) {
        val iterator = inventoryManager.explosions.iterator()
        while(iterator.hasNext()) {
            val next = iterator.next()
            next.update(delta)
            if(next.animation.isAnimationFinished(next.stateTimer)) {
                iterator.remove()
            }
        }
    }

}