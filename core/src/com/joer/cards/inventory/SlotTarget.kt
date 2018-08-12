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

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target

/**
 * @author Daniel Holderbaum
 */
class SlotTarget(actor: SlotActor) : Target(actor) {

    private val targetSlot: Slot

    init {
        targetSlot = actor.slot
        getActor().color = Color.LIGHT_GRAY
    }

    override fun drag(source: Source, payload: Payload, x: Float, y: Float, pointer: Int): Boolean {
        val payloadSlot = payload.getObject() as Slot
        // if (targetSlot.getItem() == payloadSlot.getItem() ||
        // targetSlot.getItem() == null) {
        actor.color = Color.WHITE
        return true
        // } else {
        // getActor().setColor(Color.DARK_GRAY);
        // return false;
        // }
    }

    override fun drop(source: Source, payload: Payload, x: Float, y: Float, pointer: Int) {}

    override fun reset(source: Source?, payload: Payload?) {
        actor.color = Color.LIGHT_GRAY
    }

}
