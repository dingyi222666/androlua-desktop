package io.github.dingyi222666.androlua.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.github.dingyi222666.androlua.LocalApplicationState
import io.github.dingyi222666.androlua.ui.common.LocalWindowScope
import io.github.dingyi222666.androlua.ui.component.FileChooserDialog
import io.github.dingyi222666.androlua.ui.component.FileChooserDialogMode
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/

@Composable
fun MainFileChooser(openState: Boolean, onDismissRequest: () -> Unit) = with(LocalWindowScope.current) {
    if (openState) {
        FileChooserDialog(
            mode = FileChooserDialogMode.Mode.LOAD_DIR,
            id = 0x12,
            title = "Your dialog title"
        ) { files ->
            println(openState)
            onDismissRequest()
        }
    }
}