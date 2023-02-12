package io.github.dingyi222666.androlua.ui.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import io.github.dingyi222666.androlua.ApplicationState
import io.github.dingyi222666.androlua.core.project.Project
import io.github.dingyi222666.androlua.core.repository.MainRepository
import io.github.dingyi222666.androlua.preferences
import io.github.dingyi222666.androlua.ui.common.LocalWindowScope
import io.github.dingyi222666.androlua.ui.common.WindowState
import io.github.dingyi222666.androlua.ui.resources.LocalAppResources
import io.github.dingyi222666.androlua.ui.resources.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    val openRecentProjectErrorSnackbarHost = SnackbarHostState()

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

        application.applicationPreferences.get("lastProjectPath", null)?.let {
            flow {
                emit(openProject(File(it)))
            }.catch {
                application.preferences {
                    it.remove("lastProjectPath")
                }
                openRecentProjectErrorSnackbarHost.showSnackbar("打开上次的项目失败，已重置打开状态")
            }.flowOn(Dispatchers.IO)
                .let {
                    scope.launch {
                        delay(1000)
                        it.collect()
                    }
                }
        }

        scope.launch {
            currentProject.collect { project ->
                if (project == Project.EMPTY) return@collect
                application.preferences {
                    it.put("lastProjectPath", project.rootDir.absolutePath)
                }
            }
        }
    }


    suspend fun openProject(path: File): Project {
        val project = MainRepository.openProject(path)

        withContext(Dispatchers.IO) { project.initProjectData() }

        currentProject
            .emit(project)

        return project
    }

}