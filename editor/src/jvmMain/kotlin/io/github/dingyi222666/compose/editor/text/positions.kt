package io.github.dingyi222666.compose.editor.text

/**
 * @author: dingyi
 * @date: 2023/2/14
 * @description:
 **/
class Position(
    val line: Int,
    val column: Int
) {

    fun with(line: Int = this.line, column: Int = this.column) = Position(line, column)

    fun delta(line: Int = 0, column: Int = 0) = Position(this.line + line, this.column + column)
}

class Range(
    val startLine: Int,
    val startColumn: Int,
    val endLine: Int,
    val endColumn: Int
) {

    fun isEmpty() = startLine == endLine && startColumn == endColumn

    fun containsPosition(position: Position) =
        position.line in startLine..endLine && position.column in startColumn..endColumn

    fun containsRange(range: Range) =
        range.startLine in startLine..endLine && range.startColumn in startColumn..endColumn &&
                range.endLine in startLine..endLine && range.endColumn in startColumn..endColumn

    fun newLeftPosition() = Position(startLine, startColumn)
    fun newRightPosition() = Position(endLine, endColumn)
}
