package com.practicum.playlist_maker.data.storage

class DatabaseMock() {

    private val historyList = mutableListOf<String>()

    fun getHistory(): List<String> = historyList.toList()

    fun addToHistory(word: String) {
        historyList.add(word)
    }
}