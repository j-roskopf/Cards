package com.joer.cards.models.items

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.models.Item
import javax.inject.Inject

class Ring @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Item(xCord, yCord, atlas) {
    override var textureRegion: TextureRegion = TextureRegion(atlas.findRegion("rpg_items"),  spriteWidth * 15,  spriteHeight * 10, spriteWidth, spriteHeight)

    override var turnsActive = 3
}