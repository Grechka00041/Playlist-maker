package com.practicum.playlist_maker.data.di

import com.practicum.playlist_maker.data.NetworkClient
import com.practicum.playlist_maker.data.network.ITunesApiService
import com.practicum.playlist_maker.data.network.RetrofitNetworkClient
import com.practicum.playlist_maker.data.storage.DatabaseMock
import com.practicum.playlist_maker.domain.impl.TracksRepositoryImpl
import com.practicum.playlist_maker.data.storage.Storage
import com.practicum.playlist_maker.domain.api.PlaylistsRepository
import com.practicum.playlist_maker.domain.api.SearchHistoryRepository
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.impl.PlaylistsRepositoryImpl
import com.practicum.playlist_maker.domain.impl.SearchHistoryRepositoryImpl
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {


    single<ITunesApiService> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)
    }

    single<DatabaseMock> { DatabaseMock() }

    factory<NetworkClient> { RetrofitNetworkClient(get()) }

    factory<TracksRepository> {
        TracksRepositoryImpl(get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }

    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(get())
    }
}