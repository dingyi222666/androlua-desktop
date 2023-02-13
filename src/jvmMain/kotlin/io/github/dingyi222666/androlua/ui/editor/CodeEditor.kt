package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import javax.swing.BoxLayout
import javax.swing.JPanel

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/


@Composable
fun CodeEditor(model: EditorModel, modifier: Modifier = Modifier) = key(model) {
    Box(modifier = modifier) {
        SwingPanel(
            background = Color.White,
            factory = {
                JPanel().apply {
                    layout = BoxLayout(this, BoxLayout.Y_AXIS)
                    add(model.getEditorPane())
                }
            }
        )
    }
}