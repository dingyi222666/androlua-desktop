package io.github.dingyi222666.androlua.ui.main

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import io.github.dingyi222666.androlua.ApplicationState
import io.github.dingyi222666.androlua.ui.common.WindowState
import io.github.dingyi222666.androlua.ui.resources.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.awt.Dimension

/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/
class MainState(application: ApplicationState) : WindowState(application) {

    override val window: androidx.compose.ui.window.WindowState = androidx.compose.ui.window.WindowState()

    @Composable
    override fun newWindow() {
        super.newWindow()

        Window(
            onCloseRequest = ::callExit,
            state = window,
            undecorated = true
        ) {
            AppTheme {
                MainWindow(this@MainState)
            }
            composeWindow = window
            window.minimumSize = Dimension(800, 600)
        }


    }

}