package com.joer.cards.di.modules

import com.badlogic.gdx.ApplicationLogger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.joer.cards.CardManager
import com.joer.cards.InventoryManager
import com.joer.cards.screens.PlayScreen
import com.joer.cards.ui.CardHud
import com.joer.cards.ui.GameHud
import com.joer.cards.util.FrameRate
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
public class GameModule {
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
    fun providesMainPlayScreen(spriteBatch: SpriteBatch,
                               camera: OrthographicCamera,
                               cardManager: CardManager,
                               logger: ApplicationLogger,
                               cardHud: CardHud): PlayScreen =
            PlayScreen(spriteBatch,
                    camera,
                    cardManager,
                    logger,
                    cardHud)

    @Provides
    @Singleton
    fun providesCardManager(textureAtlas: TextureAtlas, logger: ApplicationLogger): CardManager {
        return CardManager(textureAtlas, logger)
    }

    @Provides
    @Singleton
    fun providesFrameRate(): FrameRate {
        return FrameRate()
    }

    private val inventoryManager = InventoryManager()

    @Provides
    @Singleton
    fun providesInventoryManager(): InventoryManager {
        return inventoryManager
    }

    @Provides
    @Singleton
    fun providesGameHud(batch: SpriteBatch, atlas: TextureAtlas): GameHud {
        return GameHud(batch, atlas)
    }
}