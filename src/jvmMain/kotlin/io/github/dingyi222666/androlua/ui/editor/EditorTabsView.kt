package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.dingyi222666.androlua.ui.component.DesktopScrollableTabRow

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/

@Composable
fun EditorTabsView(model: EditorState) {

    Box(
        // Modifier for the Box, fill the maximum available width
        modifier = Modifier.fillMaxWidth()
    ) {
        if (model.editors.isNotEmpty()) {
            EditorScrollableTab(model)
        }

    }
}

@Composable
fun BoxScope.EditorScrollableTab(model: EditorState) {

    // A state to remember the scroll state of the ScrollableTabRow
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentSize(align = Alignment.CenterStart)
    ) {
        DesktopScrollableTabRow(
            selectedTabIndex = model.currentActiveEditorIndex,
            edgePadding = 16.dp,
            scrollState = scrollState,
            modifier = Modifier
                .wrapContentSize(align = Alignment.CenterStart)
            // Modifier for the ScrollableTabRow, fill the maximum available width
        ) {

            for (index in model.editors.indices) {
                val editorModel = model.editors[index]

                Tab(
                    text = { Text(editorModel.path.name) },
                    selected = model.currentActiveEditorIndex == index,
                    onClick = { model.currentActiveEditorIndex = index }
                )
            }
        }
    }

    // A Scrollbar for the ScrollableTabRow, aligned to the top
    HorizontalScrollbar(
        // Modifier for the Scrollbar, align to the top start
        modifier = Modifier.align(Alignment.TopStart),
        // The adapter for the Scrollbar, providing the scroll state and the content size
        adapter = rememberScrollbarAdapter(
            scrollState = scrollState
        )
    )
}