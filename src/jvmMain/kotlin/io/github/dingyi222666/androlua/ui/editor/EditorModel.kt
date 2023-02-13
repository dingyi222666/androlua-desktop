package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rtextarea.RTextScrollPane
import java.io.File

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/
class EditorModel(
    private val state: EditorState,
    val path: File
) {

    private var buffer = StringBuilder()


    private var isInit = false
    var isReading by mutableStateOf(false)
        private set

    internal val syntaxTextArea by lazy(LazyThreadSafetyMode.NONE) {
        RSyntaxTextArea("")
    }

    internal val rtEditorPane by lazy(LazyThreadSafetyMode.NONE) { RTextScrollPane(syntaxTextArea) }


    fun syncFromDisk() {
        isReading = true
        clear()
        path.forEachLine {
            buffer.append(it)
            buffer.append("\n")
        }
        isReading = false
    }

    fun syncToEditor() {
        rtEditorPane.textArea.text = buffer.toString()
    }

    fun init() {
        if (isInit) return
        syncFromDisk()
        syncToEditor()
        isInit = true
    }

    fun getEditorPane(): RTextScrollPane {
        syncToEditor()
        syntaxTextArea.addCaretListener {
            write(syntaxTextArea.text)
        }
        return rtEditorPane
    }

    fun write(text: String) {
        buffer.clear().append(text)
    }

    fun flush() {
        path.writeText(buffer.toString())
    }

    fun clear() {
        buffer.clear()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EditorModel

        return path.absolutePath == other.path.absolutePath
    }

    override fun hashCode(): Int {
        return path.hashCode()
    }


}