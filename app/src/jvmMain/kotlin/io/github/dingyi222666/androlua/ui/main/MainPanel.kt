package io.github.dingyi222666.androlua.ui.main

import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import io.github.dingyi222666.androlua.ui.common.LocalWindowScope
import io.github.dingyi222666.androlua.ui.component.SplitterState
import io.github.dingyi222666.androlua.ui.component.VerticalSplittable

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/

@Composable
fun MainPanel(
    state: MainState,
    leftPanel: @Composable () -> Unit,
    rightPanel: @Composable BoxScope.() -> Unit
) {

    val panelState = remember { PanelState() }

    val animatedSize = if (panelState.splitter.isResizing) {
        if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize
    } else {
        animateDpAsState(
            if (panelState.isExpanded) panelState.expandedSize else panelState.collapsedSize,
            SpringSpec(stiffness = StiffnessLow)
        ).value
    }

    val maxWidth = LocalWindowScope.current.window.width

    VerticalSplittable(
        Modifier.fillMaxSize(),
        panelState.splitter,
        onResize = {
            val expandedSize =
                (panelState.expandedSize + it).coerceAtLeast(panelState.expandedSizeMin)
            panelState.expandedSize = min(expandedSize, (maxWidth - 26).dp)
        }
    ) {
        ResizablePanel(
            Modifier.width(animatedSize)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surfaceVariant), panelState
        ) {
            leftPanel()
        }
        Box {
            rightPanel()
        }
    }
}

private class PanelState {
    val collapsedSize = 24.dp
    var expandedSize by mutableStateOf(280.dp)
    val expandedSizeMin = 26.dp
    var isExpanded by mutableStateOf(true)
    val splitter = SplitterState()
}

@Composable
private fun ResizablePanel(
    modifier: Modifier,
    state: PanelState,
    content: @Composable () -> Unit,
) {
    val alpha by animateFloatAsState(if (state.isExpanded) 1f else 0f, SpringSpec(stiffness = StiffnessLow))

    Box(modifier) {
        Box(Modifier.fillMaxSize().graphicsLayer(alpha = alpha)) {
            content()
        }

        Icon(
            if (state.isExpanded) Icons.Default.ArrowBack else Icons.Default.ArrowForward,
            contentDescription = if (state.isExpanded) "Collapse" else "Expand",
            tint = LocalContentColor.current,
            modifier = Modifier
                .padding(top = 4.dp)
                .width(24.dp)
                .clickable {
                    state.isExpanded = !state.isExpanded
                }
                .padding(4.dp)
                .align(Alignment.TopEnd)
        )
    }
}
