package com.joer.cards

import com.joer.cards.animations.Explosion
import com.joer.cards.models.Item
import com.joer.cards.models.TURNS_ACTIVE
import com.joer.cards.models.items.Necklace
import com.joer.cards.models.items.SpellBook
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryManager @Inject constructor() {
    internal var goldAmount = 0
    internal var items: ArrayList<Item> = ArrayList()
    internal var explosions: ArrayList<Explosion> = ArrayList()

    fun handleTurnsActive() {
        val iterator = items.iterator()
        while(iterator.hasNext()) {
            val item = iterator.next()
            if(item.turnsActive != TURNS_ACTIVE.FOREVER.getValue()) {
                item.turnsActive--
                if(item.turnsActive <= 0) {
                    iterator.remove()
                }
            }
        }
    }

    fun necklaceActive(): Boolean {
        return items.any { item -> item is Necklace }
    }

    fun spellBookActive(): Int {
        return items.firstOrNull { item ->  item is SpellBook && item.isCurrentlySelected }?.damage ?: -1
    }

    fun removeSelectedItem() {
        val iterator = items.iterator()
        while(iterator.hasNext()) {
            val next = iterator.next()
            if(next.isCurrentlySelected) {
                iterator.remove()
            }
        }
    }
}