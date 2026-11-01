package com.practicum.playlist_maker.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.playlist_maker.R
import com.practicum.playlist_maker.domain.model.Track
import com.practicum.playlist_maker.ui.view_model.PlaylistDetailsViewModel

@Composable
fun PlaylistDetailsScreen(
    onNavigateBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    playlistDetailsViewModel: PlaylistDetailsViewModel
) {
    val playlist by playlistDetailsViewModel.playlistState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (playlist == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val currentPlaylist = playlist!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = stringResource(R.string.back_button),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onNavigateBack() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    if (!currentPlaylist.coverImageUri.isNullOrEmpty() && currentPlaylist.coverImageUri != "uri") {
                        AsyncImage(
                            model = currentPlaylist.coverImageUri,
                            contentDescription = currentPlaylist.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.ic_music),
                            contentDescription = currentPlaylist.name,
                            modifier = Modifier.size(96.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = currentPlaylist.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp
                )

                if (currentPlaylist.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentPlaylist.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                val totalMinutes = calculateTotalDurationMinutes(currentPlaylist.tracks)
                val tracksCountText = formatTracksCount(currentPlaylist.tracks.size)
                Text(
                    text = "$totalMinutes минут • $tracksCountText",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (currentPlaylist.tracks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "В этом плейлисте нет треков",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(currentPlaylist.tracks.size) { index ->
                            val track = currentPlaylist.tracks[index]
                            PlaylistTrackItem(track = track, onClick = { onTrackClick(track) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlaylistTrackItem(track: Track, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = track.image,
            placeholder = painterResource(R.drawable.ic_music),
            error = painterResource(R.drawable.ic_music),
            contentDescription = track.trackName,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = track.trackName,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = track.artistName,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
        Text(
            text = track.trackTime,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun calculateTotalDurationMinutes(tracks: List<Track>): Long {
    var totalSeconds = 0L
    for (track in tracks) {
        val parts = track.trackTime.split(":")
        if (parts.size == 2) {
            val mins = parts[0].toLongOrNull() ?: 0L
            val secs = parts[1].toLongOrNull() ?: 0L
            totalSeconds += mins * 60 + secs
        }
    }
    return totalSeconds / 60
}

private fun formatTracksCount(count: Int): String {
    val remainder10 = count % 10
    val remainder100 = count % 100
    return when {
        remainder100 in 11..14 -> "$count треков"
        remainder10 == 1 -> "$count трек"
        remainder10 in 2..4 -> "$count трека"
        else -> "$count треков"
    }
}