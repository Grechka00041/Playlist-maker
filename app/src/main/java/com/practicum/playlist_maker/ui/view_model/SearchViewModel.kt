package com.practicum.playlist_maker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlist_maker.domain.api.SearchHistoryRepository
import com.practicum.playlist_maker.domain.api.TracksRepository
import com.practicum.playlist_maker.domain.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {
    sealed class SearchState {
        object Initial: SearchState()
        object Searching: SearchState()
        data class Success(val foundList: List<Track>): SearchState()
        data class Fail(val error: String): SearchState()
    }
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000L)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotBlank()) {
                        search(query)
                    } else {
                        clearSearch()
                    }
                }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun search(request: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                searchHistoryRepository.addToHistory(request)
                val list = tracksRepository.searchTracks(expression = request)
                _searchScreenState.update { SearchState.Success(foundList = list) }
            } catch (e: Exception) {
                val errorMsg = e.localizedMessage ?: "Неизвестная ошибка"
                _searchScreenState.update { SearchState.Fail(errorMsg) }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
        _searchQuery.value = ""
    }

    suspend fun getHistoryList() = searchHistoryRepository.getHistory()

}