package com.maks.courseproject.di

import com.maks.courseproject.ui.fragments.characters.CharactersViewModelFactory
import com.maks.courseproject.ui.fragments.episodes.EpisodesViewModelFactory
import com.maks.courseproject.ui.fragments.location.LocationsViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoriesModule::class])
interface AppComponent {
    fun charactersViewModelsFactory(): CharactersViewModelFactory

    fun locationViewModelFactory() : LocationsViewModelFactory

    fun episodesViewModelFactory(): EpisodesViewModelFactory
}