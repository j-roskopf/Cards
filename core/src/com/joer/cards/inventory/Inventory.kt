/* Copyright (c) 2014 PixelScientists
 * 
 * The MIT License (MIT)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.joer.cards.inventory

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array

/**
 * @author Daniel Holderbaum
 */
class Inventory {

    val slots: Array<Slot>

    init {
        slots = Array(25)
        for (i in 0..24) {
            slots.add(Slot(null, 0))
        }

        // create some random items
        for (slot in slots) {
            slot.add(Item.values()[MathUtils.random(0, Item.values().size - 1)], 1)
        }

        // create a few random empty slots
        for (i in 0..2) {
            val randomSlot = slots.get(MathUtils.random(0, slots.size - 1))
            randomSlot.take(randomSlot.amount)
        }
    }

    fun checkInventory(item: Item): Int {
        var amount = 0

        for (slot in slots) {
            if (slot.item == item) {
                amount += slot.amount
            }
        }

        return amount
    }

    fun store(item: Item, amount: Int): Boolean {
        // first check for a slot with the same item type
        val itemSlot = firstSlotWithItem(item)
        if (itemSlot != null) {
            itemSlot.add(item, amount)
            return true
        } else {
            // now check for an available empty slot
            val emptySlot = firstSlotWithItem(null)
            if (emptySlot != null) {
                emptySlot.add(item, amount)
                return true
            }
        }

        // no slot to add
        return false
    }

    private fun firstSlotWithItem(item: Item?): Slot? {
        for (slot in slots) {
            if (slot.item == item) {
                return slot
            }
        }

        return null
    }

}
