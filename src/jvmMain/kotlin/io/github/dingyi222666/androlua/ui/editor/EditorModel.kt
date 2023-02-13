package io.github.dingyi222666.androlua.ui.editor

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

    fun sync() {
        clear()
        path.forEachLine {
            buffer.append(it)
            buffer.append("\n")
        }
    }

    fun write(text: String) {
        buffer.append(text)
    }

    fun flush() {
        path.writeText(buffer.toString())
    }

    fun clear() {
        buffer.clear()
    }

}