package com.practicum.playlist_maker.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlist_maker.R
import com.practicum.playlist_maker.domain.model.Track
import com.practicum.playlist_maker.ui.theme.BlackPrimary
import com.practicum.playlist_maker.ui.theme.GreyLight
import com.practicum.playlist_maker.ui.theme.GreyPrimary
import com.practicum.playlist_maker.ui.theme.WhitePrimary
import com.practicum.playlist_maker.ui.view_model.SearchViewModel


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onNavigateToDetailScreen: (Track) -> Unit,
    onNavigateBack: () -> Unit
){
    val screenState by searchViewModel.searchScreenState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WhitePrimary)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ){
        PanelHeader(onNavigateBack)
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
        SearchResults(
            screenState = screenState,
            modifier = Modifier.padding(top = 16.dp),
            onTrackClick = onNavigateToDetailScreen
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
                contentDescription = "arrow back",
                tint = BlackPrimary,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            stringResource(R.string.search),
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.titleLarge,
            color = BlackPrimary
        )

    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
){
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(56.dp)
        ,
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = GreyPrimary,
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                ),
                color = GreyPrimary,
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "search icon",
                tint = GreyPrimary,
                modifier = Modifier.size(16.dp)
            )
        },
        trailingIcon = {
            if(query.isNotEmpty()){
                IconButton(onClick = { onQueryChange("")}) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "clear",
                        tint = GreyPrimary ,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = GreyLight,
            focusedContainerColor = GreyLight,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedTextColor = GreyPrimary,
            unfocusedTextColor = GreyPrimary,
        )
    )
}

@Composable
private fun SearchResults(
    screenState: SearchViewModel.SearchState,
    modifier: Modifier,
    onTrackClick: (Track) -> Unit // Используем новое имя параметра
) {
    when (screenState) {
        is SearchViewModel.SearchState.Initial -> {
            // Ничего не показываем в начальном состоянии
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
                InformerState(stringResource(R.string.no_tracks_found))
            } else {
                LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(tracks.size) { index ->
                        TrackListItem(
                            track = tracks[index],
                            onClick = { onTrackClick(tracks[index]) } // Вызываем навигацию по клику
                        )
                    }
                }
            }
        }

        is SearchViewModel.SearchState.Fail -> {
            InformerState(
                titleText = screenState.error, // Берём сообщение прямо из состояния
                subtitleText = stringResource(R.string.check_connection) // Дополнительный текст из ресурсов
            )
        }
    }
}

@Composable
private fun InformerState(
    titleText: String,
    subtitleText: String? = null,
    modifier: Modifier = Modifier
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
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        subtitleText?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TrackListItem(
    track: Track,
    onClick: () -> Unit // Оставляем только обработчик одиночного клика
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Иконка трека (заглушка)
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = "Track icon",
            modifier = Modifier.size(100.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f),
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
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "•",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    track.trackTime,
                    fontSize = 11.sp,
                    color = Color.Gray,
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
            tint = Color.Gray,
        )
    }
}