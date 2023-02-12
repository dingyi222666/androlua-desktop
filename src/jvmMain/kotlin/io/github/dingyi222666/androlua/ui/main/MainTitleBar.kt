package io.github.dingyi222666.androlua.ui.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.dingyi222666.androlua.ui.component.TitleBar

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/

@Composable
fun MainTitleBar(state: MainState) {

    var showMainPopupMenu by remember { mutableStateOf(false) }

    TitleBar(
        state = state,
        leading = {
            IconButton(
                onClick = {
                    showMainPopupMenu = true
                },
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .height(24.dp)
                    .width(24.dp)
            ) {
                Icon(
                    painter = painterResource("images/menu.xml"),
                    contentDescription = "Menu",
                )
                MainTitleBarPopupMenu(state, showMainPopupMenu) {
                    showMainPopupMenu = false
                }
            }
        },
        trailing = {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(end = 24.dp)
                    .height(24.dp)
                    .width(24.dp)
            ) {
                Icon(
                    painter = painterResource("images/play.xml"),
                    contentDescription = "Menu",
                )
            }
        }
    )

}
