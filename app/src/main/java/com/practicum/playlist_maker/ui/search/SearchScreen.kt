package com.practicum.playlist_maker.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.practicum.playlist_maker.R
import com.practicum.playlist_maker.domain.model.Track
import com.practicum.playlist_maker.ui.theme.GreyPrimary
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import androidx.lifecycle.compose.currentStateAsState
import coil.compose.AsyncImage


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onNavigateToDetailScreen: (Track) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier,
    isDarkTheme: Boolean
){
    val screenState by searchViewModel.searchScreenState.collectAsState()
    val searchQuery by searchViewModel.searchQuery.collectAsState()

    var historyList by remember { mutableStateOf<List<String>>(emptyList()) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle.currentStateAsState().value) {
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            historyList = searchViewModel.getHistoryList()
        }
    }
    LaunchedEffect(screenState) {
        if (screenState is SearchViewModel.SearchState.Success) {
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ){
        PanelHeader(onNavigateBack)
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchViewModel.updateQuery(it) },
            onClearClick = { searchViewModel.clearSearch() },
            onSearchClick = {
                searchViewModel.updateQuery("")
                searchViewModel.clearSearch()
            },
            onFocusChanged = { isFocused = it },
            focusRequester = focusRequester
        )

        if (isFocused && searchQuery.isEmpty() && historyList.isNotEmpty()) {
            HistoryRequests(
                historyList = historyList,
                onClick = { word ->
                    searchViewModel.updateQuery(word)
                    focusRequester.requestFocus()
                }
            )
        }

        SearchResults(
            screenState = screenState,
            modifier = Modifier.padding(top = 16.dp),
            onTrackClick = onNavigateToDetailScreen,
            isDarkTheme
        )
    }


}

@Composable
private fun PanelHeader(
    onNavigateBack: () -> Unit
){
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onNavigateBack) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = stringResource(R.string.arrow_back),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            stringResource(R.string.search),
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearchClick: () -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    focusRequester: FocusRequester
){
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(56.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChanged(it.isFocused)},
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = GreyPrimary,
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        leadingIcon = {
            IconButton(onClick = {
                if (query.isNotBlank()) onSearchClick()
            }) {
                Icon(
                    painterResource(R.drawable.search_icon),
                    contentDescription = stringResource(R.string.search),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
            }
        },
        trailingIcon = {
            if(query.isNotEmpty()){
                IconButton(onClick = {
                    onQueryChange("")
                    onClearClick()
                }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant ,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        )
    )
}

@Composable
private fun SearchResults(
    screenState: SearchViewModel.SearchState,
    modifier: Modifier,
    onTrackClick: (Track) -> Unit,
    isDarkTheme: Boolean
) {
    when (screenState) {
        is SearchViewModel.SearchState.Initial -> {
            // Nothing to show in an initial state
        }

        is SearchViewModel.SearchState.Searching -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SearchViewModel.SearchState.Success -> {
            val tracks = screenState.foundList
            if (tracks.isEmpty()) {
                Column {
                    Icon(
                        painter = painterResource(
                            if (isDarkTheme) R.drawable.no_files_dark
                            else R.drawable.no_files
                        ),
                        contentDescription = stringResource(R.string.no_tracks_found),
                        modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                        tint = Color.Unspecified
                    )
                    InformerState(
                        titleText = stringResource(R.string.no_tracks_found),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

            } else {
                LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(tracks.size) { index ->
                        TrackListItem(
                            track = tracks[index],
                            onClick = { onTrackClick(tracks[index]) }
                        )
                    }
                }
            }
        }

        is SearchViewModel.SearchState.Fail -> {
            Column {
                Icon(
                    painter = painterResource(
                        if (isDarkTheme) R.drawable.no_internet_dark
                        else R.drawable.no_internet
                    ),
                    contentDescription = stringResource(R.string.check_connection),
                    modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                    tint = Color.Unspecified
                )
                InformerState(
                    titleText = screenState.error,
                    subtitleText = stringResource(R.string.check_connection),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun InformerState(
    modifier: Modifier = Modifier,
    titleText: String,
    subtitleText: String? = null,

) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titleText,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        subtitleText?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TrackListItem(
    track: Track,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = track.image,
            contentDescription = "Track ${track.trackName}",
            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
            error = painterResource(id = android.R.drawable.ic_menu_gallery)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                track.trackName,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = track.artistName,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "•",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    track.trackTime,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .padding(4.dp)
        )
        Icon(
            modifier = Modifier.size(24.dp).padding(4.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "arrow",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun HistoryRequests(
    historyList: List<String>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 200.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        items(historyList.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(historyList[index]) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = historyList[index])
            }
            if (historyList[index] != historyList.last()) {
                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    }
}