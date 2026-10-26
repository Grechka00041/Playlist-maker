package com.practicum.playlist_maker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlist_maker.domain.api.PlaylistsRepository
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.model.Playlist
import com.practicum.playlist_maker.domain.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrackDetailsViewModel(
    private val tracksRepository: TracksRepository,
    private val playlistsRepository: PlaylistsRepository,
    private val track: Track
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    private val _addTrackResult = MutableSharedFlow<AddTrackResult>()
    val addTrackResult: SharedFlow<AddTrackResult> = _addTrackResult.asSharedFlow()

    init {
        loadTrackDetails()
        observePlaylists()
    }

    private fun loadTrackDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = UiState.Loading
                _uiState.value = UiState.Success(track)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    private fun observePlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.getAllPlaylists().collect { list ->
                _playlists.value = list
            }
        }
    }

    fun onFavoriteClicked(track: Track) {
        val newFavoriteStatus = !track.favorite
        val updatedTrack = track.copy(favorite = newFavoriteStatus)
        _uiState.value = UiState.Success(updatedTrack)

        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(track, newFavoriteStatus)
        }
    }

    fun onAddTrackToPlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            val isAlreadyInPlaylist = playlist.tracks.any { it.id == track.id }
            if (isAlreadyInPlaylist) {
                _addTrackResult.emit(AddTrackResult.AlreadyInPlaylist(playlist.name))
            } else {
                tracksRepository.insertTrack(track)
                tracksRepository.addTrackToPlaylist(track.id, playlist.id)
                _addTrackResult.emit(AddTrackResult.Added(playlist.name))
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val track: Track) : UiState()
        data class Error(val message: String) : UiState()
    }

    sealed class AddTrackResult {
        data class Added(val playlistName: String) : AddTrackResult()
        data class AlreadyInPlaylist(val playlistName: String) : AddTrackResult()
    }
}
