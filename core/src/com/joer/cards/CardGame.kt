package com.joer.cards

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.joer.cards.di.components.DaggerGameComponent
import com.joer.cards.di.components.GameComponent
import com.joer.cards.di.modules.GameModule
import com.joer.cards.screens.PlayScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardGame @Inject constructor(): Game() {

    // sigmoid function for probability

    @Inject lateinit var assetManager: AssetManager

    companion object {
        lateinit var component: GameComponent
    }

    override fun create() {
        component = DaggerGameComponent.builder()
                .gameModule(GameModule())
                .build()

        component.inject(this)

        assetManager.load("skins/uiskin.json", Skin::class.java)
        assetManager.load("icons/icons.atlas", TextureAtlas::class.java)
        assetManager.finishLoading()

        setScreen(PlayScreen(this))
    }
}


