package io.github.dingyi222666.androlua.ui.fileTree

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.bonsai.core.Bonsai
import cafe.adriel.bonsai.core.BonsaiStyle
import cafe.adriel.bonsai.core.OnNodeClick
import cafe.adriel.bonsai.core.node.Node
import cafe.adriel.bonsai.core.tree.Tree
import io.github.dingyi222666.androlua.ui.resources.rememberVectorPainter
import okio.Path
import java.io.File


/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/

@Composable
fun FileTree(
    tree: Tree<Path>,
    onClick: OnNodeClick<Path> = null,
    style: BonsaiStyle<Path> = NewFileSystemBonsaiStyle()
) {
    Bonsai(
        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
        onClick = onClick,
        tree = tree,
        style = style
    )
}

@Composable
fun NewFileSystemBonsaiStyle(): BonsaiStyle<Path> = BonsaiStyle(
    nodeNameStartPadding = 4.dp,
    nodeIconSize = 14.dp,
    nodeCollapsedIcon = { node ->
        getNodeIcon(node)
    },
    nodeExpandedIcon = { node ->
        getNodeIcon(node)
    },
    // nodeSelectedBackgroundColor = MaterialTheme.colorScheme.secondaryContainer,
    nodeNameTextStyle = MaterialTheme.typography.labelMedium,
)

@Composable
internal fun getNodeIcon(node: Node<Path>): Painter? {
    val file = node.content.toFile()

    return when {
        file.isFile -> getFileIcon(file)

        else -> null
    }
}

@Composable
fun getFileIcon(file: File): Painter {
    return when (file.extension) {
        "lua", "aly" -> rememberVectorPainter(Icons.Default.Code, Color(0xff2f2f98))
        "kt" -> rememberVectorPainter(Icons.Default.Code, Color(0xFF3E86A0))
        "xml" -> rememberVectorPainter(Icons.Default.Code, Color(0xFFC19C5F))
        "txt" -> rememberVectorPainter(Icons.Default.Description, Color(0xFF87939A))
        "md" -> rememberVectorPainter(Icons.Default.Description, Color(0xFF87939A))
        "gitignore" -> rememberVectorPainter(Icons.Default.BrokenImage, Color(0xFF87939A))
        "gradle" -> rememberVectorPainter(Icons.Default.Build, Color(0xFF87939A))
        "kts" -> rememberVectorPainter(Icons.Default.Build, Color(0xFF3E86A0))
        "properties" -> rememberVectorPainter(Icons.Default.Settings, Color(0xFF62B543))
        "bat" -> rememberVectorPainter(Icons.Default.Launch, Color(0xFF87939A))
        else -> rememberVectorPainter(Icons.Default.TextSnippet, Color(0xFF87939A))
    }

}

@Composable
fun FileTreeViewTabView() = Surface {
    Row(
        Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(start = 8.dp, top = 2.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(
            "文件列表",
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}