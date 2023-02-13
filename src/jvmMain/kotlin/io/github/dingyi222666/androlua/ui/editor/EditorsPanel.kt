package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.dingyi222666.androlua.ui.main.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/
@Composable
fun EditorsPanel(state: MainState) = Column {

    val editorState = state.editorState

    val scope = rememberCoroutineScope()


    EditorTabsView(editorState)

    Box(modifier = Modifier.fillMaxSize()) {

        if (editorState.currentActiveEditor() != null) {
            val currentEditorModel = checkNotNull(editorState.currentActiveEditor())
            if (currentEditorModel.isReading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                CodeEditor(
                    modifier = Modifier.fillMaxSize(),
                    model = currentEditorModel
                )
            }

            scope.launch {
                currentEditorModel.init()
            }

        }

    }

}