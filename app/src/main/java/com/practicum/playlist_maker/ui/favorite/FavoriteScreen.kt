package com.practicum.playlist_maker.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.playlist_maker.R
import com.practicum.playlist_maker.domain.model.Track
import com.practicum.playlist_maker.ui.view_model.FavoritesViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel,
    onNavigateBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    isDark: Boolean
) {
    val favoriteList by viewModel.favoriteList.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onNavigateBack() },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_button),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.favourite),
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(modifier = Modifier.fillMaxSize().weight(1f)) {
            if (favoriteList.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ){
                    Icon(
                        painter = painterResource(
                            if (isDark) R.drawable.no_files_dark
                            else R.drawable.no_files
                        ),
                        contentDescription = stringResource(R.string.no_tracks_found),
                        modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(R.string.nothing_found),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(4.dp)
                    )
                }

            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(favoriteList.size) { index ->
                        TrackListItem(
                            track = favoriteList[index],
                            onClick = { onTrackClick(favoriteList[index]) },
                            onLongClick = {
                                viewModel.toggleFavorite(favoriteList[index], false)
                            }
                        )
                        HorizontalDivider(thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun TrackListItem(
    track: Track,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = track.image,
            contentDescription = "Track ${track.trackName}",
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
            error = painterResource(id = android.R.drawable.ic_menu_gallery)
        )
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = track.trackName,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))

            Row {
                Text(
                    text = track.artistName,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = " • ",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = track.trackTime,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}