package com.practicum.playlist_maker

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.playlist_maker.ui.theme.BlackPrimary
import com.practicum.playlist_maker.ui.theme.GreyPrimary
import com.practicum.playlist_maker.ui.theme.PlaylistmakerTheme
import com.practicum.playlist_maker.ui.theme.WhitePrimary
import androidx.core.net.toUri


private data class SettingsItemData(
    val iconResId: Int,
    val textResId: Int,
    val action: (Context) -> Unit
)
private val menuItems = listOf(
    SettingsItemData(R.drawable.share, R.string.share, ::shareApp),
    SettingsItemData(R.drawable.support, R.string.support, ::sendEmail),
    SettingsItemData(R.drawable.arrow_icon, R.string.user_agreement, ::readAgreement)
)

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistmakerTheme {
                SettingsScreen()
            }
        }
    }
}


@Composable
private fun SettingsScreen(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WhitePrimary)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
                PanelHeader()
                SettingsList()
        }

}

@Composable
private fun PanelHeader(){
    Box(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        contentAlignment = Alignment.CenterStart
    ){
        Text(
            text = stringResource(R.string.settings),
            modifier = Modifier.padding(4.dp),
            color = BlackPrimary,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun SettingsList(){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        menuItems.forEach { item ->
            SettingsMenuItem(
                icon = painterResource(item.iconResId),
                text = stringResource(item.textResId),
                onClick = {item.action(context)}
            )
        }
    }
}

@Composable
private fun SettingsMenuItem(icon: Painter, text: String, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .padding(horizontal = 12.dp, vertical = 18.dp)
            .clickable{
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier.wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text,
                style = MaterialTheme.typography.bodyLarge,
                color = BlackPrimary
            )
        }

        Icon(
            painter = icon,
            contentDescription = "icon",
            tint = GreyPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}

fun shareApp(context: Context){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_message))
    }
    context.startActivity(Intent.createChooser(intent, "Поделиться приложением"))

}

private fun sendEmail(context: Context){
    val subject = Uri.encode(context.getString(R.string.share_theme))
    val body = Uri.encode(context.getString(R.string.share_text))
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:23052006z@gmail.com?subject=$subject&body=$body".toUri()
    }
    context.startActivity(intent)
}

private fun readAgreement(context: Context){
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = context.getString(R.string.agreement_link).toUri()
    }
    context.startActivity(Intent.createChooser(intent, "Открыть пользовательское соглашение"))
}

@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenPreview(){
    SettingsScreen()
}