package com.joer.cards.di.components

import com.joer.cards.CardGame
import com.joer.cards.di.modules.GameModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(GameModule::class)])
interface GameComponent {
    fun inject(cardGame: CardGame)
}