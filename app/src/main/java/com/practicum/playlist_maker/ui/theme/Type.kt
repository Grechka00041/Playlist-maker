package com.practicum.playlist_maker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.practicum.playlist_maker.R



val YsTextMedium = FontFamily(
    Font(R.font.ys_text_medium, FontWeight.W500),
    Font(R.font.ys_text_medium_italic, FontWeight.W500, FontStyle.Italic)
)

val YsTextLight = FontFamily(
    Font(R.font.ys_text_light, FontWeight.W300),
    Font(R.font.ys_text_light_italic, FontWeight.W300, FontStyle.Italic)
)

val YsTextBold = FontFamily(
    Font(R.font.ys_text_bold, FontWeight.W700),
    Font(R.font.ys_text_bold_italic, FontWeight.W700, FontStyle.Italic)
)

val YsTextRegular = FontFamily(
    Font(R.font.ys_text_regular, FontWeight.W400),
    Font(R.font.ys_text_regular_italic, FontWeight.W400, FontStyle.Italic)
)



val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = YsTextMedium,
        fontWeight = FontWeight.W500,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = YsTextMedium,
        fontWeight = FontWeight.W500,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)