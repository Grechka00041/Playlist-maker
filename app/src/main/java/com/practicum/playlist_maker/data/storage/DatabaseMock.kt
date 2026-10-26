package com.practicum.playlist_maker.data.storage

import com.practicum.playlist_maker.domain.model.Playlist
import com.practicum.playlist_maker.domain.model.Track
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseMock() {
    private val historyList = mutableListOf<String>()
    private val playlists = mutableListOf<Playlist>()
    private val playlistIdCounter = java.util.concurrent.atomic.AtomicLong(0)
    private val trackIdCounter = java.util.concurrent.atomic.AtomicLong(0)
    private val tracks = mutableListOf<Track>()

    private val playlistTrackRefs = mutableListOf<Pair<Long, Long>>()

    fun getHistory(): List<String> {
        return historyList.toList()
    }

    fun addToHistory(word: String) {
        historyList.add(word)
    }

    fun addToFavourite(track: Track) {
        tracks.find { it.id == track.id }?.let {
            it.favorite = true
        }
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        delay(500) // Имитируем задержку загрузки из базы данных
        val filteredPlaylists = mutableListOf<Playlist>()
        playlists.forEach { playlist ->
            val trackIds = playlistTrackRefs
                .filter { it.first == playlist.id }
                .map { it.second }
            val playlistTracks = tracks.filter { it.id in trackIds }
            filteredPlaylists.add(playlist.copy(tracks = playlistTracks))
        }

        emit(filteredPlaylists.toList())
    }

    fun getPlaylist(id: Long): Flow<Playlist?> = flow {
        val playlist = playlists.find { it.id == id }
        if (playlist != null) {
            val trackIds = playlistTrackRefs
                .filter { it.first == id }
                .map { it.second }
            val playlistTracks = tracks.filter { it.id in trackIds }
            emit(playlist.copy(tracks = playlistTracks))
        } else {
            emit(null)
        }
    }

    fun addNewPlaylist(name: String, description: String, coverImageUri: String?) {
        playlists.add(
            Playlist(
                id = playlistIdCounter.incrementAndGet(),
                name = name,
                description = description,
                coverImageUri = coverImageUri,
                tracks = emptyList()
            )
        )
    }

    fun deletePlaylistById(playlistId: Long) {
        playlists.removeIf { it.id == playlistId }
        playlistTrackRefs.removeIf { it.first == playlistId }
    }

    fun addTrackToPlaylist(trackId: Long, playlistId: Long) {
        playlistTrackRefs.add(playlistId to trackId)
    }

    fun removeTrackFromPlaylist(trackId: Long, playlistId: Long) {
        playlistTrackRefs.remove(playlistId to trackId)
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        emit(tracks.find { it.trackName == track.trackName && it.artistName == track.artistName })
    }

    fun insertTrack(track: Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
    }

    fun getFavoriteTracks(): Flow<List<Track>> = flow {
        delay(300) // Имитируем задержку
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }



    fun searchTracks(expression: String): List<Track> {
        return tracks.filter { it.trackName.contains(expression, true) }
    }

    suspend fun getTrackById(id: Long): Track?{
        return tracks.find { it.id == id }
    }

    init {
        tracks.addAll(
            listOf(
                Track(trackIdCounter.incrementAndGet(), "Yesterday (Remastered 2009)", "The Beatles", "2:55", "", false),
                Track(trackIdCounter.incrementAndGet(), "Here Comes The Sun (Remastered 2009)", "The Beatles", "4:01", "", false),
                Track(trackIdCounter.incrementAndGet(), "No Reply", "The Beatles", "5:12", "", false),
                Track(trackIdCounter.incrementAndGet(), "Let It Be", "The Beatles", "6:01", "", false),
                Track(trackIdCounter.incrementAndGet(), "Girl", "The Beatles", "4:11", "", false),
                Track(trackIdCounter.incrementAndGet(), "Michelle", "The Beatles", "3:01", "", false),
                Track(trackIdCounter.incrementAndGet(), "Eleanor Rigby", "The Beatles", "6:12", "", false),
                Track(trackIdCounter.incrementAndGet(), "Come Together", "The Beatles", "4:09", "", false),
            )
        )
        playlists.addAll(
            listOf(
                Playlist(playlistIdCounter.incrementAndGet(), "Best songs 2021", "My favorite tracks", null, emptyList()),
                Playlist(playlistIdCounter.incrementAndGet(), "Summer Party", "Beach vibes", null, emptyList()),
            )
        )
        historyList.addAll(listOf("Rammstein", "Queen", "Bruno Mars"))
    }
}