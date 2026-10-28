package com.practicum.playlist_maker

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.practicum.playlist_maker.data.di.repositoryModule
import com.practicum.playlist_maker.database.di.databaseModule
import com.practicum.playlist_maker.domain.model.Track
import com.practicum.playlist_maker.ui.view_model.PlaylistDetailsViewModel
import com.practicum.playlist_maker.ui.view_model.PlaylistsListViewModel
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import com.practicum.playlist_maker.ui.view_model.SettingsViewModel
import com.practicum.playlist_maker.ui.view_model.TrackDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


class MyApplication : Application() {
    lateinit var searchHistoryDs: DataStore<Preferences>
        private set
    override fun onCreate() {
        super.onCreate()
        searchHistoryDs = applicationContext.searchHistoryDataStore
        startKoin {
            androidContext(this@MyApplication)

            val viewModelModule = module {
                viewModel {
                    SearchViewModel(
                        tracksRepository = get(),
                        searchHistoryRepository = get()
                    )
                }
            }

            val settingsViewModelModule = module {
                viewModel {
                    SettingsViewModel(
                        get()
                    )
                }
            }

            val playlistsViewModule = module {
                viewModel {
                    PlaylistsListViewModel(
                        playlistsRepository = get(),
                        tracksRepository = get()
                    )
                }
            }

            val trackDetailsViewModel = module {
                viewModel { (track: Track) ->
                    TrackDetailsViewModel(
                        tracksRepository = get(),
                        playlistsRepository = get(),
                        track = track
                    )
                }
            }

            val playlistDetailsViewModel = module {
                viewModel { (playlistId: Long) ->
                    PlaylistDetailsViewModel(
                        playlistsRepository = get(),
                        playlistId = playlistId
                    )
                }
            }

            modules(getDataStoreModule(), settingsViewModelModule, repositoryModule, databaseModule, viewModelModule, playlistsViewModule, trackDetailsViewModel, playlistDetailsViewModel)
        }

    }
    private fun getDataStoreModule() = module {
        single<DataStore<Preferences>> { searchHistoryDs }
    }

}