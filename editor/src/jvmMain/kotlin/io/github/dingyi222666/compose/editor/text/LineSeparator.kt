package io.github.dingyi222666.compose.editor.text

import java.util.*


/**
 * @author: dingyi
 * @date: 2023/2/17
 * @description:
 **/

/**
 * Line separator types
 *
 * @author Rosemoe
 */
enum class LineSeparator(
    /**
     * Get the text of this separator
     */
    val content: String
) {
    LF("\n"),
    CR("\r"),
    CRLF("\r\n"),
    NONE("");

    /**
     * Get text length of this separator
     */
    val length: Int = content.length

    companion object {
        fun fromChar(char: Char, nextChar: Char): LineSeparator {
            return when (char) {
                '\n' -> LF
                '\r' -> if (nextChar == '\n') CRLF else CR
                else -> NONE
            }
        }
    }


}

