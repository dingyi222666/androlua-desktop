package io.github.dingyi222666.androlua.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.dingyi222666.androlua.core.project.Project
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

    val currentProjectState by state.currentProject.collectAsState()

    Column {

        TitleBar(
            state = state,
            titleText = LocalAppResources.current.appTitle,
            leading = {

                Image(
                    painter = LocalAppResources.current.appIcon,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .height(24.dp)
                        .width(24.dp),
                    contentScale = ContentScale.Fit
                )

                TitleBarIcon(
                    painter = painterResource("images/menu.xml"),
                    contentDescription = "Menu",
                    modifier = Modifier
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
                if (currentProjectState != Project.EMPTY) {
                    TitleBarIcon(
                        painter = painterResource("images/play.xml"),
                        contentDescription = "Run",
                    )
                }
            }
        )

        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )

    }


}
