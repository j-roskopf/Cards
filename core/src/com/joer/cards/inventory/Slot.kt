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

import com.badlogic.gdx.utils.Array

/**
 * @author Daniel Holderbaum
 */
class Slot(item: Item?, amount: Int) {

    var item: Item? = null
        private set

    var amount: Int = 0
        private set

    private val slotListeners = Array<SlotListener>()

    val isEmpty: Boolean
        get() = item == null || amount <= 0

    init {
        this.item = item
        this.amount = amount
    }

    fun addListener(slotListener: SlotListener) {
        slotListeners.add(slotListener)
    }

    fun removeListener(slotListener: SlotListener) {
        slotListeners.removeValue(slotListener, true)
    }

    /**
     * Returns `true` in case this slot has the same item type and at
     * least the same amount of items as the given other slot.
     *
     * @param other
     * The other slot to be checked.
     * @return `True` in case this slot has the same item type and at
     * least the same amount of items as the given other slot.
     * `False` otherwise.
     */
    fun matches(other: Slot): Boolean {
        return this.item == other.item && this.amount >= other.amount
    }

    fun add(item: Item, amount: Int): Boolean {
        if (this.item == item || this.item == null) {
            this.item = item
            this.amount += amount
            notifyListeners()
            return true
        }

        return false
    }

    fun take(amount: Int): Boolean {
        if (this.amount >= amount) {
            this.amount -= amount
            if (this.amount == 0) {
                item = null
            }
            notifyListeners()
            return true
        }

        return false
    }

    private fun notifyListeners() {
        for (slotListener in slotListeners) {
            slotListener.hasChanged(this)
        }
    }

    override fun toString(): String {
        return "Slot[$item:$amount]"
    }
}
