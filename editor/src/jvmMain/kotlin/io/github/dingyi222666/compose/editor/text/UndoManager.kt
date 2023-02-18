package io.github.dingyi222666.compose.editor.text

/**
 * @author: dingyi
 * @date: 2023/2/19
 * @description:
 **/
internal class UndoManager : TextChangeListener {
    private var undoStack = ArrayDeque<Rope>()
    private var redoStack = ArrayDeque<Rope>()

    override fun onTextChange(event: TextChangeEvent) {
        undoStack.addFirst(event.newRope)
        redoStack.clear()
    }

    fun undo(textModel: TextModel) {
        if (undoStack.isEmpty()) {
            return
        }
        val rope = undoStack.removeFirst()
        redoStack.addFirst(textModel.text)
        textModel.text = rope
    }

    fun redo(textModel: TextModel) {
        if (redoStack.isEmpty()) {
            return
        }
        val rope = redoStack.removeFirst()
        undoStack.addFirst(textModel.text)
        textModel.text = rope
    }

    fun canUndo() = undoStack.isNotEmpty()

    fun canRedo() = redoStack.isNotEmpty()
}