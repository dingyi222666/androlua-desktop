package io.github.dingyi222666.androlua.ui.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import io.github.dingyi222666.androlua.ui.view.DropdownSubMenu
import io.github.dingyi222666.androlua.ui.view.TitleBar


/**
 * @author: dingyi
 * @date: 2023/2/11
 * @description:
 **/

@Composable
fun WindowScope.MainWindow(state: MainState) {
    Column(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        MainTitleBar(state)
    }
}


@Composable
fun WindowScope.MainTitleBar(state: MainState) {

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


@Composable
fun MainTitleBarPopupMenu(state: MainState, expanded: Boolean, onDismissRequest: () -> Unit = {}) {

    val menuItems = mapOf(
        "文件" to listOf(
            "新建",
            "打开",
            "保存",
            "另存为",
            "关闭",
            "退出",
        ),
        "编辑" to listOf(
            "撤销",
            "重做",
            "剪切",
            "复制",
            "粘贴",
            "删除",
            "全选",
        ),
        "运行" to listOf(
            "运行",
            "调试",
            "停止",
        ),
        "帮助" to listOf(
            "帮助",
            "关于",
        ),
    )

    val menuClicks = { value: String ->

    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        for ((key, value) in menuItems) {
            DropdownSubMenu(key, expanded, Size(-260f, -40f)) {
                for (item in value) {
                    DropdownMenuItem(
                        text = {
                            Text(item)
                        },
                        modifier = Modifier
                            .width(210.dp)
                            .height(40.dp),
                        onClick = {
                            menuClicks(item)
                        },
                        enabled = true
                    )
                }
            }

        }
    }

}
