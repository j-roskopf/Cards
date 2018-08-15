package com.joer.cards.models

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.config.Config


abstract class Item(xCord: Int, yCord: Int, atlas: TextureAtlas): Card(xCord, yCord, atlas) {

    companion object {
        internal var spriteWidth = 48
        internal var spriteHeight = 48
    }

    init {
        x = xCord * Config.CARD_WIDTH
        y = yCord * Config.CARD_HEIGHT
    }

    internal open var turnsActive = 0

    internal open var markedForRemoval = false

    internal open var damage = ((Math.random() + 1) * 4).toInt()

    abstract var textureRegion: TextureRegion

    private var inInventory = false

    override fun update(delta: Float) {
        if(!inInventory) {
            super.update(delta)
        }
    }

    fun setInInventory() {
        inInventory = true
        setPosition(x, y)
        this.setBounds(x, y, Item.spriteWidth.toFloat(), Item.spriteHeight.toFloat())
    }

    override fun draw(batch: Batch) {
        if(!inInventory) {
            //so it will draw the card behind
            super.draw(batch)
            batch.draw(textureRegion, x + Config.CARD_WIDTH / 2 - spriteWidth / 2 , y + Config.CARD_HEIGHT / 2 - spriteHeight / 2, spriteWidth.toFloat(), spriteHeight.toFloat())
        } else {
            batch.draw(textureRegion, x , y, spriteWidth.toFloat(), spriteHeight.toFloat())
        }
    }
}

enum class TURNS_ACTIVE(private val turns: Int) {

    FOREVER (-100),
    NOT_FOREVER(0);

    fun getValue(): Int {
        return turns
    }
}
