package com.practicum.playlist_maker

import android.app.Application
import com.practicum.playlist_maker.data.di.repositoryModule
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            val viewModelModule = module {
                viewModel {
                    SearchViewModel(get())
                }
            }
            modules(repositoryModule, viewModelModule)
        }
    }
}