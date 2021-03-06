package com.joer.cards.managers

import com.joer.cards.models.Item
import com.joer.cards.models.entities.Enemy
import com.joer.cards.models.entities.Player
import com.joer.cards.models.entities.Shield
import com.joer.cards.models.entities.Sword
import com.joer.cards.models.items.GoldBar
import com.joer.cards.models.items.Necklace
import com.joer.cards.models.items.Ring
import com.joer.cards.models.items.SpellBook
import com.joer.cards.models.items.potion.DamagePotion
import com.joer.cards.models.items.potion.HealthPotion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollisionManager @Inject constructor() {

    @Inject lateinit var inventoryManager: InventoryManager
    @Inject lateinit var cardManager: CardManager

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
            is HealthPotion -> {
                pickupItem(tileClickedOn)
            }
            is DamagePotion -> {
                pickupItem(tileClickedOn)
            }
            is Necklace -> {
                pickupItem(tileClickedOn)
            }
            is SpellBook -> {
                pickupItem(tileClickedOn)
            }
            is Ring -> {
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
        (cardManager.cards[cardManager.playerPosition] as Player).addAmountToAttack(tileClickedOn.attack)
        cardManager.swapCards()
    }

    private fun attackEnemy(tileClickedOn: Enemy) {
        val player = (cardManager.cards[cardManager.playerPosition] as Player)
        player.attack()
        if(inventoryManager.ringActive()) {
            //the player is invincible
            cardManager.swapCards()
        } else {
            val killed = cardManager.calculateDamage(tileClickedOn)
            if(killed) {
                //we killed them!
                cardManager.swapCards()
            }
        }
    }


}