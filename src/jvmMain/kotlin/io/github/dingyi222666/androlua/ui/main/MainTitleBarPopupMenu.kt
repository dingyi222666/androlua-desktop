package io.github.dingyi222666.androlua.ui.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import io.github.dingyi222666.androlua.ui.view.DropdownSubMenu

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/

@Composable
fun MainTitleBarPopupMenu(state: MainState, expanded: Boolean, onDismissRequest: () -> Unit = {}) {
    val menuItems = mapOf(
        "文件" to listOf(
            "新建",
            "打开（项目)",
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
