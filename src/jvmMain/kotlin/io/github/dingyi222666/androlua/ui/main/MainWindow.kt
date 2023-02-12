package io.github.dingyi222666.androlua.ui.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.bonsai.filesystem.FileSystemTree
import io.github.dingyi222666.androlua.core.project.Project
import io.github.dingyi222666.androlua.ui.editor.EditorEmptyView
import io.github.dingyi222666.androlua.ui.fileTree.FileTree


/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainWindow(state: MainState) {
    Scaffold(
        topBar = {
            MainTitleBar(state)
        },
        snackbarHost = {
            SnackbarHost(
                hostState = state.openRecentProjectErrorSnackbarHost
            )
        }
    ) {
        MainScreen(state, it)
    }
}

@Composable
fun MainScreen(state: MainState, paddingValues: PaddingValues) {

    val currentProjectState by state.currentProject.collectAsState()

    println(currentProjectState.rootDir)

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
    ) {
        MainPanel(
            state = state,
            leftPanel = {
                if (currentProjectState != Project.EMPTY) {
                    FileTree(
                        tree = FileSystemTree(
                            rootPath = currentProjectState.rootDir,
                            selfInclude = true
                        )
                    )
                }
            },
            rightPanel = {
                EditorEmptyView()
            }
        )

    }
}




