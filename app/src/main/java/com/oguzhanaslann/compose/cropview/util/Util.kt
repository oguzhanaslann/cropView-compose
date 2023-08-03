package com.oguzhanaslann.compose.cropview.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp


internal fun boundedNewX(
    topLeft: Offset,
    dragAmount: Offset,
    size: Size,
    maxWidthPx: Float,
) = when {
    topLeft.x + dragAmount.x < 0 -> 0f
    topLeft.x + size.width + dragAmount.x > maxWidthPx -> maxWidthPx - size.width
    else -> topLeft.x + dragAmount.x
}

internal fun boundedNewXCircular(
    center: Offset,
    dragAmount: Offset,
    radius: Float,
    maxWidthPx: Float,
) = when {
    center.x + dragAmount.x - radius < 0 -> radius
    center.x + radius + dragAmount.x > maxWidthPx -> maxWidthPx - radius
    else -> center.x + dragAmount.x
}

internal fun boundedNewY(
    topLeft: Offset,
    dragAmount: Offset,
    size: Size,
    maxHeightPx: Float,
) = when {
    topLeft.y + dragAmount.y < 0 -> 0f
    topLeft.y + size.height + dragAmount.y > maxHeightPx -> maxHeightPx - size.height
    else -> topLeft.y + dragAmount.y
}

internal fun boundedNewYCircular(
    center: Offset,
    dragAmount: Offset,
    radius: Float,
    maxHeightPx: Float,
) = when {
    center.y + dragAmount.y - radius < 0 -> radius
    center.y + radius + dragAmount.y > maxHeightPx -> maxHeightPx - radius
    else -> center.y + dragAmount.y
}

internal fun boundedWidth(
    newWidth: Float,
    maxWidthPx: Float,
) = when {
    newWidth < 0 -> 0f
    newWidth > maxWidthPx -> maxWidthPx
    else -> newWidth
}

internal fun boundedHeight(
    newHeight: Float,
    maxHeightPx: Float,
) = when {
    newHeight < 0 -> 0f
    newHeight > maxHeightPx -> maxHeightPx
    else -> newHeight
}

// bounded radius
internal fun boundedRadius(
    center: Offset,
    radius: Float,
    maxWidthPx: Float,
    maxHeightPx: Float,
    dragAmount: Offset,
    minWidth: Float,
): Float {
    val newRadius = radius + dragAmount.x
    return when {
        newRadius + center.x > maxWidthPx -> maxWidthPx - center.x
        newRadius + center.y > maxHeightPx -> maxHeightPx - center.y
        newRadius < minWidth -> minWidth
        center.y - newRadius < 0 -> center.y
        center.x - newRadius < 0 -> center.x
        else -> newRadius
    }
}

internal fun isWidthBiggerThanAllowedWidth(
    targetWidth: Float,
    allowedWidth: Float,
    leftX: Float,
): Boolean {
    return targetWidth + leftX > allowedWidth
}

internal fun isWidthLessThanMinWidth(
    targetWidth: Float,
    minWidth: Float,
): Boolean {
    return targetWidth < minWidth
}

internal fun isHeightBiggerThanAllowedHeight(
    targetHeight: Float,
    allowedHeight: Float,
    topY: Float,
): Boolean {
    return targetHeight + topY > allowedHeight
}

internal fun isHeightLessThanMinHeight(
    targetHeight: Float,
    minHeight: Float,
): Boolean {
    return targetHeight < minHeight
}


@Composable
fun Dp.toPx(): Float {
    val density = Density(LocalContext.current)
    return with(density) { toPx() }
}