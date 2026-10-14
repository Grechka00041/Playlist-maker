package com.practicum.playlist_maker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val trackRepository: TracksRepository
) : ViewModel() {
    sealed class SearchState {
        object Initial: SearchState()
        object Searching: SearchState()
        data class Success(val foundList: List<Track>): SearchState()
        data class Fail(val error: String): SearchState()
    }
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    fun searchTracks(query: String) {
        viewModelScope.launch {
            _searchScreenState.value = SearchState.Searching
            try {
                val result = trackRepository.searchTracksContains(query)
                if (result.isNotEmpty()) {
                    _searchScreenState.value = SearchState.Success(result)
                } else {
                    _searchScreenState.value = SearchState.Fail("Ничего не найдено")
                }
            } catch (e: Exception) {
                _searchScreenState.value = SearchState.Fail("Ошибка при поиске: ${e.message}")
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.value = SearchState.Initial
    }
}