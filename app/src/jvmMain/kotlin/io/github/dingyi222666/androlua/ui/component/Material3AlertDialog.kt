package io.github.dingyi222666.androlua.ui.component

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialogProvider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import java.awt.event.KeyEvent
import kotlin.math.max

@Composable
internal fun Material3AlertDialogContent(
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)?,
    title: (@Composable () -> Unit)?,
    text: @Composable (() -> Unit)?,
    shape: Shape,
    containerColor: Color,
    tonalElevation: Dp,
    buttonContentColor: Color,
    iconContentColor: Color,
    titleContentColor: Color,
    textContentColor: Color,
    // See next
    onDismissRequest: () -> Unit,
) {

    Surface(
        modifier = modifier.pointerInput(onDismissRequest) {
            detectTapGestures(onPress = {
                // Workaround to disable clicks on Surface background https://github.com/JetBrains/compose-jb/issues/2581
            })
        },
        shape = shape,
        color = containerColor,
        tonalElevation = tonalElevation,
    ) {
        Column(
            modifier = Modifier.padding(DialogPadding)
        ) {
            icon?.let {
                CompositionLocalProvider(LocalContentColor provides iconContentColor) {
                    Box(
                        Modifier
                            .padding(IconPadding)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        icon()
                    }
                }
            }
            title?.let {
                CompositionLocalProvider(LocalContentColor provides titleContentColor) {
                    val textStyle = MaterialTheme.typography.headlineSmall
                    ProvideTextStyle(textStyle) {
                        Box(
                            // Align the title to the center when an icon is present.
                            Modifier
                                .padding(TitlePadding)
                                .align(
                                    if (icon == null) {
                                        Alignment.Start
                                    } else {
                                        Alignment.CenterHorizontally
                                    }
                                )
                        ) {
                            title()
                        }
                    }
                }
            }
            text?.let {
                CompositionLocalProvider(LocalContentColor provides textContentColor) {
                    val textStyle =
                        MaterialTheme.typography.bodyMedium
                    ProvideTextStyle(textStyle) {
                        Box(
                            Modifier
                                .weight(weight = 1f, fill = false)
                                .padding(TextPadding)
                                .align(Alignment.Start)
                        ) {
                            text()
                        }
                    }
                }
            }
            Box(modifier = Modifier.align(Alignment.End)) {
                CompositionLocalProvider(LocalContentColor provides buttonContentColor) {
                    val textStyle =
                        MaterialTheme.typography.labelLarge
                    ProvideTextStyle(value = textStyle, content = buttons)
                }
            }
        }
    }
}

/**
 * Simple clone of FlowRow that arranges its children in a horizontal flow with limited
 * customization.
 */
@Composable
internal fun AlertDialogFlowRow(
    mainAxisSpacing: Dp,
    crossAxisSpacing: Dp,
    content: @Composable () -> Unit
) {
    Layout(content) { measurables, constraints ->
        val sequences = mutableListOf<List<Placeable>>()
        val crossAxisSizes = mutableListOf<Int>()
        val crossAxisPositions = mutableListOf<Int>()

        var mainAxisSpace = 0
        var crossAxisSpace = 0

        val currentSequence = mutableListOf<Placeable>()
        var currentMainAxisSize = 0
        var currentCrossAxisSize = 0

        // Return whether the placeable can be added to the current sequence.
        fun canAddToCurrentSequence(placeable: Placeable) =
            currentSequence.isEmpty() || currentMainAxisSize + mainAxisSpacing.roundToPx() +
                    placeable.width <= constraints.maxWidth

        // Store current sequence information and start a new sequence.
        fun startNewSequence() {
            if (sequences.isNotEmpty()) {
                crossAxisSpace += crossAxisSpacing.roundToPx()
            }
            sequences += currentSequence.toList()
            crossAxisSizes += currentCrossAxisSize
            crossAxisPositions += crossAxisSpace

            crossAxisSpace += currentCrossAxisSize
            mainAxisSpace = max(mainAxisSpace, currentMainAxisSize)

            currentSequence.clear()
            currentMainAxisSize = 0
            currentCrossAxisSize = 0
        }

        for (measurable in measurables) {
            // Ask the child for its preferred size.
            val placeable = measurable.measure(constraints)

            // Start a new sequence if there is not enough space.
            if (!canAddToCurrentSequence(placeable)) startNewSequence()

            // Add the child to the current sequence.
            if (currentSequence.isNotEmpty()) {
                currentMainAxisSize += mainAxisSpacing.roundToPx()
            }
            currentSequence.add(placeable)
            currentMainAxisSize += placeable.width
            currentCrossAxisSize = max(currentCrossAxisSize, placeable.height)
        }

        if (currentSequence.isNotEmpty()) startNewSequence()

        val mainAxisLayoutSize = max(mainAxisSpace, constraints.minWidth)

        val crossAxisLayoutSize = max(crossAxisSpace, constraints.minHeight)

        val layoutWidth = mainAxisLayoutSize

        val layoutHeight = crossAxisLayoutSize

        layout(layoutWidth, layoutHeight) {
            sequences.forEachIndexed { i, placeables ->
                val childrenMainAxisSizes = IntArray(placeables.size) { j ->
                    placeables[j].width +
                            if (j < placeables.lastIndex) mainAxisSpacing.roundToPx() else 0
                }
                val arrangement = Arrangement.Bottom
                // TODO(soboleva): rtl support
                // Handle vertical direction
                val mainAxisPositions = IntArray(childrenMainAxisSizes.size) { 0 }
                with(arrangement) {
                    arrange(mainAxisLayoutSize, childrenMainAxisSizes, mainAxisPositions)
                }
                placeables.forEachIndexed { j, placeable ->
                    placeable.place(
                        x = mainAxisPositions[j],
                        y = crossAxisPositions[i]
                    )
                }
            }
        }
    }
}

internal val DialogMinWidth = 280.dp
internal val DialogMaxWidth = 560.dp

// Paddings for each of the dialog's parts.
private val DialogPadding = PaddingValues(all = 24.dp)
private val IconPadding = PaddingValues(bottom = 16.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val TextPadding = PaddingValues(bottom = 24.dp)

/**
 * <a href="https://m3.material.io/components/dialogs/overview" class="external" target="_blank">Material Design basic dialog</a>.
 *
 * Dialogs provide important prompts in a user flow. They can require an action, communicate
 * information, or help users accomplish a task.
 *
 * ![Basic dialog image](https://developer.android.com/images/reference/androidx/compose/material3/basic-dialog.png)
 *
 * The dialog will position its buttons, typically [TextButton]s, based on the available space.
 * By default it will try to place them horizontally next to each other and fallback to horizontal
 * placement if not enough space is available.
 *
 * Simple usage:
 * @sample androidx.compose.material3.samples.AlertDialogSample
 *
 * Usage with a "Hero" icon:
 * @sample androidx.compose.material3.samples.AlertDialogWithIconSample
 *
 * @param onDismissRequest called when the user tries to dismiss the Dialog by clicking outside
 * or pressing the back button. This is not called when the dismiss button is clicked.
 * @param confirmButton button which is meant to confirm a proposed action, thus resolving what
 * triggered the dialog. The dialog does not set up any events for this button so they need to be
 * set up by the caller.
 * @param modifier the [Modifier] to be applied to this dialog
 * @param dismissButton button which is meant to dismiss the dialog. The dialog does not set up any
 * events for this button so they need to be set up by the caller.
 * @param icon optional icon that will appear above the [title] or above the [text], in case a
 * title was not provided.
 * @param title title which should specify the purpose of the dialog. The title is not mandatory,
 * because there may be sufficient information inside the [text].
 * @param text text which presents the details regarding the dialog's purpose.
 * @param shape defines the shape of this dialog's container
 * @param containerColor the color used for the background of this dialog. Use [Color.Transparent]
 * to have no color.
 * @param iconContentColor the content color used for the icon.
 * @param titleContentColor the content color used for the title.
 * @param textContentColor the content color used for the text.
 * @param tonalElevation when [containerColor] is [ColorScheme.surface], a translucent primary color
 * overlay is applied on top of the container. A higher tonal elevation value will result in a
 * darker color in light theme and lighter color in dark theme. See also: [Surface].
 * @param properties typically platform specific properties to further configure the dialog.
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Material3AlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = Material3AlertDialogDefaults.shape,
    containerColor: Color = Material3AlertDialogDefaults.containerColor,
    iconContentColor: Color = Material3AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = Material3AlertDialogDefaults.titleContentColor,
    textContentColor: Color = Material3AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = Material3AlertDialogDefaults.TonalElevation,
    dialogProvider: AlertDialogProvider = FixedPopupAlertDialogProvider
) {
    with(dialogProvider) {
        AlertDialog(onDismissRequest = onDismissRequest) {
            Material3AlertDialogContent(
                buttons = {
                    AlertDialogFlowRow(
                        mainAxisSpacing = ButtonsMainAxisSpacing,
                        crossAxisSpacing = ButtonsCrossAxisSpacing
                    ) {
                        dismissButton?.invoke()
                        confirmButton()
                    }
                },
                modifier = modifier,
                icon = icon,
                title = title,
                text = text,
                shape = shape,
                containerColor = containerColor,
                tonalElevation = tonalElevation,
                // Note that a button content color is provided here from the dialog's token, but in
                // most cases, TextButtons should be used for dismiss and confirm buttons.
                // TextButtons will not consume this provided content color value, and will used their
                // own defined or default colors.
                buttonContentColor = MaterialTheme.colorScheme.primary,
                iconContentColor = iconContentColor,
                titleContentColor = titleContentColor,
                textContentColor = textContentColor,
                onDismissRequest = onDismissRequest
            )
        }
    }
}


// TODO(https://github.com/JetBrains/compose-jb/issues/933): is it right to use Popup to show a
//  dialog?
/**
 * Shows Alert dialog as popup in the middle of the window.
 */
@ExperimentalMaterialApi
object FixedPopupAlertDialogProvider : AlertDialogProvider {
    @Composable
    override fun AlertDialog(
        onDismissRequest: () -> Unit,
        content: @Composable () -> Unit
    ) {
        // Popups on the desktop are by default embedded in the component in which
        // they are defined and aligned within its bounds. But an [AlertDialog] needs
        // to be aligned within the window, not the parent component, so we cannot use
        // [alignment] property of [Popup] and have to use [Box] that fills all the
        // available space. Also [Box] provides a dismiss request feature when clicked
        // outside of the [AlertDialog] content.
        Popup(
            popupPositionProvider = object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset = IntOffset.Zero
            },
            focusable = true,
            onDismissRequest = onDismissRequest,
            onKeyEvent = {
                if (it.type == KeyEventType.KeyDown && it.awtEventOrNull?.keyCode == KeyEvent.VK_ESCAPE) {
                    onDismissRequest()
                    true
                } else {
                    false
                }
            },
        ) {
            val scrimColor = Color.Black.copy(alpha = 0.32f) //todo configure scrim color in function arguments
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(scrimColor)
                    .pointerInput(onDismissRequest) {
                        detectTapGestures(onPress = { onDismissRequest() })
                    },
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}

/**
 * Contains default values used for [Material3AlertDialog]
 */
object Material3AlertDialogDefaults {
    /** The default shape for alert dialogs */
    val shape: Shape @Composable get() = MaterialTheme.shapes.extraLarge

    /** The default container color for alert dialogs */
    val containerColor: Color @Composable get() = MaterialTheme.colorScheme.surface

    /** The default icon color for alert dialogs */
    val iconContentColor: Color @Composable get() = MaterialTheme.colorScheme.secondary

    /** The default title color for alert dialogs */
    val titleContentColor: Color @Composable get() = MaterialTheme.colorScheme.onSurface

    /** The default text color for alert dialogs */
    val textContentColor: Color @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    /** The default tonal elevation for alert dialogs */
    // md.sys.elevation.level3
    val TonalElevation: Dp = 6.dp
}

private val ButtonsMainAxisSpacing = 8.dp
private val ButtonsCrossAxisSpacing = 12.dp
