package io.github.dingyi222666.androlua.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/

@Composable
fun DropdownSubMenu(
    text: String,
    enabled: Boolean,
    parentSize: Size,
    context: @Composable (ColumnScope.(currentSize: Size, parentMenuExpand: MutableState<Boolean>) -> Unit) = { _, _ -> }
) {

    val isExpanded = mutableStateOf(false)
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
                            isExpanded.value = true
                        }
                )

                DropdownMenu(
                    offset = DpOffset(
                        x = parentSize.width.dp,
                        y = parentSize.height.dp
                    ),
                    expanded = isExpanded.value,
                    onDismissRequest = { isExpanded.value = false }
                ) {
                    context(Size(size.width - parentSize.width, -parentSize.height), isExpanded)
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
            isExpanded.value = true
        },
        enabled = enabled
    )
}