package com.joer.cards.models

import com.badlogic.gdx.graphics.g2d.TextureAtlas

abstract class Item(xCord: Int, yCord: Int, atlas: TextureAtlas): Card(xCord, yCord, atlas) {

    companion object {
        internal var spriteWidth = 48
        internal var spriteHeight = 48
    }

}