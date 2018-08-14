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
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.joer.cards.CardGame
import javax.inject.Inject

/**
 * @author Daniel Holderbaum
 */
class SlotActor(private val slotSkin: Skin, val slot: Slot, stage: Stage) : ImageButton(StyleCreator().createStyle(slotSkin, slot)), SlotListener {

    @Inject
    lateinit var assetManager: AssetManager

    init {
        CardGame.component.inject(this)
        slot.addListener(this)

        val tooltip = SlotTooltip(slot, slotSkin)
        tooltip.touchable = Touchable.disabled // allows for mouse to hit tooltips in the top-right corner of the screen without flashing
        stage.addActor(tooltip);
        addListener(TooltipListener(tooltip, true))
    }

    fun createStyle(skin: Skin, slot: Slot): ImageButton.ImageButtonStyle {
        val icons = assetManager.get("icons/icons.atlas", TextureAtlas::class.java)
        val image: TextureRegion
        if (slot.item != null) {
            image = icons.findRegion(slot.item!!.textureRegion)
        } else {
            image = icons.findRegion("nothing")
        }
        val style = ImageButton.ImageButtonStyle(skin.get<Button.ButtonStyle>(Button.ButtonStyle::class.java))
        style.imageUp = TextureRegionDrawable(image)
        style.imageDown = TextureRegionDrawable(image)

        return style
    }

    override fun hasChanged(slot: Slot) {
        style = createStyle(slotSkin, slot)
    }

}

class StyleCreator {

    init {
        CardGame.component.inject(this)
    }

    @Inject
    lateinit var assetManager: AssetManager

    fun createStyle(slotSkin: Skin, slot: Slot): ImageButton.ImageButtonStyle {
        val icons = assetManager.get("icons/icons.atlas", TextureAtlas::class.java)
        val image: TextureRegion
        if (slot.item != null) {
            image = icons.findRegion(slot.item!!.textureRegion)
        } else {
            image = icons.findRegion("nothing")
        }
        val style = ImageButton.ImageButtonStyle(slotSkin.get<Button.ButtonStyle>(Button.ButtonStyle::class.java))
        style.imageUp = TextureRegionDrawable(image)
        style.imageDown = TextureRegionDrawable(image)

        return style
    }

}
