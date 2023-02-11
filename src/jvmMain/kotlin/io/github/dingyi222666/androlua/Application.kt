package io.github.dingyi222666.androlua

import androidx.compose.runtime.*
import androidx.compose.ui.window.*

import io.github.dingyi222666.androlua.ui.common.WindowState
import io.github.dingyi222666.androlua.ui.common.LocalAppResources
import io.github.dingyi222666.androlua.ui.main.MainState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/

class ApplicationState {

    val tray = TrayState()

    private val _windows = mutableStateListOf<WindowState>()
    val windows: List<WindowState> get() = _windows

    fun addWindow(windowState: WindowState) {
        _windows.add(windowState)
    }


    fun exitWindow(windowState: WindowState) {
        _windows.remove(windowState)
        if (_windows.isEmpty()) {
            exitApplication()
        }
    }

    fun sendNotification(notification: Notification) {
        tray.sendNotification(notification)
    }

    private fun exitApplication() {
        exitProcess(0)
    }

    suspend fun exit() {
        val windowsCopy = windows.reversed()
        for (window in windowsCopy) {
            if (!window.exit()) {
                break
            }
        }
        exitApplication()
    }
}

@Composable
fun rememberApplicationState() = remember {
    ApplicationState().apply {
        addWindow(MainState(this))
    }
}

@Composable
fun ApplicationScope.Application(state: ApplicationState) {
    val scope = rememberCoroutineScope()
    if (/*state.settings.isTrayEnabled && */state.windows.isNotEmpty()) {
        ApplicationTray(state, scope)
    }

    for (window in state.windows) {
        key(window) {
            window.newWindow()
        }
    }
}

@Composable
private fun MenuScope.ApplicationMenu(state: ApplicationState, scope: CoroutineScope) {
    fun exit() = scope.launch { state.exit() }

    /*Item("New", onClick = state::newWindow)*/
    Separator()
    Item("Exit", onClick = { exit() })
}

@Composable
private fun ApplicationScope.ApplicationTray(state: ApplicationState, scope: CoroutineScope) {
    Tray(
        LocalAppResources.current.trayIcon,
        state = state.tray,
        tooltip = "AndroLua+ Desktop",
        menu = { ApplicationMenu(state, scope) }
    )
}

