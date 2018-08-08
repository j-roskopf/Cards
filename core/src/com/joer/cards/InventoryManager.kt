package com.joer.cards

import com.joer.cards.models.Item
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryManager @Inject constructor() {
    internal var goldAmount = 0
    internal var items: ArrayList<Item> = ArrayList()
}