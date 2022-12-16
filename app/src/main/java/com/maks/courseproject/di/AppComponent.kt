package com.maks.courseproject.di

import com.maks.courseproject.ui.fragments.characters.CharactersViewModelFactory
import com.maks.courseproject.ui.fragments.characters_details.DetailsCharactersViewModelFactory
import com.maks.courseproject.ui.fragments.episodes.EpisodesViewModelFactory
import com.maks.courseproject.ui.fragments.episodes_details.DetailsEpisodesViewModelFactory
import com.maks.courseproject.ui.fragments.location.LocationsViewModelFactory
import com.maks.courseproject.ui.fragments.location_details.DetailsLocationViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RemoteRepositoriesModule::class,DatabaseModule::class])

interface AppComponent {
    fun charactersViewModelsFactory(): CharactersViewModelFactory

    fun locationViewModelFactory(): LocationsViewModelFactory

    fun episodesViewModelFactory(): EpisodesViewModelFactory

    fun detailsCharacterViewModelFactory(): DetailsCharactersViewModelFactory

    fun detailsLocationViewModelFactory(): DetailsLocationViewModelFactory

    fun detailsEpisodeViewModelFactory(): DetailsEpisodesViewModelFactory
}