package com.practicum.playlist_maker.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.practicum.playlist_maker.ui.navigation.PlaylistHost
import com.practicum.playlist_maker.R
import com.practicum.playlist_maker.ui.theme.BlackPrimary
import com.practicum.playlist_maker.ui.theme.BluePrimary
import com.practicum.playlist_maker.ui.theme.GreyPrimary
import com.practicum.playlist_maker.ui.theme.PlaylistmakerTheme
import com.practicum.playlist_maker.ui.theme.WhitePrimary
import com.practicum.playlist_maker.ui.view_model.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private data class MenuItemData(
    val iconResId: Int,
    val textResId: Int,
    val onClick: () -> Unit
)

class MainActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistmakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlaylistHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = rememberNavController(),
                        gson = remember { Gson() },
                        startDestination = "main",
                        searchViewModel = searchViewModel
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview(){
    MainScreen({}, {})
}

@Composable
fun MainScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BluePrimary)
    ) {
        Header()
        Menu(
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToSettings = onNavigateToSettings
        )
    }
}

@Composable
private fun Header(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BluePrimary,
            ),
        contentAlignment = Alignment.CenterStart

    ){
        Text(
            stringResource(R.string.main_header),
            style = MaterialTheme.typography.titleLarge,
            color = WhitePrimary,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun Menu(
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(
                color = WhitePrimary
            )
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        MenuList(onNavigateToSearch, onNavigateToSettings)
    }
}


@Composable
private fun MenuList(
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit
){
    val menuItems = listOf(
        MenuItemData(R.drawable.search_icon, R.string.search) { onNavigateToSearch() },
        MenuItemData(R.drawable.library_icon, R.string.playlists) { /* Пока заглушка */ },
        MenuItemData(R.drawable.favourite_icon, R.string.favourite) { /* Пока заглушка */ },
        MenuItemData(R.drawable.settings_icon, R.string.settings) { onNavigateToSettings() }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        menuItems.forEach { menuItem ->
            MainMenuItem(
                icon = painterResource(menuItem.iconResId),
                text = stringResource(menuItem.textResId),
                onClick = menuItem.onClick
            )
        }
    }
}

@Composable
private fun MainMenuItem(icon: Painter, text: String, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .padding(horizontal = 12.dp, vertical = 18.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier.wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(
                painter = icon,
                contentDescription = "icon",
                tint = BlackPrimary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text,
                style = MaterialTheme.typography.titleLarge,
                color = BlackPrimary
            )
        }

        IconButton(onClick) {
            Icon(
                painter = painterResource(R.drawable.arrow_icon),
                contentDescription = "arrow_icon",
                tint = GreyPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


