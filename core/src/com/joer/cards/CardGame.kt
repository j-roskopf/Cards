package com.joer.cards

import com.badlogic.gdx.Game
import com.joer.cards.di.components.DaggerGameComponent
import com.joer.cards.di.components.GameComponent
import com.joer.cards.di.modules.GameModule
import com.joer.cards.screens.PlayScreen
import javax.inject.Inject

class CardGame : Game() {

    @Inject
    lateinit var playScreen: PlayScreen

    companion object {
        lateinit var component: GameComponent
    }

    override fun create() {
        component = DaggerGameComponent.builder()
                .gameModule(GameModule())
                .build()
        component.inject(this)


        setScreen(playScreen)
    }

}


