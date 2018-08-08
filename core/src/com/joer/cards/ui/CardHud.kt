package com.joer.cards.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.joer.cards.CardManager
import com.joer.cards.config.Config

class CardHud {

    private val borderShapeRenderer: ShapeRenderer = ShapeRenderer()

    internal fun drawBorder(cardManager: CardManager) {

        borderShapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        val w = Config.CARD_WIDTH
        val h = Config.CARD_HEIGHT
        val x = cardManager.cards[cardManager.playerPosition]?.x ?: 0f
        val y = cardManager.cards[cardManager.playerPosition]?.y ?: 0f

        drawLine(x, y, w, h)

    }

    private fun drawLine(x: Float, y: Float, w: Float, h: Float) {
        val lineWidth = 8
        Gdx.gl20.glLineWidth(lineWidth.toFloat())

        borderShapeRenderer.color = Color.RED
        borderShapeRenderer.line(x, y, x + w, y) // bottom
        borderShapeRenderer.line(x, y + h, w + x, h + y) //top
        borderShapeRenderer.line(x, y, x, y + h) //left
        borderShapeRenderer.line(x + w, y, w + x, h + y) // right
        borderShapeRenderer.end()
    }

}