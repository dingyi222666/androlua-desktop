package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.dingyi222666.androlua.ui.component.DesktopScrollableTabRow
import io.github.dingyi222666.androlua.ui.fileTree.getFileIcon

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
            edgePadding = 0.dp,
            scrollState = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.CenterStart)
            // Modifier for the ScrollableTabRow, fill the maximum available width
        ) {

            for (index in model.editors.indices) {
                val editorModel = model.editors[index]
                Tab(
                    text = {
                        Text(editorModel.path.name)
                    },
                    /*
                    icon = {
                        // 不怎么支持，removed
                          Icon(
                              modifier = Modifier.width(24.dp).height(24.dp),
                              painter = getFileIcon(file = editorModel.path),
                              contentDescription = null
                          )
                    }, */
                    selected = model.currentActiveEditorIndex == index,
                    onClick = { model.activeEditor(index) }
                )

            }
        }
    }

    // A Scrollbar for the ScrollableTabRow, aligned to the top
    HorizontalScrollbar(
        // Modifier for the Scrollbar, align to the top start
        modifier = Modifier.align(Alignment.TopStart)
            .height(4.dp),
        // The adapter for the Scrollbar, providing the scroll state and the content size
        adapter = rememberScrollbarAdapter(
            scrollState = scrollState
        ),
        style = ScrollbarStyle(
            minimalHeight = 4.dp,
            thickness = 8.dp,
            shape = RoundedCornerShape(4.dp),
            hoverDurationMillis = 300,
            unhoverColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0f),
            hoverColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.80f)
        )
    )
}