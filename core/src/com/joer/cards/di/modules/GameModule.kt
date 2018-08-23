package com.joer.cards.di.modules

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.joer.cards.managers.InventoryManager
import com.joer.cards.models.entities.Player
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

    @Provides
    @Singleton
    fun providesCamera(): OrthographicCamera = OrthographicCamera()

    @Provides
    @Singleton
    fun providesLogger() = Gdx.app.applicationLogger!!


    @Provides
    @Singleton
    fun providesPlayer( atlas: TextureAtlas, inventoryManager: InventoryManager): Player {
        return Player(1, 1, atlas, inventoryManager)
    }

    @Provides
    @Singleton
    fun providesAssetManager(): AssetManager {
        return AssetManager()
    }

}