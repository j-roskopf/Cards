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

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target
import com.joer.cards.CardGame
import javax.inject.Inject

/**
 * @author Daniel Holderbaum
 */
class SlotSource(actor: SlotActor) : Source(actor) {

    private val sourceSlot: Slot
    
    @Inject
    lateinit var assetManager: AssetManager

    init {
        CardGame.component.inject(this)
        this.sourceSlot = actor.slot
    }

    override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int): Payload? {
        if (sourceSlot.amount == 0) {
            return null
        }

        val payload = Payload()
        val payloadSlot = Slot(sourceSlot.item!!, sourceSlot.amount)
        sourceSlot.take(sourceSlot.amount)
        payload.setObject(payloadSlot)

        val icons = assetManager.get("icons/icons.atlas", TextureAtlas::class.java)
        val icon = icons.findRegion(payloadSlot.item?.textureRegion)

        val dragActor = Image(icon)
        payload.dragActor = dragActor

        val validDragActor = Image(icon)
        // validDragActor.setColor(0, 1, 0, 1);
        payload.validDragActor = validDragActor

        val invalidDragActor = Image(icon)
        // invalidDragActor.setColor(1, 0, 0, 1);
        payload.invalidDragActor = invalidDragActor

        return payload
    }

    override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int, payload: Payload?, target: Target?) {
        val payloadSlot = payload!!.getObject() as Slot
        if (target != null) {
            val targetSlot = (target.actor as SlotActor).slot
            if (targetSlot.item == payloadSlot.item || targetSlot.item == null) {
                targetSlot.add(payloadSlot.item!!, payloadSlot.amount)
            } else {
                val targetType = targetSlot.item
                val targetAmount = targetSlot.amount
                targetSlot.take(targetAmount)
                targetSlot.add(payloadSlot.item!!, payloadSlot.amount)
                sourceSlot.add(targetType!!, targetAmount)
            }
        } else {
            sourceSlot.add(payloadSlot.item!!, payloadSlot.amount)
        }
    }
}
