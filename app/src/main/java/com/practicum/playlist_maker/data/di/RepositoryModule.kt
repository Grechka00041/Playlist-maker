package com.practicum.playlist_maker.data.di

import com.practicum.playlist_maker.data.NetworkClient
import com.practicum.playlist_maker.data.network.RetrofitNetworkClient
import com.practicum.playlist_maker.data.network.TracksRepositoryImpl
import com.practicum.playlist_maker.domain.api.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<NetworkClient> {
        RetrofitNetworkClient()
    }
    factory<TracksRepository> {
        TracksRepositoryImpl(get())
    }
}