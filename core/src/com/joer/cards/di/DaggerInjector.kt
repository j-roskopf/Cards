package com.joer.cards.di

import com.joer.cards.di.components.DaggerGameComponent
import com.joer.cards.di.components.GameComponent


object DaggerInjector {

    fun get(): GameComponent {
        return DaggerGameComponent.builder()
                .build()
    }

}