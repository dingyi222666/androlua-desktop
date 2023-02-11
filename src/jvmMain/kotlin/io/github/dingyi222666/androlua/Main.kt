package io.github.dingyi222666.androlua

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import io.github.dingyi222666.androlua.Application
import io.github.dingyi222666.androlua.rememberApplicationState
import io.github.dingyi222666.androlua.ui.common.LocalAppResources
import io.github.dingyi222666.androlua.ui.common.rememberAppResources

fun main() = application {
    CompositionLocalProvider(LocalAppResources provides rememberAppResources()) {
       Application(rememberApplicationState())
    }
}
