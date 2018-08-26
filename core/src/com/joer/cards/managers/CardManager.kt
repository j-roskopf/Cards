package com.joer.cards.managers

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.joer.cards.CardGame
import com.joer.cards.models.Card
import com.joer.cards.models.entities.Enemy
import com.joer.cards.models.entities.Player
import com.joer.cards.models.entities.Shield
import com.joer.cards.models.entities.Sword
import com.joer.cards.models.items.Necklace
import com.joer.cards.models.items.SpellBook
import com.joer.cards.models.items.potion.HealthPotion
import com.joer.cards.screens.GameOverScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardManager @Inject constructor() {

    @Inject lateinit var inventoryManager: InventoryManager
    @Inject lateinit var player: Player
    @Inject lateinit var atlas: TextureAtlas
    @Inject lateinit var spriteBatch: SpriteBatch
    @Inject lateinit var assetManager: AssetManager

    internal var cards: HashMap<Int, Card> = HashMap()
    internal var firstSelectedPosition = -1

    companion object {
        const val GRID_WIDTH = 4
        const val GRID_HEIGHT = 4
    }

    var playerPosition = 5
    var game: CardGame? = null

    var deathTimer = -1f

    init {
        CardGame.component.inject(this)
    }

    /**
     *      |0,2|1,2|2,2|
     *      |0,1|1,1|2,1|
     *      |0,0|1,0|2,0|
     */
    internal fun createInitialCards() {
        cards = HashMap()

        player = Player(1, 1, atlas, inventoryManager)

        cards[0] = Sword(0, 0, atlas)
        cards[1] = Enemy(1, 0, atlas)
        cards[2] = Shield(2, 0, atlas)
        cards[3] = Shield(3, 0, atlas)

        cards[4] = Enemy(0, 1, atlas)
        cards[playerPosition] = player
        cards[6] = Necklace(2, 1, atlas)
        cards[7] = Necklace(3, 1, atlas)

        cards[8] = HealthPotion(0, 2, atlas)
        cards[9] = Necklace(1, 2, atlas)
        cards[10] = SpellBook(2, 2, atlas)
        cards[11] = SpellBook(3, 2, atlas)

        cards[12] = HealthPotion(0, 3, atlas)
        cards[13] = Necklace(1, 3, atlas)
        cards[14] = SpellBook(2, 3, atlas)
        cards[15] = SpellBook(3, 3, atlas)

    }

    private fun addCardAtPosition(psn: Int) {
        val x = psn % GRID_WIDTH
        val y = psn / GRID_WIDTH
        val choice = (Math.random() * 9).toInt()
        when(choice) {
/*            0 -> cards[psn] = Enemy(x, y, atlas)
            1 -> cards[psn] = Sword(x, y, atlas)
            2 -> cards[psn] = Shield(x, y, atlas)
            3 -> cards[psn] = GoldBar(x, y, atlas)
            4 -> cards[psn] = Necklace(x, y, atlas)
            5 -> cards[psn] = SpellBook(x, y, atlas)
            6 -> cards[psn] = HealthPotion(x, y, atlas)
            7 -> cards[psn] = DamagePotion(x, y, atlas)
            8 -> cards[psn] = Ring(x, y, atlas)
            else -> cards[psn] = Sword(x, y, atlas)*/

            0 -> cards[psn] = Enemy(x, y, atlas)
            1 -> cards[psn] = Enemy(x, y, atlas)
            2 -> cards[psn] = Enemy(x, y, atlas)
            3 -> cards[psn] = Enemy(x, y, atlas)
            4 -> cards[psn] = Enemy(x, y, atlas)
            5 -> cards[psn] = Enemy(x, y, atlas)
            6 -> cards[psn] = Enemy(x, y, atlas)
            7 -> cards[psn] = Enemy(x, y, atlas)
            8 -> cards[psn] = Enemy(x, y, atlas)
            else -> cards[psn] = Enemy(x, y, atlas)
        }
    }

    internal fun swapCards() {
        val positionForPlayerToMoveTo = Vector2(cards[firstSelectedPosition]?.x ?: 0f, cards[firstSelectedPosition]?.y ?: 0f)
        cards[playerPosition]?.positionToGoTo = positionForPlayerToMoveTo
        cards[playerPosition]?.needToSwap = true

        cards[playerPosition]?.let {
            cards[firstSelectedPosition] = it
            val oldPlayerPosition = playerPosition
            playerPosition = firstSelectedPosition
            addCardAtPosition(oldPlayerPosition)
        }

        inventoryManager.handleTurnsActive()
    }

    /**
     * TODO - Joe - Refactor this outta here and into the player class
     */
    internal fun calculateDamage(tileClickedOn: Enemy): Boolean {
        val currentHealth = tileClickedOn.health
        tileClickedOn.health = tileClickedOn.health - ((cards[playerPosition] as Player).attack)
        val leftoverAttack = (cards[playerPosition]?.attack ?: 0) - currentHealth

        when {
            cards[playerPosition]?.attack ?: 0 <= 0 -> {
                cards[playerPosition]?.health = (cards[playerPosition]?.health ?: 0) - currentHealth

                if(cards[playerPosition]?.health ?: 0 <= 0) {
                    (cards[playerPosition] as Player).die()
                    deathTimer = 0f
                    return false
                }
                return true
            }
            leftoverAttack < 0 -> {
                cards[playerPosition]?.attack = 0
                return false
            }
            leftoverAttack == 0 -> {
                cards[playerPosition]?.attack = leftoverAttack
                return true
            }
            else -> {
                cards[playerPosition]?.attack = (cards[playerPosition]?.attack ?: 0) - currentHealth
                return true
            }
        }
    }

    fun update(delta: Float) {
        if(deathTimer >= 0) {
            deathTimer += delta
        }

        if(deathTimer > 1) {
            game?.screen = GameOverScreen(spriteBatch, assetManager, game!!)
            deathTimer = -1f
        }
    }

    fun dealDamageToCardAtPosition(position: Int, spellBoolActiveDamage: Int) {
        val currentTile = cards[position]
        currentTile?.let {
            it.health = it.health - spellBoolActiveDamage
            if(it.health <= 0) {
                addCardAtPosition(position)
            }
        }
    }
}