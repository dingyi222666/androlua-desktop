package io.github.dingyi222666.androlua.ui.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.dingyi222666.androlua.ui.component.TitleBar
import io.github.dingyi222666.androlua.ui.component.TitleBarIcon
import io.github.dingyi222666.androlua.ui.resources.LocalAppResources

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
        titleText = LocalAppResources.current.appTitle,
        leading = {
            TitleBarIcon(
                painter = painterResource("images/menu.xml"),
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .height(24.dp)
                    .width(24.dp),
                onClick = {
                    showMainPopupMenu = true
                },
            ) {
                MainTitleBarPopupMenu(state, showMainPopupMenu) {
                    showMainPopupMenu = false
                }
            }
        },
        trailing = {
            TitleBarIcon(
                painter = painterResource("images/play.xml"),
                contentDescription = "Run",
            )
        }
    )

}
