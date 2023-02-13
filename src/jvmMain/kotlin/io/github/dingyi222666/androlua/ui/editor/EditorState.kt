package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.runtime.*
import io.github.dingyi222666.androlua.ui.main.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.max

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
        println("file:$file ${currentProject.value.supportOpen(file)}")
        if (currentProject.value.supportOpen(file)) {
            val editorModel = EditorModel(this, file)
            mainState.scope.launch {
                withContext(Dispatchers.IO) { editorModel.init() }
            }
            editors.add(editorModel)
            currentProject.value.addOpenedFile(file)
            currentActiveEditorIndex = editors.lastIndex
        }
    }

    fun closeFile(file: File) {
        val currentModel = editors.find { it.path.absolutePath == file.absolutePath }
        val indexOf = editors.indexOf(currentModel)
        currentActiveEditorIndex = max(0, indexOf - 1)
        editors.remove(currentModel)
        currentProject.value.removeOpenedFile(file)

    }
}