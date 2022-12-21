package com.maks.courseproject.di

import com.maks.courseproject.ui.fragments.characters_screens.characters.CharactersViewModelFactory
import com.maks.courseproject.ui.fragments.characters_screens.characters_details.DetailsCharactersViewModelFactory
import com.maks.courseproject.ui.fragments.episodes_screens.episodes.EpisodesViewModelFactory
import com.maks.courseproject.ui.fragments.episodes_screens.episodes_details.DetailsEpisodesViewModelFactory
import com.maks.courseproject.ui.fragments.location_screens.location.LocationsViewModelFactory
import com.maks.courseproject.ui.fragments.location_screens.location_details.DetailsLocationViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoriesModule::class])
interface AppComponent {
    fun charactersViewModelsFactory(): CharactersViewModelFactory

    fun locationViewModelFactory(): LocationsViewModelFactory

    fun episodesViewModelFactory(): EpisodesViewModelFactory

    fun detailsCharacterViewModelFactory(): DetailsCharactersViewModelFactory

    fun detailsLocationViewModelFactory(): DetailsLocationViewModelFactory

    fun detailsEpisodeViewModelFactory(): DetailsEpisodesViewModelFactory
}