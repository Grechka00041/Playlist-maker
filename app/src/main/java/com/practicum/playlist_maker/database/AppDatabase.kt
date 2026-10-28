package com.practicum.playlist_maker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlist_maker.database.dao.PlaylistTrackDao
import com.practicum.playlist_maker.database.dao.PlaylistsDao
import com.practicum.playlist_maker.database.dao.TracksDao
import com.practicum.playlist_maker.database.entity.PlaylistEntity
import com.practicum.playlist_maker.database.entity.PlaylistTrackCrossRef
import com.practicum.playlist_maker.database.entity.TrackEntity

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackCrossRef::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tracksDao(): TracksDao
    abstract fun playlistsDao(): PlaylistsDao
    abstract fun playlistTrackDao(): PlaylistTrackDao

}