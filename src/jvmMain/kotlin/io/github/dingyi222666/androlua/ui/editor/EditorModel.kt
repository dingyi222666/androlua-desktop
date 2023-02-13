package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rsyntaxtextarea.SyntaxConstants
import org.fife.ui.rsyntaxtextarea.SyntaxScheme
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

    var caretPosition = 0
        private set

    private var isInit = false
    var isReading by mutableStateOf(true)
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
        syntaxTextArea.addCaretListener {
            write(syntaxTextArea.text)
        }

        syntaxTextArea.syntaxEditingStyle = when (path.extension) {
            "lua","aly" -> SyntaxConstants.SYNTAX_STYLE_LUA
            "java" -> SyntaxConstants.SYNTAX_STYLE_JAVA
            "xml" -> SyntaxConstants.SYNTAX_STYLE_XML
            "kt" -> SyntaxConstants.SYNTAX_STYLE_KOTLIN
            "json" -> SyntaxConstants.SYNTAX_STYLE_JSON
            "js" -> SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT
            "html" -> SyntaxConstants.SYNTAX_STYLE_HTML
            "css" -> SyntaxConstants.SYNTAX_STYLE_CSS
            "properties" -> SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE
            else -> SyntaxConstants.SYNTAX_STYLE_NONE
        }


        isInit = true
    }

    fun getEditorPane(): RTextScrollPane {
        syncToEditor()
        return rtEditorPane
    }

    fun syncCaretPosition() {
        syntaxTextArea.caretPosition = caretPosition
    }

    fun saveCaretPosition() {
        caretPosition = syntaxTextArea.caretPosition
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

    fun getText() = buffer


}