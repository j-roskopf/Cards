package com.joer.cards.managers

import com.joer.cards.animations.Explosion
import com.joer.cards.models.Item
import com.joer.cards.models.TURNS_ACTIVE
import com.joer.cards.models.items.Necklace
import com.joer.cards.models.items.Ring
import com.joer.cards.models.items.SpellBook
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryManager @Inject constructor() {
    internal var goldAmount = 0
    internal var items: ArrayList<Item> = ArrayList()
    internal var explosions: ArrayList<Explosion> = ArrayList()

    var selectedItem: Item? = null

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

    fun update() {
        val iterator = items.iterator()
        while(iterator.hasNext()) {
            if(iterator.next().markedForRemoval) {
                iterator.remove()
            }
        }
    }

    fun necklaceActive(): Boolean {
        return items.any { item -> item is Necklace }
    }

    fun ringActive(): Boolean {
        return items.any { item -> item is Ring }
    }

    fun spellBookActive(): Int {
        return if(selectedItem is SpellBook) selectedItem?.damage ?: -1 else -1
    }

    fun removeSelectedItem() {
        items.forEach {
            if(it == selectedItem) {
                it.markedForRemoval = true
            }
        }

        selectedItem = null
    }

}