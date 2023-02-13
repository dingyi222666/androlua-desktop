package io.github.dingyi222666.androlua.ui.resources.font

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/
private val HarmonyOSFont = FontFamily(
    Font("fonts/HarmonyOS_Sans_SC_Regular.ttf", FontWeight.Normal),
    Font("fonts/HarmonyOS_Sans_SC_Bold.ttf", FontWeight.Bold),
    Font("fonts/HarmonyOS_Sans_SC_Medium.ttf", FontWeight.Medium),
    Font("fonts/HarmonyOS_Sans_SC_Light.ttf", FontWeight.Light),
    Font("fonts/HarmonyOS_Sans_SC_Thin.ttf", FontWeight.Thin),
    Font("fonts/HarmonyOS_Sans_SC_Black.ttf", FontWeight.Black)

)

val appTypography = Typography(
    bodyLarge =
    TextStyle(
        fontFamily = TypeScaleTokens.BodyLargeFont,
        fontWeight = TypeScaleTokens.BodyLargeWeight,
        fontSize = TypeScaleTokens.BodyLargeSize,
        lineHeight = TypeScaleTokens.BodyLargeLineHeight,
        letterSpacing = TypeScaleTokens.BodyLargeTracking,
    ),
    bodyMedium = TextStyle(
        fontFamily = TypeScaleTokens.BodyMediumFont,
        fontWeight = TypeScaleTokens.BodyMediumWeight,
        fontSize = TypeScaleTokens.BodyMediumSize,
        lineHeight = TypeScaleTokens.BodyMediumLineHeight,
        letterSpacing = TypeScaleTokens.BodyMediumTracking,
    ),
    bodySmall =
    TextStyle(
        fontFamily = TypeScaleTokens.BodySmallFont,
        fontWeight = TypeScaleTokens.BodySmallWeight,
        fontSize = TypeScaleTokens.BodySmallSize,
        lineHeight = TypeScaleTokens.BodySmallLineHeight,
        letterSpacing = TypeScaleTokens.BodySmallTracking,
    ),
    displayLarge =
    TextStyle(
        fontFamily = TypeScaleTokens.DisplayLargeFont,
        fontWeight = TypeScaleTokens.DisplayLargeWeight,
        fontSize = TypeScaleTokens.DisplayLargeSize,
        lineHeight = TypeScaleTokens.DisplayLargeLineHeight,
        letterSpacing = TypeScaleTokens.DisplayLargeTracking,
    ),
    displayMedium =
    TextStyle(
        fontFamily = TypeScaleTokens.DisplayMediumFont,
        fontWeight = TypeScaleTokens.DisplayMediumWeight,
        fontSize = TypeScaleTokens.DisplayMediumSize,
        lineHeight = TypeScaleTokens.DisplayMediumLineHeight,
        letterSpacing = TypeScaleTokens.DisplayMediumTracking,
    ),
    displaySmall =
    TextStyle(
        fontFamily = TypeScaleTokens.DisplaySmallFont,
        fontWeight = TypeScaleTokens.DisplaySmallWeight,
        fontSize = TypeScaleTokens.DisplaySmallSize,
        lineHeight = TypeScaleTokens.DisplaySmallLineHeight,
        letterSpacing = TypeScaleTokens.DisplaySmallTracking,
    ),
    headlineLarge =
    TextStyle(
        fontFamily = TypeScaleTokens.HeadlineLargeFont,
        fontWeight = TypeScaleTokens.HeadlineLargeWeight,
        fontSize = TypeScaleTokens.HeadlineLargeSize,
        lineHeight = TypeScaleTokens.HeadlineLargeLineHeight,
        letterSpacing = TypeScaleTokens.HeadlineLargeTracking,
    ),
    headlineMedium =
    TextStyle(
        fontFamily = TypeScaleTokens.HeadlineMediumFont,
        fontWeight = TypeScaleTokens.HeadlineMediumWeight,
        fontSize = TypeScaleTokens.HeadlineMediumSize,
        lineHeight = TypeScaleTokens.HeadlineMediumLineHeight,
        letterSpacing = TypeScaleTokens.HeadlineMediumTracking,
    ),
    headlineSmall =
    TextStyle(
        fontFamily = TypeScaleTokens.HeadlineSmallFont,
        fontWeight = TypeScaleTokens.HeadlineSmallWeight,
        fontSize = TypeScaleTokens.HeadlineSmallSize,
        lineHeight = TypeScaleTokens.HeadlineSmallLineHeight,
        letterSpacing = TypeScaleTokens.HeadlineSmallTracking,
    ),
    labelLarge =
    TextStyle(
        fontFamily = TypeScaleTokens.LabelLargeFont,
        fontWeight = TypeScaleTokens.LabelLargeWeight,
        fontSize = TypeScaleTokens.LabelLargeSize,
        lineHeight = TypeScaleTokens.LabelLargeLineHeight,
        letterSpacing = TypeScaleTokens.LabelLargeTracking,
    ),
    labelMedium =
    TextStyle(
        fontFamily = TypeScaleTokens.LabelMediumFont,
        fontWeight = TypeScaleTokens.LabelMediumWeight,
        fontSize = TypeScaleTokens.LabelMediumSize,
        lineHeight = TypeScaleTokens.LabelMediumLineHeight,
        letterSpacing = TypeScaleTokens.LabelMediumTracking,
    ),
    labelSmall =
    TextStyle(
        fontFamily = TypeScaleTokens.LabelSmallFont,
        fontWeight = TypeScaleTokens.LabelSmallWeight,
        fontSize = TypeScaleTokens.LabelSmallSize,
        lineHeight = TypeScaleTokens.LabelSmallLineHeight,
        letterSpacing = TypeScaleTokens.LabelSmallTracking,
    ),
    titleLarge =
    TextStyle(
        fontFamily = TypeScaleTokens.TitleLargeFont,
        fontWeight = TypeScaleTokens.TitleLargeWeight,
        fontSize = TypeScaleTokens.TitleLargeSize,
        lineHeight = TypeScaleTokens.TitleLargeLineHeight,
        letterSpacing = TypeScaleTokens.TitleLargeTracking,
    ),
    titleMedium =
    TextStyle(
        fontFamily = TypeScaleTokens.TitleMediumFont,
        fontWeight = TypeScaleTokens.TitleMediumWeight,
        fontSize = TypeScaleTokens.TitleMediumSize,
        lineHeight = TypeScaleTokens.TitleMediumLineHeight,
        letterSpacing = TypeScaleTokens.TitleMediumTracking,
    ),
    titleSmall =
    TextStyle(
        fontFamily = TypeScaleTokens.TitleSmallFont,
        fontWeight = TypeScaleTokens.TitleSmallWeight,
        fontSize = TypeScaleTokens.TitleSmallSize,
        lineHeight = TypeScaleTokens.TitleSmallLineHeight,
        letterSpacing = TypeScaleTokens.TitleSmallTracking,
    ),
)


private object TypeScaleTokens {
    val BodyLargeFont = HarmonyOSFont
    val BodyLargeLineHeight = 24.0.sp
    val BodyLargeSize = 16.sp
    val BodyLargeTracking = 0.5.sp
    val BodyLargeWeight =  FontWeight.Normal
    val BodyMediumFont = HarmonyOSFont
    val BodyMediumLineHeight = 20.0.sp
    val BodyMediumSize = 14.sp
    val BodyMediumTracking = 0.2.sp
    val BodyMediumWeight =  FontWeight.Normal
    val BodySmallFont = HarmonyOSFont
    val BodySmallLineHeight = 16.0.sp
    val BodySmallSize = 12.sp
    val BodySmallTracking = 0.4.sp
    val BodySmallWeight =  FontWeight.Normal
    val DisplayLargeFont = HarmonyOSFont
    val DisplayLargeLineHeight = 64.0.sp
    val DisplayLargeSize = 57.sp
    val DisplayLargeTracking = -0.2.sp
    val DisplayLargeWeight =  FontWeight.Normal
    val DisplayMediumFont = HarmonyOSFont
    val DisplayMediumLineHeight = 52.0.sp
    val DisplayMediumSize = 45.sp
    val DisplayMediumTracking = 0.0.sp
    val DisplayMediumWeight =  FontWeight.Normal
    val DisplaySmallFont = HarmonyOSFont
    val DisplaySmallLineHeight = 44.0.sp
    val DisplaySmallSize = 36.sp
    val DisplaySmallTracking = 0.0.sp
    val DisplaySmallWeight =  FontWeight.Normal
    val HeadlineLargeFont = HarmonyOSFont
    val HeadlineLargeLineHeight = 40.0.sp
    val HeadlineLargeSize = 32.sp
    val HeadlineLargeTracking = 0.0.sp
    val HeadlineLargeWeight =  FontWeight.Normal
    val HeadlineMediumFont = HarmonyOSFont
    val HeadlineMediumLineHeight = 36.0.sp
    val HeadlineMediumSize = 28.sp
    val HeadlineMediumTracking = 0.0.sp
    val HeadlineMediumWeight =  FontWeight.Normal
    val HeadlineSmallFont = HarmonyOSFont
    val HeadlineSmallLineHeight = 32.0.sp
    val HeadlineSmallSize = 24.sp
    val HeadlineSmallTracking = 0.0.sp
    val HeadlineSmallWeight =  FontWeight.Normal
    val LabelLargeFont = HarmonyOSFont
    val LabelLargeLineHeight = 20.0.sp
    val LabelLargeSize = 14.sp
    val LabelLargeTracking = 0.1.sp
    val LabelLargeWeight =  FontWeight.Medium
    val LabelMediumFont = HarmonyOSFont
    val LabelMediumLineHeight = 16.0.sp
    val LabelMediumSize = 12.sp
    val LabelMediumTracking = 0.5.sp
    val LabelMediumWeight =  FontWeight.Medium
    val LabelSmallFont = HarmonyOSFont
    val LabelSmallLineHeight = 16.0.sp
    val LabelSmallSize = 11.sp
    val LabelSmallTracking = 0.5.sp
    val LabelSmallWeight =  FontWeight.Medium
    val TitleLargeFont = HarmonyOSFont
    val TitleLargeLineHeight = 28.0.sp
    val TitleLargeSize = 22.sp
    val TitleLargeTracking = 0.0.sp
    val TitleLargeWeight =  FontWeight.Normal
    val TitleMediumFont = HarmonyOSFont
    val TitleMediumLineHeight = 24.0.sp
    val TitleMediumSize = 16.sp
    val TitleMediumTracking = 0.2.sp
    val TitleMediumWeight =  FontWeight.Medium
    val TitleSmallFont = HarmonyOSFont
    val TitleSmallLineHeight = 20.0.sp
    val TitleSmallSize = 14.sp
    val TitleSmallTracking = 0.1.sp
    val TitleSmallWeight =  FontWeight.Medium
}
