package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.runtime.*
import io.github.dingyi222666.androlua.ui.main.MainState

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/
class EditorState(
    private val mainState: MainState
) {
    val currentProject = mainState.currentProject

    val editors = mutableStateListOf<EditorModel>()

    var currentSelectedEditorIndex by remember { mutableStateOf(0) }

}