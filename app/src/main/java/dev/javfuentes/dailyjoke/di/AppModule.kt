package dev.javfuentes.dailyjoke.di

import android.content.Context
import dev.javfuentes.dailyjoke.data.datasource.FavoritesDataSource
import dev.javfuentes.dailyjoke.data.datasource.FavoritesDataSourceImpl
import dev.javfuentes.dailyjoke.data.repository.JokeRepository
import dev.javfuentes.dailyjoke.data.repository.JokeRepositoryImpl
import dev.javfuentes.dailyjoke.network.NetworkModule
import dev.javfuentes.dailyjoke.viewmodel.JokeViewModelFactory

object AppModule {
    
    fun provideFavoritesDataSource(context: Context): FavoritesDataSource {
        return FavoritesDataSourceImpl(context)
    }
    
    fun provideJokeRepository(context: Context): JokeRepository {
        return JokeRepositoryImpl(
            apiService = NetworkModule.jokeApiService,
            favoritesDataSource = provideFavoritesDataSource(context)
        )
    }
    
    fun provideJokeViewModelFactory(context: Context): JokeViewModelFactory {
        return JokeViewModelFactory(provideJokeRepository(context))
    }
}