package com.joer.cards.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.joer.cards.config.Config
import com.joer.cards.models.entities.Enemy
import com.joer.cards.models.entities.Player
import com.joer.cards.models.entities.Shield
import com.joer.cards.models.entities.Sword
import com.joer.cards.models.items.GoldBar
import kotlin.math.roundToInt

abstract class Card(xCord: Int, yCord: Int, atlas: TextureAtlas): Sprite() {

    internal var needToSwap = false
    internal var positionToGoTo = Vector2(-1f, -1f)
    internal var stateTimer = 0f
    private var font: BitmapFont = BitmapFont()
    private var textureRegion: TextureRegion

    private var shieldTextureRegion: TextureRegion
    private var swordTextureRegion: TextureRegion

    private val swordSpriteWidth = 16f
    private val swordSpriteHeight = 16f
    private val shieldSpriteWidth = 16f
    private val shieldSpriteHeight = 16f

    internal open var health = 10
    internal open var attack = 10

    init {
        x = xCord * Config.CARD_WIDTH
        y = yCord * Config.CARD_HEIGHT
        this.setPosition(x, y)
        updateBounds()

        font.color = Color.RED
        font.data.scale(0.1f)

        texture = Texture(Gdx.files.internal("card_bg_2.png"))
        textureRegion = TextureRegion(texture)

        swordTextureRegion = TextureRegion(atlas.findRegion("swords"), 0, 0, swordSpriteWidth.toInt(), swordSpriteHeight.toInt())
        shieldTextureRegion = TextureRegion(atlas.findRegion("shields"), 0, 0, shieldSpriteWidth.toInt(), shieldSpriteHeight.toInt())
    }

    open fun update(delta: Float) {
        stateTimer += delta

        if(needToSwap) {
            if(x.roundToInt() == positionToGoTo.x.roundToInt() && y.roundToInt() == positionToGoTo.y.roundToInt()) {
                needToSwap = false
                positionToGoTo = Vector2(-1f, -1f)
                return
            }
            x = MathUtils.lerp(x, positionToGoTo.x, 0.1f)
            y = MathUtils.lerp(y, positionToGoTo.y, 0.1f)
            updatePosition(x, y)
        }

    }

    private fun updatePosition(x: Float, y: Float) {
        this.x = x
        this.y = y
        setPosition(x, y)
        updateBounds()
    }

    private fun updateBounds() {
        this.setBounds(x, y  , Config.CARD_WIDTH, Config.CARD_HEIGHT)
    }

    override fun draw(batch: Batch) {
        //draw bg
        batch.draw(textureRegion, x + 16, y + 16, Config.CARD_WIDTH - 32, Config.CARD_HEIGHT - 32)

        when(this) {
            is Player -> {
                drawAttack(batch)
                drawHealth(batch, health)
            }
            is Sword -> {
                drawAttack(batch)
            }
            is Shield -> {
                drawHealth(batch, health)
            }
            is Enemy -> {
                drawHealth(batch, health)
            }
            is GoldBar -> {
                drawHealth(batch, worth)
            }
        }

    }

    private fun drawHealth(batch: Batch, worth: Int) {
        font.draw(batch, worth.toString(), x + Config.CARD_WIDTH - 50, y + Config.CARD_HEIGHT - 56 )
        batch.draw(shieldTextureRegion, x + Config.CARD_WIDTH - 50, y + Config.CARD_HEIGHT - 50, shieldSpriteWidth, shieldSpriteHeight)
    }

    private fun drawAttack(batch: Batch) {
        font.draw(batch, attack.toString(), x + 32, y + Config.CARD_HEIGHT - 56 )
        batch.draw(swordTextureRegion, x + 32, y + Config.CARD_HEIGHT - 50, swordSpriteWidth, swordSpriteHeight)
    }

    fun dispose() {

    }

}