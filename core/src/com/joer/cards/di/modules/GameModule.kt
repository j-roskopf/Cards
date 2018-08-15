package com.joer.cards.di.modules

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.joer.cards.managers.CardManager
import com.joer.cards.models.entities.Player
import com.joer.cards.ui.CardHud
import com.joer.cards.ui.GameHud
import com.joer.cards.util.FrameRate
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GameModule {
    @Provides
    @Singleton
    fun providesSpriteBatch(): SpriteBatch = SpriteBatch()

    @Provides
    @Singleton
    fun providesTextureAtlas(): TextureAtlas = TextureAtlas("pack.atlas")

    val camera = OrthographicCamera()

    @Provides
    @Singleton
    fun providesCamera(): OrthographicCamera = camera

    @Provides
    @Singleton
    fun providesLogger() = Gdx.app.applicationLogger

    @Provides
    @Singleton
    fun providesCardHud(cardManager: CardManager) = CardHud(cardManager)


    @Provides
    @Singleton
    fun providesFrameRate(): FrameRate {
        return FrameRate()
    }

    @Provides
    @Singleton
    fun providesGameHud(batch: SpriteBatch, atlas: TextureAtlas): GameHud {
        return GameHud(batch, atlas)
    }

    @Provides
    @Singleton
    fun providesPlayer( atlas: TextureAtlas): Player {
        return Player(1, 1, atlas)
    }

    @Provides
    @Singleton
    fun providesAssetManager(): AssetManager {
        return AssetManager()
    }

}