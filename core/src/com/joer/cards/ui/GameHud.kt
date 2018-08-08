package com.joer.cards.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.utils.Disposable
import com.joer.cards.CardGame
import com.joer.cards.InventoryManager
import com.joer.cards.models.Item.Companion.spriteHeight
import com.joer.cards.models.Item.Companion.spriteWidth
import javax.inject.Inject

class GameHud @Inject constructor(private val batch: SpriteBatch, atlas: TextureAtlas): Disposable {

    @Inject lateinit var inventoryManager: InventoryManager

    private val font: BitmapFont = BitmapFont()
    private var cam: OrthographicCamera? = null
    private var textureRegion: TextureRegion
    private var goldAmountLayout = GlyphLayout()
    private var goldMargin = 16

    init {
        CardGame.component.inject(this)

        textureRegion = TextureRegion(atlas.findRegion("rpg_items"),spriteWidth * 15,  spriteHeight * 6, spriteWidth, spriteHeight)
    }

    fun resize(screenWidth: Int, screenHeight: Int) {
        cam = OrthographicCamera(screenWidth.toFloat(), screenHeight.toFloat())
        cam!!.translate((screenWidth / 2).toFloat(), (screenHeight / 2).toFloat())
        cam!!.update()
        batch.projectionMatrix = cam!!.combined
    }

    fun update() {

    }

    fun render() {
        batch.begin()
        goldAmountLayout.setText(font, inventoryManager.goldAmount.toString())

        font.draw(batch, inventoryManager.goldAmount.toString(), (Gdx.graphics.width - goldAmountLayout.width - goldMargin), (Gdx.graphics.height - (spriteHeight / 2) + 4).toFloat())
        batch.draw(textureRegion, (Gdx.graphics.width - spriteWidth.toFloat() - goldAmountLayout.width - goldMargin), (Gdx.graphics.height - 50).toFloat(), spriteWidth.toFloat(), spriteHeight.toFloat())

        inventoryManager.items.forEachIndexed { index, item ->
            batch.draw(item.textureRegion, (Gdx.graphics.width - spriteWidth.toFloat() - goldAmountLayout.width - goldMargin) - ((index + 1) * spriteWidth), (Gdx.graphics.height - 50).toFloat(), spriteWidth.toFloat(), spriteHeight.toFloat())
        }

        batch.end()
    }

    override fun dispose() {
        font.dispose()
        batch.dispose()
    }
}