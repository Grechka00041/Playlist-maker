package com.practicum.playlist_maker

import android.app.Application
import com.practicum.playlist_maker.data.di.repositoryModule
import com.practicum.playlist_maker.data.storage.DatabaseMock
import com.practicum.playlist_maker.domain.api.SearchHistoryRepository
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.impl.SearchHistoryRepositoryImpl
import com.practicum.playlist_maker.domain.impl.TracksRepositoryImpl
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import org.koin.android.ext.android.get
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {

            val viewModelModule = module {
                viewModel {
                    SearchViewModel(
                        tracksRepository = get(),
                        searchHistoryRepository = get()
                    )
                }
            }

            modules(repositoryModule, viewModelModule)
        }
    }
}