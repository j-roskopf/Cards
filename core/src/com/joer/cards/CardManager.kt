package com.joer.cards

import com.badlogic.gdx.ApplicationLogger
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.joer.cards.config.Config
import com.joer.cards.models.*
import javax.inject.Inject

class CardManager @Inject constructor(private val atlas: TextureAtlas, private val logger: ApplicationLogger) {
    internal var cards: HashMap<Int, Card> = HashMap()
    private var firstSelectedPosition = -1

    internal var playerPosition = 4
    private var gridWidth = 3
    private var gridHeight = 3

    internal fun createInitialCards() {
        cards.clear()

        /**
            |0,2|1,2|2,2|
            |0,1|1,1|2,1|
            |0,0|1,0|2,0|
         */

        cards[0] = Sword(0, 0, atlas)
        cards[1] = Enemy(1,0, atlas)
        cards[2] = Shield(2,0, atlas)

        cards[3] = Enemy(0,1, atlas)
        cards[playerPosition] = Player(1,1, atlas)
        cards[5] = Enemy(2, 1, atlas)

        cards[6] = Enemy(0,2, atlas)
        cards[7] = Enemy(1, 2, atlas)
        cards[8] = Enemy(2, 2, atlas)


    }

    fun detectClickedCard(screenX: Float, screenY: Float, pointer: Int, button: Int) {
        for((position, card) in cards) {
            val cardsBoundingRectangle = Rectangle(card.x, card.y, Config.CARD_WIDTH, Config.CARD_HEIGHT)
            if(cardsBoundingRectangle.contains(screenX, screenY)) {
                if(firstSelectedPosition == -1) {
                    if(validPositionForPlayerToMoveTo(position)) {
                        firstSelectedPosition = position
                        switchBasedOnTile()
                        firstSelectedPosition = -1
                    }
                }
            }
        }
    }

    private fun addCardAtPosition(psn: Int) {
        val x = psn % gridWidth
        val y = psn / gridWidth
        val choice = (Math.random() * 3).toInt()
        when(choice) {
            0 -> cards[psn] = Enemy(x, y, atlas)
            1 -> cards[psn] = Sword(x, y, atlas)
            2 -> cards[psn] = Shield(x, y, atlas)
            else -> cards[psn] = Sword(x, y, atlas)
        }
    }

    private fun validPositionForPlayerToMoveTo(firstSelectedPosition: Int): Boolean {
        val selectedX = firstSelectedPosition % gridWidth
        val selectedY = firstSelectedPosition / gridWidth

        val playerX = playerPosition % gridWidth
        val playerY = playerPosition / gridWidth

        logger.log("D","selected psn = $firstSelectedPosition and player psn = $playerPosition")

        logger.log("D","selectedPsn and player $selectedX $selectedY $playerX $playerY")

        val canMoveX = Math.abs(selectedX - playerX) < 1
        val canMoveY = Math.abs(selectedY - playerY) < 1

        logger.log("D","canMoveX and canMoveY $canMoveX $canMoveY")

        return canMoveX != canMoveY
    }

    private fun swapCards() {
        val positionForPlayerToMoveTo = Vector2(cards[firstSelectedPosition]?.x ?: 0f, cards[firstSelectedPosition]?.y ?: 0f)
        cards[playerPosition]?.positionToGoTo = positionForPlayerToMoveTo
        cards[playerPosition]?.needToSwap = true

        cards[playerPosition]?.let {
            cards[firstSelectedPosition] = it
            val oldPlayerPosition = playerPosition
            playerPosition = firstSelectedPosition
            addCardAtPosition(oldPlayerPosition)
        }
    }

    private fun switchBasedOnTile() {
        val tileClickedOn = cards[firstSelectedPosition]
        when(tileClickedOn) {
            is Enemy -> {
                attackEnemy(tileClickedOn)
            }
            is Sword -> {
                pickUpSword(tileClickedOn)
            }
            is Shield -> {
                pickUpShield(tileClickedOn)
            }
        }
    }

    private fun pickUpShield(tileClickedOn: Shield) {
        (cards[playerPosition] as Player).addAmountToHealth(tileClickedOn.health)
        swapCards()
    }

    private fun pickUpSword(tileClickedOn: Sword) {
        (cards[playerPosition] as Player).addAmountToAttack(tileClickedOn.health)
        swapCards()
    }

    private fun attackEnemy(tileClickedOn: Enemy) {
        (cards[playerPosition] as Player).attack()
        val killed = calculateDamage(tileClickedOn)
        if(killed) {
            //we killed them!
            swapCards()
        }
    }


    private fun calculateDamage(tileClickedOn: Enemy): Boolean {
        val currentHealth = tileClickedOn.health
        tileClickedOn.health = tileClickedOn.health - ((cards[playerPosition] as Player).attack)
        var leftoverAttack = (cards[playerPosition]?.attack ?: 0) - currentHealth

        if((cards[playerPosition]?.attack ?: 0 <= 0)){
            cards[playerPosition]?.health = (cards[playerPosition]?.health ?: 0) - currentHealth

            if(cards[playerPosition]?.health ?: 0 <= 0) {
                (cards[playerPosition] as Player).die()
                return false
            }
            return true
        } else if(leftoverAttack < 0) {
            cards[playerPosition]?.attack = 0
            return false
        } else if(leftoverAttack == 0) {
            cards[playerPosition]?.attack = leftoverAttack
            return true
        } else {
            cards[playerPosition]?.attack = (cards[playerPosition]?.attack ?: 0) - currentHealth
            return true
        }
    }

    fun update(delta: Float) {

    }

}