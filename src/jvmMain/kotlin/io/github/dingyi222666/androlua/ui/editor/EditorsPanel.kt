package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.dingyi222666.androlua.ui.main.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/
@Composable
fun EditorsPanel(state: MainState) = Column {

    val editorState = state.editorState

    EditorTabsView(editorState)

    Box {

        if (editorState.currentActiveEditor() != null) {
            val currentEditorModel = checkNotNull( editorState.currentActiveEditor())
            if (currentEditorModel.isReading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                CodeEditor(currentEditorModel)
            }
        }

    }


}