package com.joer.cards

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Rectangle
import com.joer.cards.CardManager.Companion.GRID_WIDTH
import com.joer.cards.animations.Explosion
import com.joer.cards.config.Config
import com.joer.cards.models.TURNS_ACTIVE
import com.joer.cards.models.entities.Enemy
import javax.inject.Inject

class InputManager @Inject constructor(private val cardManager: CardManager,
                                       private val inventoryManager: InventoryManager,
                                       private val collisionManager: CollisionManager,
                                       private val atlas: TextureAtlas,
                                       private val spriteBatch: SpriteBatch) {

    internal fun handleKeyboard(keycode: Int) {
        var playerX = cardManager.playerPosition % GRID_WIDTH
        var playerY = cardManager.playerPosition / GRID_WIDTH
        when (keycode) {
            Input.Keys.LEFT -> {
                playerX--
            }
            Input.Keys.RIGHT -> {
                playerX++
            }
            Input.Keys.DOWN -> {
                playerY--
            }
            Input.Keys.UP -> {
                playerY++
            }
        }
        if (validPositionForPlayerToMoveTo(playerX, playerY)) {
            cardManager.firstSelectedPosition = playerX + GRID_WIDTH * playerY
            collisionManager.switchBasedOnTile()
            cardManager.firstSelectedPosition = -1
        }
    }

    fun detectClickedCard(screenX: Float, screenY: Float, pointer: Int, button: Int) {
        //check if it was meant for player
        for ((position, card) in cardManager.cards) {
            val cardsBoundingRectangle = Rectangle(card.x, card.y, Config.CARD_WIDTH, Config.CARD_HEIGHT)
            if (cardsBoundingRectangle.contains(screenX, screenY)) {
                val spellBoolActive = inventoryManager.spellBookActive()

                if (spellBoolActive > 0) {
                    if ((cardManager.cards[position] is Enemy)) {
                        val selectedX = position % GRID_WIDTH
                        val selectedY = position / GRID_WIDTH

                        val explosion = Explosion(atlas, selectedX, selectedY)
                        inventoryManager.explosions.add(explosion)
                        inventoryManager.removeSelectedItem()

                        cardManager.dealDamageToCardAtPosition(position, spellBoolActive)
                    }
                } else {
                    if (cardManager.firstSelectedPosition == -1 && validPositionForPlayerToMoveTo(position)) {
                        cardManager.firstSelectedPosition = position
                        collisionManager.switchBasedOnTile()
                        cardManager.firstSelectedPosition = -1
                    }
                }
            }
        }

        //check if it hit inventory
        inventoryManager.items.forEach {
            if (it.boundingRectangle.contains(screenX, screenY) && it.turnsActive == TURNS_ACTIVE.FOREVER.getValue()) {
                Gdx.app.log("D", "Clicked on item $it")
                
                val currentlySelected = it.isCurrentlySelected
                inventoryManager.items.map { item -> item.isCurrentlySelected = false }
                it.isCurrentlySelected = !currentlySelected
                return
            }
        }
    }

    private fun validPositionForPlayerToMoveTo(firstSelectedPosition: Int): Boolean {
        val selectedX = firstSelectedPosition % GRID_WIDTH
        val selectedY = firstSelectedPosition / GRID_WIDTH

        val playerX = cardManager.playerPosition % GRID_WIDTH
        val playerY = cardManager.playerPosition / GRID_WIDTH

        val distance = Math.sqrt(((playerX - selectedX) * (playerX - selectedX) + (playerY - selectedY) * (playerY - selectedY)).toDouble())

        return checkDistance(distance)
    }

    private fun validPositionForPlayerToMoveTo(newPlayerX: Int, newPlayerY: Int): Boolean {

        val playerX = cardManager.playerPosition % GRID_WIDTH
        val playerY = cardManager.playerPosition / GRID_WIDTH

        val distance = Math.sqrt(((playerX - newPlayerX) * (playerX - newPlayerX) + (playerY - newPlayerY) * (playerY - newPlayerY)).toDouble())

        return checkDistance(distance)
    }

    private fun checkDistance(distance: Double): Boolean {
        return if (inventoryManager.necklaceActive()) {
            distance <= 2
        } else {
            distance <= 1
        }
    }
}