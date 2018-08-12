package com.joer.cards.models.items.potion

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.models.Item
import com.joer.cards.models.TURNS_ACTIVE
import javax.inject.Inject

//give health to user at a later date

class DamagePotion @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Item(xCord, yCord, atlas) {
    override var textureRegion: TextureRegion = TextureRegion(atlas.findRegion("rpg_items"), spriteWidth * 8,  spriteHeight * 9, spriteWidth, spriteHeight)

    override var turnsActive = TURNS_ACTIVE.FOREVER.getValue()

    override var health = ((Math.random() + 1) * 4).toInt()
}