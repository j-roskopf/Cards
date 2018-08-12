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

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.joer.cards.CardGame
import javax.inject.Inject

/**
 * @author Daniel Holderbaum
 */

class InventoryScreen : Screen {

    private var inventoryActor: InventoryActor? = null

    @Inject lateinit var assetManager: AssetManager

    private lateinit var stage: Stage

    init {
        CardGame.component.inject(this)
    }

    override fun show() {
        stage = Stage()
        Gdx.input.inputProcessor = stage

        val skin = assetManager.get("skins/uiskin.json", Skin::class.java)
        val dragAndDrop = DragAndDrop()
        inventoryActor = InventoryActor(Inventory(), dragAndDrop, skin, stage)
        stage.addActor(inventoryActor)
    }

    override fun resume() {
        assetManager.finishLoading()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            inventoryActor!!.isVisible = true
        }

        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun pause() {
        // NOOP
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
        dispose()
    }

    override fun dispose() {
        stage.dispose()
    }


}
