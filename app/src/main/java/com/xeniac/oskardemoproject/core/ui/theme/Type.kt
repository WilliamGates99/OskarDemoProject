package com.xeniac.oskardemoproject.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.xeniac.oskardemoproject.R

val oskarDemoFont = FontFamily(
    listOf(
        Font(resId = R.font.font_extra_light, weight = FontWeight.ExtraLight),
        Font(resId = R.font.font_light, weight = FontWeight.Light),
        Font(resId = R.font.font_regular, weight = FontWeight.Normal),
        Font(resId = R.font.font_medium, weight = FontWeight.Medium),
        Font(resId = R.font.font_semi_bold, weight = FontWeight.SemiBold),
        Font(resId = R.font.font_bold, weight = FontWeight.Bold),
        Font(resId = R.font.font_extra_bold, weight = FontWeight.ExtraBold),
        Font(resId = R.font.font_black, weight = FontWeight.Black)
    )
)

private val defaultTypography = Typography()
val typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = oskarDemoFont),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = oskarDemoFont),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = oskarDemoFont),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = oskarDemoFont),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = oskarDemoFont),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = oskarDemoFont),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = oskarDemoFont),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = oskarDemoFont),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = oskarDemoFont),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = oskarDemoFont),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = oskarDemoFont),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = oskarDemoFont),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = oskarDemoFont),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = oskarDemoFont),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = oskarDemoFont)
)