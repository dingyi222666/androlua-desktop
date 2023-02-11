package io.github.dingyi222666.androlua.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CursorDropdownMenu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import jdk.jfr.Enabled

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/

@Composable
fun ColumnScope.DropdownSubMenu(
    text: String,
    enabled: Boolean,
    parentSize: Size,
    context: @Composable ColumnScope.(currentSize: Size) -> Unit = {}
) {

    var isExpanded by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }

    DropdownMenuItem(
        text = {
            Box(
                modifier = Modifier

                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        size = coordinates.size.toSize()
                    }
            ) {

                Text(
                    text = text,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            isExpanded = true
                        }
                )

                DropdownMenu(
                    offset = DpOffset(
                        x = parentSize.width.dp,
                        y = parentSize.height.dp
                    ),
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    context(Size(size.width - parentSize.width, -parentSize.height))
                }
            }
        },
        modifier = Modifier
            .width(210.dp)
            .height(40.dp),
        trailingIcon = {
            Icon(
                painter = painterResource("images/menu_right_outline.xml"),
                contentDescription = "Menu",
            )
        },
        onClick = {
            isExpanded = true
        },
        enabled = enabled
    )
}