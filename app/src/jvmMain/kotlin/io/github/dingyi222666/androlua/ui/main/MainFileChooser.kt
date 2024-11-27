package io.github.dingyi222666.androlua.ui.main

import androidx.compose.material3.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import io.github.dingyi222666.androlua.ui.common.LocalWindowScope
import io.github.dingyi222666.androlua.ui.component.FileChooserDialog
import io.github.dingyi222666.androlua.ui.component.FileChooserDialogMode
import kotlinx.coroutines.launch

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/

@Composable
fun MainFileChooser(state: MainState, openState: Boolean, onDismissRequest: () -> Unit) {

    var isShowErrorProjectState by remember { mutableStateOf(false) }

    if (openState) {
        with(LocalWindowScope.current) {
            FileChooserDialog(
                mode = FileChooserDialogMode.Mode.LOAD_DIR, id = 0x12, title = "选择项目地址"
            ) { files ->

                val selectDir = files.getOrNull(0) ?: return@FileChooserDialog

                state.scope.launch {
                    runCatching {
                        state.openProject(selectDir)
                    }.onFailure {
                        it.printStackTrace()
                        isShowErrorProjectState = true
                    }
                }

                onDismissRequest()

            }


        }
    }

    if (isShowErrorProjectState) {
        OpenErrorProjectDialog(isShowErrorProjectState) {
            isShowErrorProjectState = false
        }
    }

}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OpenErrorProjectDialog(openState: Boolean, onDismissRequest: () -> Unit) {
    if (openState) {
        AlertDialog(onDismissRequest = onDismissRequest,
            shape = MaterialTheme.shapes.medium,
            title = { Text("打开项目失败") },
            text = { Text("你可能打开的不是一个AndroLua+项目呢") },
            confirmButton = {
                Button(onClick = {
                    onDismissRequest()
                }) {
                    Text("确定")
                }
            })
    }
}