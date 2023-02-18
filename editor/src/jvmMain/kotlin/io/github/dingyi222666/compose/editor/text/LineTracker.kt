package io.github.dingyi222666.compose.editor.text

/**
 * @author: dingyi
 * @date: 2023/2/19
 * @description:
 **/
interface LineTracker {
    fun getLineCount(): Int
    fun getLineStartOffset(line: Int): Int
    fun getLineEndOffset(line: Int): Int

    fun getLineContent(line: Int): CharSequence

    fun getLineNumber(offset: Int): Int

    fun getPosition(offset: Int): Position

    fun getOffset(line: Int, column: Int): Int

    fun getOffset(position: Position): Int {
        return getOffset(position.line, position.column)
    }


    fun getLineSeparator(): LineSeparator
}