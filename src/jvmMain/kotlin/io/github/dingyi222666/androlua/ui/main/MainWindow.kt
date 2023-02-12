package io.github.dingyi222666.androlua.ui.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.dingyi222666.androlua.ui.editor.EditorEmptyView
import java.io.File


/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/

@Composable
fun MainWindow(state: MainState) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        MainTitleBar(state)
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


