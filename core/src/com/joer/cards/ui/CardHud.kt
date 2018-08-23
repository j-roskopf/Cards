package com.joer.cards.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.joer.cards.config.Config
import com.joer.cards.managers.InventoryManager
import com.joer.cards.models.entities.Player
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardHud @Inject constructor(private val player: Player, private val inventoryManager: InventoryManager){

    private val borderShapeRenderer: ShapeRenderer = ShapeRenderer()

        internal fun drawBorder() {

        borderShapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        val w = Config.CARD_WIDTH
        val h = Config.CARD_HEIGHT
        val x = player.x
        val y = player.y

        drawLine(x, y, w, h)
    }

    private fun drawLine(x: Float, y: Float, w: Float, h: Float) {
        val lineWidth = 4
        Gdx.gl20.glLineWidth(lineWidth.toFloat())

        if(inventoryManager.ringActive()) {
            borderShapeRenderer.color = Color.BLUE
        } else {
            borderShapeRenderer.color = Color.RED
        }
        borderShapeRenderer.rect(x, y, w, h)
        borderShapeRenderer.end()

    }

}