package com.joer.cards.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.joer.cards.CardGame
import com.joer.cards.config.Config

class KotlinDesktopLauncher {
    companion object {
        @JvmStatic
        fun main(arg: Array<String>) {
            val config = LwjglApplicationConfiguration()
            config.width = Config.GAME_WIDTH.toInt()
            config.height = Config.GAME_HEIGHT.toInt()
            LwjglApplication(CardGame(), config)
        }
    }

}
