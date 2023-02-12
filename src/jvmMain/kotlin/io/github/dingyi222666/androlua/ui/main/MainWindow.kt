package io.github.dingyi222666.androlua.ui.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.dingyi222666.androlua.ui.editor.EditorEmptyView


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
        MainScreen(state)
    }
}

@Composable
fun MainScreen(state: MainState) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        MainPanel(
            state = state,
            leftPanel = {
                Column {

                }
            },
            rightPanel = {
                EditorEmptyView()
            }
        )

    }
}




