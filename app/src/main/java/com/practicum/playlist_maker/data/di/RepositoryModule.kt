package com.practicum.playlist_maker.data.di

import com.practicum.playlist_maker.data.NetworkClient
import com.practicum.playlist_maker.data.network.RetrofitNetworkClient
import com.practicum.playlist_maker.domain.impl.TracksRepositoryImpl
import com.practicum.playlist_maker.data.storage.MockStorage
import com.practicum.playlist_maker.data.storage.Storage
import com.practicum.playlist_maker.domain.api.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<Storage> { MockStorage() }

    factory<NetworkClient> { RetrofitNetworkClient(get()) }

    factory<TracksRepository> { TracksRepositoryImpl(get()) }
}