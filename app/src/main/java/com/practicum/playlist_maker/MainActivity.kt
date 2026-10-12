package com.practicum.playlist_maker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.playlist_maker.ui.theme.BlackPrimary
import com.practicum.playlist_maker.ui.theme.BluePrimary
import com.practicum.playlist_maker.ui.theme.GreyPrimary
import com.practicum.playlist_maker.ui.theme.PlaylistmakerTheme
import com.practicum.playlist_maker.ui.theme.WhitePrimary


private val menuItems = listOf(
    MenuItemData(R.drawable.search_icon, R.string.search, SearchActivity::class.java),
    MenuItemData(R.drawable.library_icon, R.string.playlists, SearchActivity::class.java),
    MenuItemData(R.drawable.favourite_icon, R.string.favourite, SearchActivity::class.java),
    MenuItemData(R.drawable.settings_icon, R.string.settings, SettingsActivity::class.java)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistmakerTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BluePrimary)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Header()
        Menu()
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview(){
    MainScreen()
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
private fun Menu(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(
                color = WhitePrimary
            )
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        MenuList()
    }
}

private data class MenuItemData(
    val iconResId: Int,
    val textResId: Int,
    val activityClass: Class<*>
)


@Composable
private fun MenuList(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        menuItems.forEach { menuItem ->
            MainMenuItem(
                icon = painterResource(menuItem.iconResId),
                text = stringResource(menuItem.textResId),
                targetClass = menuItem.activityClass
            )
        }
    }
}

@Composable
private fun MainMenuItem(icon: Painter, text: String, targetClass: Class<*>){
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .padding(horizontal = 12.dp, vertical = 18.dp)
            .clickable{
                val intent = Intent(context, targetClass)
                context.startActivity(intent)
            }
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

        Icon(
            painter = painterResource(R.drawable.arrow_icon),
            contentDescription = "arrow_icon",
            tint = GreyPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}


