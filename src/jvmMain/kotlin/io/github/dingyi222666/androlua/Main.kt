package io.github.dingyi222666.androlua

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import io.github.dingyi222666.androlua.ui.resources.LocalAppResources
import io.github.dingyi222666.androlua.ui.resources.rememberAppResources
import javax.swing.UIManager

fun main() = application {
    System.setProperty("skiko.renderApi", "OPENGL")
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    CompositionLocalProvider(LocalAppResources provides rememberAppResources()) {
       Application(rememberApplicationState())
    }
}
