package io.github.dingyi222666.androlua.ui.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.RenderVectorGroup
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Typography
import io.github.dingyi222666.androlua.ui.resources.font.appTypography

/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/

val LocalAppResources = staticCompositionLocalOf<AppResources> {
    error(
        "LocalApp" + "Resources isn't provided"
    )
}


@Composable
fun rememberAppResources(): AppResources {
    val appIcon = painterResource("icon.png")
    return remember { AppResources(appIcon, appFont = appTypography) }
}

class AppResources(
    val appIcon: Painter, val appFont: Typography
) {
    val appTitle = "AndroLua+ Desktop"
}

@Composable
fun rememberVectorPainter(image: ImageVector, tintColor: Color) =
    rememberVectorPainter(defaultWidth = image.defaultWidth,
        defaultHeight = image.defaultHeight,
        viewportWidth = image.viewportWidth,
        viewportHeight = image.viewportHeight,
        name = image.name,
        tintColor = tintColor,
        tintBlendMode = image.tintBlendMode,
        autoMirror = false,
        content = { _, _ -> RenderVectorGroup(group = image.root) })

