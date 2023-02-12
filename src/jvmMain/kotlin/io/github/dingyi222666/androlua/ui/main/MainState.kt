package io.github.dingyi222666.androlua.ui.main

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import io.github.dingyi222666.androlua.ApplicationState
import io.github.dingyi222666.androlua.core.project.Project
import io.github.dingyi222666.androlua.ui.common.LocalWindowScope
import io.github.dingyi222666.androlua.ui.common.WindowState
import io.github.dingyi222666.androlua.ui.resources.theme.AppTheme
import java.awt.Dimension

/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/
class MainState(application: ApplicationState) : WindowState(application) {

    override val window: androidx.compose.ui.window.WindowState = androidx.compose.ui.window.WindowState()

    var currentProject: Project = Project.EMPTY
        private set

    @Composable
    override fun newWindow() {
        super.newWindow()

        Window(
            onCloseRequest = ::callExit,
            state = window,
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

    fun openProject(project: Project) {
        currentProject = project
    }

}