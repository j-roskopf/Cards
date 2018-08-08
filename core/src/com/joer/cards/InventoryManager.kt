package com.joer.cards

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryManager @Inject constructor() {
    internal var goldAmount = 0
}