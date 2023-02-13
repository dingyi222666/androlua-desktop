package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.runtime.*
import io.github.dingyi222666.androlua.ui.main.MainState
import java.io.File

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

    var currentActiveEditorIndex by mutableStateOf(0)

    fun syncFromDisk() {
        currentProject.value.getOpenedFiles().forEach {
            editors.add(EditorModel(this, it))
        }
    }

    fun currentActiveEditor() = editors.getOrNull(currentActiveEditorIndex)

    fun openFile(file: File) {
        editors.add(EditorModel(this, file))
        currentProject.value.addOpenedFile(file)
    }

    fun closeFile(file: File) {
        editors.removeIf { it.path == file }
        currentProject.value.removeOpenedFile(file)
    }
}