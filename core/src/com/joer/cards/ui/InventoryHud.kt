package com.joer.cards.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.joer.cards.InventoryManager
import javax.inject.Inject

class InventoryHud @Inject constructor(){
    @Inject lateinit var camera: OrthographicCamera
    @Inject lateinit var inventoryManager: InventoryManager

    private val borderShapeRenderer: ShapeRenderer = ShapeRenderer()

    internal fun drawLine(x: Float, y: Float, w: Float, h: Float) {
        borderShapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        val lineWidth = 4
        Gdx.gl20.glLineWidth(lineWidth.toFloat())

        borderShapeRenderer.color = Color.RED
        borderShapeRenderer.rect(x, y, w, h)
        borderShapeRenderer.end()
    }

    fun draw() {
        inventoryManager.items.forEach {  item ->
            if(item.isCurrentlySelected) {
                drawLine(item.x, item.y, item.width, item.height)
            }
        }
    }

}