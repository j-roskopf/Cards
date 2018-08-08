package com.joer.cards.di.components

import com.joer.cards.CardGame
import com.joer.cards.CardManager
import com.joer.cards.di.modules.GameModule
import com.joer.cards.screens.PlayScreen
import com.joer.cards.ui.GameHud
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(GameModule::class)])
interface GameComponent {
    fun inject(cardGame: CardGame)
    fun inject(playScreen: PlayScreen)
    fun inject(manager: CardManager)
    fun inject(hud: GameHud)
}