package io.github.dingyi222666.androlua.ui.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author: dingyi
 * @date: 2023/2/12
 * @description:
 **/
@Composable
fun EditorEmptyView() = Box(Modifier.fillMaxSize()) {
    Column(Modifier.align(Alignment.Center)) {
        Icon(
            Icons.Default.Code,
            contentDescription = null,
            tint = LocalContentColor.current.copy(alpha = 0.60f),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            "Try to open a code file",
            color = LocalContentColor.current.copy(alpha = 0.60f),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
        )
    }
}