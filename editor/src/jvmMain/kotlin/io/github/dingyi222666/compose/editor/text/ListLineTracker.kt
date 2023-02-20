package io.github.dingyi222666.compose.editor.text

import kotlin.math.max

/**
 * @author: dingyi
 * @date: 2023/2/19
 * @description:
 **/
class ListLineTracker : LineTracker, TextChangeListener {

    private var lineSeparator = LineSeparator.NONE

    private val lines = mutableListOf(Line(0, 0))

    private lateinit var currentRope: Rope

    private val cacheLines = mutableListOf<Line>()

    override fun getLineCount(): Int {
        return lines.size
    }

    private fun checkLine(line: Int) {
        require(line <= lines.size) {
            "line $line is out of range"
        }
    }

    override fun getLineStartOffset(line: Int): Int {
        checkLine(line)
        return lines[line - 1].startOffset
    }

    override fun getLineEndOffset(line: Int): Int {
        return if (line == lines.size - 1) {
            currentRope.length
        } else {
            max(0, lines[line + 1].startOffset - lineSeparator.length)
        }
    }

    override fun getLineContent(line: Int): CharSequence {
        checkLine(line)
        val line = lines[line - 1]
        return currentRope.subSequence(line.startOffset, line.startOffset + line.length)
    }

    override fun getLineNumber(offset: Int): Int {
        return lines.binarySearch { it.startOffset - offset }.let {
            if (it < 0) {
                -it - 2
            } else {
                it
            }
        } + 1
    }

    override fun getPosition(offset: Int): Position {
        val line = getLineNumber(offset)
        return Position(line, offset - lines[line].startOffset)
    }

    override fun getOffset(line: Int, column: Int): Int {
        return lines[line].startOffset + column
    }

    override fun getLineSeparator(): LineSeparator {
        return lineSeparator
    }


    private fun onInsertText(event: TextChangeEvent) {
        val text = event.text

        // start line
        val startLine = getLineNumber(event.startOffset) - 1
        var lineCount = 0
        var currentOffset = 0
        var currentLine = lines[startLine]
        cacheLines.clear()

        while (currentOffset < text.length) {
            val currentChar = text.get(currentOffset)
            val nextChar = text.getOrNull(currentOffset + 1) ?: '\u0000'
            if (currentChar != '\n' && currentChar != '\r') {
                currentOffset++
                continue
            }

            val lineSeparator = LineSeparator.fromChar(currentChar, nextChar)
            currentLine.length =
                currentOffset + event.startOffset - currentLine.startOffset
            val nextLine =
                Line(
                    currentOffset + event.startOffset + lineSeparator.length,
                    0,
                    lineSeparator.length
                )
            currentLine = nextLine
            cacheLines.add(nextLine)
            // }
            lineCount++
            currentOffset += lineSeparator.length
        }

        // last line
        currentLine.length = text.length - currentLine.startOffset + event.startOffset

        // add new lines
        lines.addAll(startLine + 1, cacheLines)

        // update old line offset
        for (i in startLine + cacheLines.size + 1 until lines.size) {
            lines[i].startOffset += event.text.length
        }
    }


    private fun onDeleteText(event: TextChangeEvent) {
        val text = event.text

        // start line
        var currentLineNumber = getLineNumber(event.endOffset) - 1
        var currentOffset = event.text.length - 1
        var currentLine = lines[currentLineNumber]

        while (currentOffset >= 0) {
            val currentChar = text[currentOffset]
            val lastChar = text.getOrNull(currentOffset - 1) ?: '\u0000'
            if (currentChar != '\r' && currentChar != '\n') {
                currentOffset--
                currentLine.length--
                continue
            }

            val lineSeparator = LineSeparator.fromChar(lastChar, currentChar)

            currentOffset -= lineSeparator.length

            val lastLine = lines.getOrNull(currentLineNumber - 1) ?: break

            if (currentLine.length < 1) {
                lines.removeAt(currentLineNumber)
            } else {
                lastLine.length = currentLine.length + lastLine.length - lastLine.lineSeparatorLength
                lastLine.lineSeparatorLength = 0
                lines.removeAt(currentLineNumber)
            }

            currentLine = lastLine
            currentLineNumber = max(0, currentLineNumber - 1)

        }

        // remove lastLine
        if (currentLine.length < 1) {
            lines.removeAt(currentLineNumber)
        }

        // update old line offset
        for (i in currentLineNumber + 1 until lines.size) {
            lines[i].startOffset -= event.text.length
        }


    }

    override fun onTextChange(event: TextChangeEvent) {
        currentRope = event.newRope
        when (event.eventType) {
            TextChangeEvent.EventType.INSERT -> onInsertText(event)
            else -> onDeleteText(event)
        }
    }

    private data class Line(
        var startOffset: Int,
        var length: Int,
        var lineSeparatorLength: Int = 0
    )
}
