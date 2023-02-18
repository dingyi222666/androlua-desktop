package io.github.dingyi222666.compose.editor.text

/**
 * @author: dingyi
 * @date: 2023/2/17
 * @description:
 **/
class TextModel : CharSequence, LineTracker {
    internal var text = Rope("")

    private val listener = mutableListOf<TextChangeListener>()

    private val undoManager = UndoManager().apply {
        addTextChangeListener(this)
    }

    private val lineTracker = ListLineTracker().apply {
        addTextChangeListener(this)
    }

    fun getLineTracker() = lineTracker

    fun getTextLength() = text.length

    fun getChar(index: Int): Char {
        if (index < 0 || index >= getTextLength()) {
            throw IndexOutOfBoundsException()
        }
        return getCharImpl(index)
    }

    private fun getCharImpl(index: Int): Char {
        return text.get(index)
    }

    fun insert(text: CharSequence) {
        insert(kotlin.math.max(0, getTextLength()), text)
    }

    fun insert(position: Int, text: CharSequence) {
        val inserted = this.text.insert(position, text)
        dispatchEvent(
            TextChangeEvent(
                text,
                inserted,
                position,
                position + text.length,
                TextChangeEvent.EventType.INSERT
            )
        )
        this.text = inserted
    }

    fun addTextChangeListener(listener: TextChangeListener) {
        this.listener.add(listener)
    }

    fun removeTextChangeListener(listener: TextChangeListener) {
        this.listener.remove(listener)
    }

    private fun dispatchEvent(event: TextChangeEvent) {
        listener.forEach {
            it.onTextChange(event)
        }
    }


    override fun getLineCount(): Int = lineTracker.getLineCount()

    override fun getLineStartOffset(line: Int): Int = lineTracker.getLineStartOffset(line)

    override fun getLineEndOffset(line: Int): Int = lineTracker.getLineEndOffset(line)

    override fun getLineContent(line: Int): CharSequence = lineTracker.getLineContent(line)

    override fun getLineNumber(offset: Int): Int = lineTracker.getLineNumber(offset)

    override fun getPosition(offset: Int): Position = lineTracker.getPosition(offset)

    override fun getOffset(line: Int, column: Int): Int  = lineTracker.getOffset(line, column)

    override fun getOffset(position: Position): Int = lineTracker.getOffset(position)

    override fun getLineSeparator(): LineSeparator = lineTracker.getLineSeparator()

    override val length: Int
        get() = text.length

    override fun get(index: Int) = text.get(index)

    override fun subSequence(startIndex: Int, endIndex: Int) = text.subSequence(startIndex, endIndex)

    override fun toString(): String {
        return text.toString()
    }

}