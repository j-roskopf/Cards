package com.joer.cards.models.items

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.models.Item
import javax.inject.Inject

//alow user to move 2 squares?

class Necklace @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Item(xCord, yCord, atlas) {
    override var textureRegion: TextureRegion = TextureRegion(atlas.findRegion("rpg_items"),  spriteWidth * 15,  spriteHeight * 7, spriteWidth, spriteHeight)

    override var turnsActive = 3
}