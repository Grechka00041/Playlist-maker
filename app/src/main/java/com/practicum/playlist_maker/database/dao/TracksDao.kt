package com.practicum.playlist_maker.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlist_maker.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist")
    fun getTrackByNameAndArtist(name: String, artist: String): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE favorite = 1")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

}