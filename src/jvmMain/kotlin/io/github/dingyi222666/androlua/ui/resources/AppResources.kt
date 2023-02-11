package io.github.dingyi222666.androlua.ui.resources

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.RenderVectorGroup
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter

/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/

val LocalAppResources = staticCompositionLocalOf<AppResources> {
    error("LocalNotepadResources isn't provided")
}

@Composable
fun rememberAppResources(): AppResources {
    val icon = rememberVectorPainter(Icons.Default.Add, tintColor = Color(0xFF2CA4E1))
    return remember { AppResources(icon) }
}

class AppResources(

    val trayIcon: VectorPainter
) {

}

@Composable
fun rememberVectorPainter(image: ImageVector, tintColor: Color) =
    rememberVectorPainter(
        defaultWidth = image.defaultWidth,
        defaultHeight = image.defaultHeight,
        viewportWidth = image.viewportWidth,
        viewportHeight = image.viewportHeight,
        name = image.name,
        tintColor = tintColor,
        tintBlendMode = image.tintBlendMode,
        autoMirror = false,
        content = { _, _ -> RenderVectorGroup(group = image.root) }
    )

