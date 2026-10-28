package com.practicum.playlist_maker.ui.track

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.practicum.playlist_maker.R
import com.practicum.playlist_maker.domain.model.Playlist
import com.practicum.playlist_maker.domain.model.Track
import com.practicum.playlist_maker.ui.playlist.PlaylistListItem
import com.practicum.playlist_maker.ui.theme.BlackPrimary
import com.practicum.playlist_maker.ui.theme.GreyLight
import com.practicum.playlist_maker.ui.theme.GreyPrimary
import com.practicum.playlist_maker.ui.theme.WhitePrimary
import com.practicum.playlist_maker.ui.theme.YsTextRegular
import com.practicum.playlist_maker.ui.view_model.TrackDetailsViewModel

@Composable
fun TrackDetailsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    trackDetailsViewModel: TrackDetailsViewModel
) {
    val uiState by trackDetailsViewModel.uiState.collectAsStateWithLifecycle()
    val playlists by trackDetailsViewModel.playlists.collectAsStateWithLifecycle()

    var isSheetVisible by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize().background(WhitePrimary)) {
        when (val state = uiState) {
            is TrackDetailsViewModel.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is TrackDetailsViewModel.UiState.Error -> {
                Text(state.message, modifier = Modifier.align(Alignment.Center))
            }
            is TrackDetailsViewModel.UiState.Success -> {
                TrackDetailsContent(
                    track = state.track,
                    onNavigateBack = onNavigateBack,
                    onToggleFavorite = { trackDetailsViewModel.onFavoriteClicked(state.track) },
                    onAddToPlaylist = { isSheetVisible = true }
                )
            }
        }
    }

    if (isSheetVisible) {
        AddToPlaylistModalSheet(
            playlists = playlists,
            onDismiss = { isSheetVisible = false },
            onPlaylistSelected = { playlist ->
                trackDetailsViewModel.onAddTrackToPlaylist(playlist)
                isSheetVisible = false
            }
        )
    }
}

@Composable
fun TrackDetailsContent(
    track: Track,
    onNavigateBack: () -> Unit,
    onToggleFavorite: () -> Unit,
    onAddToPlaylist: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, bottom = 12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = stringResource(R.string.back_button),
                tint = BlackPrimary,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNavigateBack() }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            AsyncImage(
                model = track.image,
                placeholder = painterResource(R.drawable.ic_music),
                error = painterResource(R.drawable.ic_music),
                contentDescription = stringResource(R.string.track_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Text(
            text = track.trackName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
        )

        Text(
            text = track.artistName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(51.dp)
                    .clip(CircleShape)
                    .background(GreyLight)
                    .clickable { onAddToPlaylist() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddBox,
                    contentDescription = stringResource(R.string.add_playlist),
                    tint = BlackPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(51.dp)
                    .clip(CircleShape)
                    .background(GreyLight)
                    .clickable { onToggleFavorite() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.favourite_icon),
                    contentDescription = stringResource(R.string.favourite),
                    tint = if (track.favorite) Color.Red else BlackPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.duration),
                style = TextStyle(
                    fontFamily = YsTextRegular,
                    fontSize = 13.sp,
                    color = GreyPrimary
                )
            )

            Text(
                text = track.trackTime,
                style = TextStyle(
                    fontFamily = YsTextRegular,
                    fontSize = 13.sp,
                    color = BlackPrimary
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToPlaylistModalSheet(
    playlists: List<Playlist>,
    onDismiss: () -> Unit,
    onPlaylistSelected: (Playlist) -> Unit
){
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(WhitePrimary)
            )
        }
    ) {
        Text(
            text = stringResource(R.string.add_to_playlist),
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge,
            color = BlackPrimary
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(playlists.size) { index ->
                val playlist = playlists[index]
                PlaylistListItem(
                    playlist = playlist,
                    onClick = { onPlaylistSelected(playlist) }
                )
                if (index < playlists.lastIndex) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }

            if (playlists.isEmpty()) {
                item {
                    Text(
                        text = "У вас пока нет плейлистов",
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = GreyPrimary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TrackDetailsContentPreview() {
    TrackDetailsContent(
        track = Track(
            id = 1,
            trackName = "Yesterday (Remastered 2009)",
            artistName = "The Beatles",
            trackTime = "2:55",
            image = "",
            favorite = false
        ),
        onNavigateBack = {},
        onToggleFavorite = {},
        onAddToPlaylist = {}
    )
}
