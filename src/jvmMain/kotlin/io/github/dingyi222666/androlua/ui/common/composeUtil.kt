package io.github.dingyi222666.androlua.ui.common

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.awt.Cursor

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/

fun Modifier.cursorForHorizontalResize(): Modifier =
    this.pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

fun clearState(vararg states: MutableState<Boolean>) {
    states.forEach {
        it.value = false
    }
}