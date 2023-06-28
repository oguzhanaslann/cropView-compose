package com.oguzhanaslann.compose.cropview

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


val touchSlop = 48.dp

internal fun PointerInputScope.isTopLeftCorner(
    xDp: Dp,
    yDp: Dp,
    topLeft: Offset,
): Boolean {
    return xDp in topLeft.x.toDp() - touchSlop..topLeft.x.toDp() + touchSlop
            && yDp in topLeft.y.toDp() - touchSlop..topLeft.y.toDp() + touchSlop
}

internal fun PointerInputScope.isTopRightCorner(
    xDp: Dp,
    yDp: Dp,
    topLeft: Offset,
    size: Size,
): Boolean {
    return xDp in topLeft.x.toDp() + size.width.toDp() - touchSlop..topLeft.x.toDp() + size.width.toDp() + touchSlop
            && yDp in topLeft.y.toDp() - touchSlop..topLeft.y.toDp() + touchSlop
}

internal fun PointerInputScope.isBottomLeftCorner(
    xDp: Dp,
    yDp: Dp,
    topLeft: Offset,
    size: Size,
): Boolean {
    return xDp in topLeft.x.toDp() - touchSlop..topLeft.x.toDp() + touchSlop
            && yDp in topLeft.y.toDp() + size.height.toDp() - touchSlop..topLeft.y.toDp() + size.height.toDp() + touchSlop
}

internal fun PointerInputScope.isBottomRightCorner(
    xDp: Dp,
    yDp: Dp,
    topLeft: Offset,
    size: Size,
): Boolean {
    return xDp in topLeft.x.toDp() + size.width.toDp() - touchSlop..topLeft.x.toDp() + size.width.toDp() + touchSlop
            && yDp in topLeft.y.toDp() + size.height.toDp() - touchSlop..topLeft.y.toDp() + size.height.toDp() + touchSlop
}

internal fun PointerInputScope.isMiddle(
    xDp: Dp,
    yDp: Dp,
    topLeft: Offset,
    size: Size,
): Boolean {
    return xDp in topLeft.x.toDp() + touchSlop..topLeft.x.toDp() + size.width.toDp() - touchSlop
            && yDp in topLeft.y.toDp() + touchSlop..topLeft.y.toDp() + size.height.toDp() - touchSlop
}

internal fun PointerInputScope.isLeftEdge(
    xDp: Dp,
    yDp: Dp,
    topLeft: Offset,
    size: Size,
): Boolean {
    return xDp in topLeft.x.toDp() - touchSlop..topLeft.x.toDp() + touchSlop
            && yDp in topLeft.y.toDp() + touchSlop..topLeft.y.toDp() + size.height.toDp() - touchSlop
}

internal fun PointerInputScope.isRightEdge(
    xDp: Dp,
    yDp: Dp,
    topLeft: Offset,
    size: Size,
): Boolean {
    return xDp in topLeft.x.toDp() + size.width.toDp() - touchSlop..topLeft.x.toDp() + size.width.toDp() + touchSlop
            && yDp in topLeft.y.toDp() + touchSlop..topLeft.y.toDp() + size.height.toDp() - touchSlop
}

internal fun PointerInputScope.isCenter(
    position: Offset,
    center: Offset,
): Boolean {
    // if the distance between the center and the position is less than 48dp
    // then it is considered as the center
    return position distanceTo center < 48.dp.toPx()
}

internal infix fun Offset.distanceTo(other: Offset): Float {
    val dx = kotlin.math.abs(this.x - other.x)
    val dy = kotlin.math.abs(this.y - other.y)
    return kotlin.math.sqrt((dx * dx) + (dy * dy))
}

internal fun PointerInputScope.isInCircle(
    position: Offset,
    center: Offset,
    radius: Float,
): Boolean {
    return position distanceTo center < radius
}

