package com.practicum.playlist_maker.data.di

import com.practicum.playlist_maker.data.NetworkClient
import com.practicum.playlist_maker.data.network.RetrofitNetworkClient
import com.practicum.playlist_maker.data.storage.DatabaseMock
import com.practicum.playlist_maker.domain.impl.TracksRepositoryImpl
import com.practicum.playlist_maker.data.storage.MockStorage
import com.practicum.playlist_maker.data.storage.Storage
import com.practicum.playlist_maker.domain.api.SearchHistoryRepository
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.impl.SearchHistoryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<Storage> { MockStorage() }
    single<DatabaseMock> { DatabaseMock() }

    factory<NetworkClient> { RetrofitNetworkClient(get()) }

    factory<TracksRepository> {
        TracksRepositoryImpl(get(), get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }
}