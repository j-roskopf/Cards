package com.joer.cards.models.items.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.joer.cards.models.Item
import javax.inject.Inject

class CloseButton @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas): Item(xCord, yCord, atlas) {

    override var textureRegion: TextureRegion = TextureRegion(Texture(Gdx.files.internal("close_button.png")),0,0, spriteWidth, spriteHeight)

    init {
        setBounds(0f, 0f, 32f, 32f)
    }
    override fun draw(batch: Batch) {
        batch.draw(textureRegion, x, y, 32f, 32f)
    }
}