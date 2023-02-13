package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/

@Composable
fun EditorTabsView(model: EditorState) = Row(
    Modifier
        .height(36.dp)
        .horizontalScroll(rememberScrollState())
) {
    Column {
        ScrollableTabRow(
            selectedTabIndex = model.currentSelectedEditorIndex,
            modifier = Modifier.wrapContentWidth(),
            edgePadding = 16.dp
        ) {
            for (index in model.editors.indices) {
                val editorModel = model.editors[index]
                Tab(
                    text = { Text(editorModel.path.name) },
                    selected = model.currentSelectedEditorIndex == index,
                    onClick = { model.currentSelectedEditorIndex = index }
                )
            }
        }
    }
}
