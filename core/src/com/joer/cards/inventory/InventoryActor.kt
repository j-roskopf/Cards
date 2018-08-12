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

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop

/**
 * @author Daniel Holderbaum
 */
class InventoryActor(inventory: Inventory, dragAndDrop: DragAndDrop, skin: Skin, stage: Stage) : Window("InventoryOld...", skin) {

    init {

        val closeButton = TextButton("X", skin)
        closeButton.addListener(HidingClickListener(this))
        titleTable.add(closeButton).height(padTop)

        setPosition(400f, 100f)
        defaults().space(8f)
        row().fill().expandX()

        var i = 0
        for (slot in inventory.slots) {
            val slotActor = SlotActor(skin, slot, stage)
            dragAndDrop.addSource(SlotSource(slotActor))
            dragAndDrop.addTarget(SlotTarget(slotActor))
            add(slotActor)

            i++
            if (i % 5 == 0) {
                row()
            }
        }

        pack()

        // it is hidden by default
        isVisible = false
    }

}
