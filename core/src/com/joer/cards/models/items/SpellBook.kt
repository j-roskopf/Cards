package com.joer.cards.models.items

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.models.Item
import com.joer.cards.models.TURNS_ACTIVE
import javax.inject.Inject

class SpellBook @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Item(xCord, yCord, atlas) {
    override var textureRegion: TextureRegion = TextureRegion(atlas.findRegion("rpg_items"), spriteWidth * 2,  spriteHeight * 2, spriteWidth, spriteHeight)

    override var turnsActive = TURNS_ACTIVE.FOREVER.getValue()
}