package io.github.dingyi222666.androlua.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import io.github.dingyi222666.androlua.ApplicationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/
abstract class WindowState(
    protected val application: ApplicationState
) {

    abstract val window: WindowState

    lateinit var scope: CoroutineScope

    lateinit var composeWindow: ComposeWindow

    open fun exit(): Boolean {
        application.exitWindow(this)
        return true
    }

    fun callExit() {
        scope.launch {
            exit()
        }
    }

    @Composable
    open fun newWindow() {
        scope = rememberCoroutineScope()
    }

}

val LocalWindowScope = staticCompositionLocalOf<WindowScope> {
    error("LocalWindowScope is not initialized")
}