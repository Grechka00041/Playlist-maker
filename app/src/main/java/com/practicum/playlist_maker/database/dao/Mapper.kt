package com.practicum.playlist_maker.database.dao

import com.practicum.playlist_maker.database.entity.TrackEntity
import com.practicum.playlist_maker.domain.model.Playlist
import com.practicum.playlist_maker.domain.model.Track

fun TrackEntity.toTrack(): Track {
    return Track(
        id = this.trackId,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        favorite = this.favorite,
        image = this.image
    )
}

fun Track.toEntity(): TrackEntity {
    return TrackEntity(
        trackId = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        image = this.image,
        favorite = this.favorite,
    )
}

fun PlaylistWithTracks.toDomainPlaylist(): Playlist {
    return Playlist(
        id = playlist.playlistId,
        name = playlist.name,
        description = playlist.description,
        coverImageUri = playlist.coverImageUri,
        tracks = tracks.map { it.toTrack() }
    )
}