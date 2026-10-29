package com.practicum.playlist_maker.database.di

import androidx.room.Room
import com.practicum.playlist_maker.database.preferences.SearchHistoryPreferences
import com.practicum.playlist_maker.database.AppDatabase
import com.practicum.playlist_maker.database.dao.PlaylistTrackDao
import com.practicum.playlist_maker.database.dao.PlaylistsDao
import com.practicum.playlist_maker.database.dao.TracksDao
import com.practicum.playlist_maker.domain.api.SearchHistoryRepository
import com.practicum.playlist_maker.domain.impl.SearchHistoryRepositoryImpl
import com.practicum.playlist_maker.database.preferences.ThemeManager
import org.koin.dsl.module


val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "playlist_db").fallbackToDestructiveMigration(true).build()
    }
    single<ThemeManager> { ThemeManager(get()) }

    single<SearchHistoryPreferences> { SearchHistoryPreferences(get()) }
    single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }

    factory<TracksDao> { get<AppDatabase>().tracksDao() }
    factory<PlaylistsDao> { get<AppDatabase>().playlistsDao() }
    factory<PlaylistTrackDao> { get<AppDatabase>().playlistTrackDao() }
}