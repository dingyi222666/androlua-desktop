package io.github.dingyi222666.androlua.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import io.github.dingyi222666.androlua.ui.common.LocalWindowScope
import io.github.dingyi222666.androlua.ui.common.WindowState
import io.github.dingyi222666.androlua.ui.resources.rememberVectorPainter

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    contentPadding: PaddingValues = TopAppBarDefaults.windowInsets.asPaddingValues(),
    leading: @Composable (RowScope.() -> Unit)? = null,
    trailing: @Composable (RowScope.() -> Unit)? = null,
    title: @Composable () -> Unit,
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        modifier = modifier
    ) {

        var leftSectionWidth = 0.dp
        var rightSectionWidth = 0.dp
        var titlePadding by remember { mutableStateOf(PaddingValues()) }

        val calculateTitlePadding = fun() {
            val dx = leftSectionWidth - rightSectionWidth
            var start = 0.dp
            var end = 0.dp
            if (dx < 0.dp) start += -dx else end += dx
            titlePadding = PaddingValues(start = start, end = end)
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .height(56.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // leading
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                with(LocalDensity.current) {
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .onGloballyPositioned { coordinates ->
                                val width = coordinates.size.width.toDp()
                                if (width != leftSectionWidth) {
                                    leftSectionWidth = width
                                    calculateTitlePadding()
                                }
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        content = leading ?: {}
                    )
                }
            }

            // title
            Row(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(titlePadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.titleMedium, content = title)
            }

            // trailing
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                with(LocalDensity.current) {
                    Row(
                        Modifier
                            .fillMaxHeight()
                            .onGloballyPositioned { coordinates ->
                                val width = coordinates.size.width.toDp()
                                if (width != rightSectionWidth) {
                                    rightSectionWidth = width
                                    calculateTitlePadding()
                                }
                            },
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        content = trailing ?: {}
                    )
                }
            }
        }
    }
}

@Composable
fun TitleBar(
    state: WindowState,
    titleText: String = "AndroLua+ Desktop",
    leading: @Composable RowScope.() -> Unit = {},
    trailing: @Composable (RowScope.() -> Unit)? = {},
) = with(LocalWindowScope.current) {
    WindowDraggableArea {
        CenterTopAppBar(
            leading = leading,
            title = {
                Text(
                    text = titleText,
                    modifier = Modifier
                        .padding(start = 18.dp, end = 24.dp)
                )
            },
            trailing = {
                trailing?.invoke(this)

                TitleBarIcon(
                    onClick = {
                        state.window.isMinimized = true
                    },
                    painter = painterResource("images/minus.xml"),
                    contentDescription = "Minimized Window",
                )

                TitleBarIcon(
                    painter = if (state.window.placement == WindowPlacement.Maximized) {
                        painterResource("images/circle_multiple_outline.xml")
                    } else {
                        painterResource("images/circle_outline.xml")
                    },
                    contentDescription = if (state.window.placement == WindowPlacement.Maximized) {
                        "Restore Window"
                    } else {
                        "Maximize Window"
                    },
                    onClick = {
                        if (state.window.placement == WindowPlacement.Maximized) {
                            state.window.placement = WindowPlacement.Floating
                        } else {
                            state.window.placement = WindowPlacement.Maximized
                        }
                    },
                )

                TitleBarIcon(
                    painter = rememberVectorPainter(
                        Icons.Default.Close, MaterialTheme.colorScheme.onSurface
                    ),
                    contentDescription = "Close Window",
                    onClick = state::callExit
                )
            }
        )

    }
}

@Composable
fun TitleBarIcon(
    painter: Painter,
    modifier: Modifier = Modifier
        .padding(end = 24.dp)
        .height(24.dp)
        .width(24.dp),
    contentDescription: String = "empty",
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
        )
        content()
    }

}
