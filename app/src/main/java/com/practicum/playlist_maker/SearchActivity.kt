package com.practicum.playlist_maker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlist_maker.ui.theme.BlackPrimary
import com.practicum.playlist_maker.ui.theme.GreyLight
import com.practicum.playlist_maker.ui.theme.GreyPrimary
import com.practicum.playlist_maker.ui.theme.PlaylistmakerTheme
import com.practicum.playlist_maker.ui.theme.WhitePrimary


class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistmakerTheme {
                SearchScreen()
            }

        }
    }
}

@Composable
private fun SearchScreen(){

    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WhitePrimary)
    ){
        Column() {
            PanelHeader()
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        }

    }
}

@Composable
@Preview(showSystemUi = true)
private fun SearchScreenPreview(){
    SearchScreen()
}

@Composable
private fun PanelHeader(){
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Icon(
            painter = painterResource(R.drawable.arrow_back),
            contentDescription = "arrow back",
            tint = BlackPrimary,
            modifier = Modifier.size(16.dp)
        )
        Text(
            stringResource(R.string.search),
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
            .height(46.dp)
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