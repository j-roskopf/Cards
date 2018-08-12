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

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener

/**
 * Makes a given tooltip actor visible when the actor this listener is attached
 * to was hovered. It will also hide the tooltip when the mouse is not hovering
 * anymore.
 *
 * @author Daniel Holderbaum
 */
class TooltipListener(private val tooltip: Actor, private val followCursor: Boolean) : InputListener() {

    private var inside: Boolean = false

    private val position = Vector2()
    private val tmp = Vector2()
    private val offset = Vector2(10f, 10f)

    override fun mouseMoved(event: InputEvent?, x: Float, y: Float): Boolean {
        if (inside && followCursor) {
            event!!.listenerActor.localToStageCoordinates(tmp.set(x, y))
            tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y)
        }
        return false
    }

    override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
        inside = true
        tooltip.isVisible = true
        tmp.set(x, y)
        event!!.listenerActor.localToStageCoordinates(tmp)
        tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y)
        tooltip.toFront()
    }

    override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
        inside = false
        tooltip.isVisible = false
    }

    /**
     * The offset of the tooltip from the touch position. It should not be
     * positive as the tooltip will flicker otherwise.
     */
    fun setOffset(offsetX: Float, offsetY: Float) {
        offset.set(offsetX, offsetY)
    }

}
