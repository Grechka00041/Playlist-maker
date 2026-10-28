package com.practicum.playlist_maker

import android.app.Application
import com.practicum.playlist_maker.data.di.repositoryModule
import com.practicum.playlist_maker.domain.model.Track
import com.practicum.playlist_maker.ui.view_model.PlaylistDetailsViewModel
import com.practicum.playlist_maker.ui.view_model.PlaylistsListViewModel
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import com.practicum.playlist_maker.ui.view_model.TrackDetailsViewModel
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

            modules(repositoryModule, viewModelModule, playlistsViewModule, trackDetailsViewModel, playlistDetailsViewModel)
        }
    }
}