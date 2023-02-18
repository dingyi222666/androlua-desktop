package io.github.dingyi222666.compose.editor.text

/**
 * @author: dingyi
 * @date: 2023/2/19
 * @description:
 **/

data class TextChangeEvent(
    val text: CharSequence,
    val newRope: Rope,
    val startOffset: Int,
    val endOffset: Int,
    val eventType: EventType
) {
    enum class EventType {
        DELETE, INSERT
    }
}

fun interface TextChangeListener {
    fun onTextChange(event: TextChangeEvent)
}