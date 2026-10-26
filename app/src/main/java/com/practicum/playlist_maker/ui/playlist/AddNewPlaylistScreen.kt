package com.practicum.playlist_maker.ui.playlist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlist_maker.R
import com.practicum.playlist_maker.ui.theme.BluePrimary
import com.practicum.playlist_maker.ui.theme.GreyLight
import com.practicum.playlist_maker.ui.theme.GreyPrimary
import com.practicum.playlist_maker.ui.theme.WhitePrimary
import com.practicum.playlist_maker.ui.view_model.PlaylistsViewModel
import kotlin.math.sin

@Composable
fun AddPlaylistScreen(
    onNavigateBack: () -> Unit,
    playlistsViewModel: PlaylistsViewModel
) {

    var playlistName by remember { mutableStateOf("") }
    var playlistDescription by remember { mutableStateOf("") }
    val isEnabled = playlistName.isNotBlank()

    Box(modifier = Modifier.fillMaxSize().background(WhitePrimary)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onNavigateBack() },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
                Text(stringResource(R.string.add_new_playlist), fontSize = 32.sp)
            }
        }

        Column(
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 188.dp, bottom = 188.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(R.drawable.ic_music),
                    contentDescription = stringResource(R.string.add_new_playlist),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.CenterHorizontally),
                value = playlistName,
                onValueChange = { playlistName = it},
                label = { Text("Название*") },
                singleLine = true,
                isError = !isEnabled && playlistName.isNotEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = WhitePrimary,
                    focusedContainerColor = WhitePrimary,
                    unfocusedBorderColor = GreyPrimary,
                    focusedBorderColor = BluePrimary,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = GreyPrimary,
                    errorBorderColor = Color.Red
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                value = "",
                onValueChange = {
                    playlistDescription = it
                },
                label = { Text("Описание") },
                maxLines = 5,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = WhitePrimary,
                    focusedContainerColor = WhitePrimary,
                    unfocusedBorderColor = GreyPrimary,
                    focusedBorderColor = BluePrimary,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = GreyPrimary,
                )
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp, top = 100.dp)
                    .align(Alignment.CenterHorizontally ),
                enabled = isEnabled,
                onClick = {
                    if (isEnabled) playlistsViewModel.createNewPlayList(playlistName, playlistDescription)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BluePrimary,
                    contentColor = WhitePrimary,
                    disabledContainerColor = GreyPrimary
                )
            ) {
                Text("Создать")
            }
        }
    }
}
