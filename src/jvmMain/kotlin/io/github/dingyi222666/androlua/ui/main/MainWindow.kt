package io.github.dingyi222666.androlua.ui.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.bonsai.filesystem.FileSystemTree
import io.github.dingyi222666.androlua.core.project.Project
import io.github.dingyi222666.androlua.ui.editor.EditorEmptyView
import io.github.dingyi222666.androlua.ui.editor.EditorsPanel
import io.github.dingyi222666.androlua.ui.fileTree.FileTree
import io.github.dingyi222666.androlua.ui.fileTree.FileTreeViewTabView


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

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
    ) {
        MainPanel(
            state = state,
            leftPanel = {
                MainLeftPanel(state)
            },
            rightPanel = {
                Box(Modifier.fillMaxSize()) {
                    if (currentProjectState != Project.EMPTY) {
                        EditorsPanel(state)
                    } else {
                        EditorEmptyView()
                    }
                }
            }
        )

    }
}

@Composable
fun MainLeftPanel(state: MainState) = Column(
    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
) {

    FileTreeViewTabView()

    if (state.currentProject.value != Project.EMPTY) {
        FileTree(
            tree = FileSystemTree(
                rootPath = state.currentProject.value.rootDir,
                selfInclude = true
            ),
            onClick = {
                val file = it.content.toFile()
                if (file.isFile) {
                    state.editorState.openFile(file)
                }
            }
        )
    }

    if (state.loadProjectState) {
        LinearProgressIndicator(
            modifier = Modifier
                .padding(top = 6.dp)
                .height(2.dp)
                .fillMaxWidth()
        )
    }
}




