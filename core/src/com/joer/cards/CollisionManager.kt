package com.joer.cards

import com.joer.cards.models.Item
import com.joer.cards.models.entities.Enemy
import com.joer.cards.models.entities.Player
import com.joer.cards.models.entities.Shield
import com.joer.cards.models.entities.Sword
import com.joer.cards.models.items.GoldBar
import com.joer.cards.models.items.Necklace
import com.joer.cards.models.items.Potion
import com.joer.cards.models.items.SpellBook
import javax.inject.Inject

class CollisionManager @Inject constructor(private val inventoryManager: InventoryManager, private val cardManager: CardManager) {

    internal fun switchBasedOnTile() {
        val tileClickedOn = cardManager.cards[cardManager.firstSelectedPosition]

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
            is GoldBar -> {
                pickupGoldBar(tileClickedOn)
            }
            is Potion -> {
                pickupItem(tileClickedOn)
            }
            is Necklace -> {
                pickupItem(tileClickedOn)
            }
            is SpellBook -> {
                pickupItem(tileClickedOn)
            }
        }
    }

    private fun pickupItem(tileClickedOn: Item) {
        cardManager.swapCards()
        tileClickedOn.setInInventory()
        inventoryManager.items.add(tileClickedOn)
    }

    private fun pickupGoldBar(tileClickedOn: GoldBar) {
        inventoryManager.goldAmount += tileClickedOn.worth
        cardManager.swapCards()
    }

    private fun pickUpShield(tileClickedOn: Shield) {
        (cardManager.cards[cardManager.playerPosition] as Player).addAmountToHealth(tileClickedOn.health)
        cardManager.swapCards()
    }

    private fun pickUpSword(tileClickedOn: Sword) {
        (cardManager.cards[cardManager.playerPosition] as Player).addAmountToAttack(tileClickedOn.health)
        cardManager.swapCards()
    }

    private fun attackEnemy(tileClickedOn: Enemy) {
        (cardManager.cards[cardManager.playerPosition] as Player).attack()
        val killed = cardManager.calculateDamage(tileClickedOn)
        if(killed) {
            //we killed them!
            cardManager.swapCards()
        }
    }


}