package io.github.dingyi222666.androlua.ui.main

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import io.github.dingyi222666.androlua.ApplicationState
import io.github.dingyi222666.androlua.core.project.Project
import io.github.dingyi222666.androlua.core.repository.MainRepository
import io.github.dingyi222666.androlua.ui.common.LocalWindowScope
import io.github.dingyi222666.androlua.ui.common.WindowState
import io.github.dingyi222666.androlua.ui.resources.LocalAppResources
import io.github.dingyi222666.androlua.ui.resources.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import java.awt.Dimension
import java.io.File

/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/
class MainState(application: ApplicationState) : WindowState(application) {

    override val window: androidx.compose.ui.window.WindowState = androidx.compose.ui.window.WindowState()

    val currentProject = MutableStateFlow(Project.EMPTY)

    @Composable
    override fun newWindow() {
        super.newWindow()

        val currentResources = LocalAppResources.current

        Window(
            onCloseRequest = ::callExit,
            state = window,
            title = currentResources.appTitle,
            icon = currentResources.appIcon,
            undecorated = true
        ) {
            AppTheme {
                CompositionLocalProvider(LocalWindowScope provides this) {
                    MainWindow(this@MainState)
                }
            }
            composeWindow = window
            window.minimumSize = Dimension(800, 600)
        }

    }


    suspend fun openProject(path: File) {
        val project = MainRepository.openProject(path)
        currentProject
            .emit(project)

    }

}