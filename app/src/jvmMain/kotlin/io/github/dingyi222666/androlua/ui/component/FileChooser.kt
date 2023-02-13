package io.github.dingyi222666.androlua.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.WindowScope
import io.github.dingyi222666.androlua.LocalApplicationState
import io.github.dingyi222666.androlua.preferences
import kotlinx.coroutines.*
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/
class FileChooserDialogMode {
    enum class Mode { LOAD, SAVE, LOAD_DIR }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun WindowScope.FileChooserDialog(
    mode: FileChooserDialogMode.Mode = FileChooserDialogMode.Mode.LOAD,
    title: String = "文件选择",
    id: Int = 0x1,
    extensions: List<FileNameExtensionFilter> = emptyList(),
    onResult: (files: List<File>) -> Unit
) {

    val currentApplicationState = LocalApplicationState.current

    GlobalScope.launch(Dispatchers.Default) {
        val fileChooser = JFileChooser()

        fileChooser.dialogTitle = title
        fileChooser.isMultiSelectionEnabled = mode == FileChooserDialogMode.Mode.LOAD
        fileChooser.isAcceptAllFileFilterUsed = extensions.isEmpty()
        fileChooser.currentDirectory = currentApplicationState.preferences {
            File(it.get("FileChooser-$id", System.getProperty("user.home")))
        }
        fileChooser.fileSelectionMode =
            if (mode == FileChooserDialogMode.Mode.LOAD_DIR) JFileChooser.DIRECTORIES_ONLY else JFileChooser.FILES_ONLY

        extensions.forEach { fileChooser.addChoosableFileFilter(it) }

        val returned = if (mode == FileChooserDialogMode.Mode.SAVE) {
            fileChooser.showSaveDialog(window)
        } else {
            fileChooser.showOpenDialog(window)
        }

        onResult(
            when (returned) {
                JFileChooser.APPROVE_OPTION -> {
                    if (mode == FileChooserDialogMode.Mode.LOAD) {
                        val files = fileChooser.selectedFiles.filter { it.canRead() }
                        files.ifEmpty {
                            emptyList()
                        }
                    } else {
                        if (!fileChooser.fileFilter.accept(fileChooser.selectedFile)) {
                            fileChooser.selectedFile = fileChooser.selectedFile
                        }
                        var selectedFile = fileChooser.selectedFile
                        if (!selectedFile.isDirectory) {
                            selectedFile = selectedFile.parentFile
                        }
                        currentApplicationState.preferences {
                            it.put("FileChooser-$id", selectedFile.absolutePath)
                        }
                        listOf(fileChooser.selectedFile)
                    }
                }

                else -> emptyList()
            })

    }

}