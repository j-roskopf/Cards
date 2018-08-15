package com.joer.cards.models.entities

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import com.joer.cards.config.Config
import com.joer.cards.models.Card
import javax.inject.Inject

enum class PLAYER_STATE { IDLE, ATTACKING, DEAD }

class Player @Inject constructor(xCord: Int, yCord: Int, atlas: TextureAtlas) : Card(xCord, yCord, atlas) {

    private var playerIdle: Animation<TextureRegion>
    private var firstPlayerAttack: Animation<TextureRegion>
    private var secondPlayerAttack: Animation<TextureRegion>
    private var playerDead: Animation<TextureRegion>

    private val spriteWidth = 50
    private val spriteHeight = 37

    private var currentState = PLAYER_STATE.IDLE
    private var previousState = PLAYER_STATE.IDLE

    init {

        val frames = Array<TextureRegion>()
        for (i in 3 until 7) {
            frames.add(TextureRegion(atlas.findRegion("adventurer"), i * spriteWidth, 5 * spriteHeight, spriteWidth, spriteHeight))
        }
        playerIdle = Animation(0.15f, frames)
        frames.clear()

        for (i in 0 until 7) {
            frames.add(TextureRegion(atlas.findRegion("adventurer"), i * spriteWidth, 6 * spriteHeight, spriteWidth, spriteHeight))
        }
        firstPlayerAttack = Animation(0.1f, frames)
        frames.clear()

        for (i in 0 until 4) {
            frames.add(TextureRegion(atlas.findRegion("adventurer"), i * spriteWidth, 7 * spriteHeight, spriteWidth, spriteHeight))
        }
        secondPlayerAttack = Animation(0.1f, frames)
        frames.clear()

        for (i in 0 until 7) {
            frames.add(TextureRegion(atlas.findRegion("adventurer_hand"), i * spriteWidth, 5 * spriteHeight, spriteWidth, spriteHeight))
        }
        playerDead = Animation(0.25f, frames)
        frames.clear()
    }

    override fun draw(batch: Batch, delta: Float) {
        super.draw(batch)
        batch.draw(getCurrentFrame(delta), x, y + Config.CARD_HEIGHT / 8, Config.CARD_WIDTH, Config.CARD_HEIGHT / 2)
    }

    private fun getCurrentFrame(delta: Float): TextureRegion {
        val region = when (currentState) {
            PLAYER_STATE.ATTACKING -> firstPlayerAttack.getKeyFrame(stateTimer)
            PLAYER_STATE.IDLE -> playerIdle.getKeyFrame(stateTimer, true)
            PLAYER_STATE.DEAD -> playerDead.getKeyFrame(stateTimer, false)
        }

        if (currentState == previousState) {
            stateTimer += delta
        } else {
            stateTimer = 0f
        }

        when (currentState) {
            PLAYER_STATE.ATTACKING -> {
                if (firstPlayerAttack.isAnimationFinished(stateTimer)) {
                    currentState = PLAYER_STATE.IDLE
                }
            }
            PLAYER_STATE.IDLE -> playerIdle.getKeyFrame(stateTimer, true)
            PLAYER_STATE.DEAD -> playerDead.getKeyFrame(stateTimer, false)
        }

        previousState = currentState

        return region
    }

    fun attack() {
        currentState = PLAYER_STATE.ATTACKING
    }

    fun die() {
        currentState = PLAYER_STATE.DEAD
    }

    fun addAmountToHealth(amount: Int) {
        this.health += amount
    }

    fun addAmountToAttack(amount: Int) {
        this.attack += amount
    }
}