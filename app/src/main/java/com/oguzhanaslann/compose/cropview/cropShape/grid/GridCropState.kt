package com.oguzhanaslann.compose.cropview.cropShape.grid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.unit.dp
import com.oguzhanaslann.compose.cropview.cropShape.cropState.RectangleCropShapeState
import com.oguzhanaslann.compose.cropview.isBottomLeftCorner
import com.oguzhanaslann.compose.cropview.isBottomRightCorner
import com.oguzhanaslann.compose.cropview.isLeftEdge
import com.oguzhanaslann.compose.cropview.isMiddle
import com.oguzhanaslann.compose.cropview.isRightEdge
import com.oguzhanaslann.compose.cropview.isTopLeftCorner
import com.oguzhanaslann.compose.cropview.isTopRightCorner
import com.oguzhanaslann.compose.cropview.util.boundedHeight
import com.oguzhanaslann.compose.cropview.util.boundedNewX
import com.oguzhanaslann.compose.cropview.util.boundedNewY
import com.oguzhanaslann.compose.cropview.util.boundedWidth
import com.oguzhanaslann.compose.cropview.util.isHeightBiggerThanAllowedHeight
import com.oguzhanaslann.compose.cropview.util.isHeightLessThanMinHeight
import com.oguzhanaslann.compose.cropview.util.isWidthBiggerThanAllowedWidth
import com.oguzhanaslann.compose.cropview.util.isWidthLessThanMinWidth
import com.oguzhanaslann.compose.cropview.util.toPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GridCropState(
    topLeft: Offset = Offset.Zero,
    size: Size = Size.Zero,
    coroutineScope: CoroutineScope,
    val minSize: Size,
) : RectangleCropShapeState {
    private val _topLeft = mutableStateOf(topLeft)
    override val topLeft by _topLeft

    private val _size = mutableStateOf(size)
    override var size: Size by _size

    private var gridAllowedArea by mutableStateOf(size)

    init {
        gridAllowedArea = size

        coroutineScope.launch {
            snapshotFlow { gridAllowedArea }
                .collectLatest(::updateTopAndSizeByAllowedAreaIfNeeded)
        }
    }

    override fun resize(
        change: PointerInputChange,
        dragAmount: Offset,
        maxSize: Size,
    ): PointerInputScope.() -> Unit = {

        val xDp = change.position.x.toDp()
        val yDp = change.position.y.toDp()
        val maxWidthPx = maxSize.width
        val maxHeightPx = maxSize.height
        when {
            isTopLeftCorner(xDp, yDp, this@GridCropState.topLeft) -> {
                setTopLeft(
                    x = boundedNewX(
                        topLeft = this@GridCropState.topLeft,
                        dragAmount = dragAmount,
                        size = this@GridCropState.size,
                        maxWidthPx = maxWidthPx
                    ),
                    y = boundedNewY(
                        topLeft = this@GridCropState.topLeft,
                        dragAmount = dragAmount,
                        size = this@GridCropState.size,
                        maxHeightPx = maxHeightPx
                    )
                )

                setSize(
                    width = boundedWidth(
                        this@GridCropState.size.width - dragAmount.x,
                        maxWidthPx
                    ),
                    height = boundedHeight(
                        this@GridCropState.size.height - dragAmount.y,
                        maxHeightPx
                    )
                )
            }

            isTopRightCorner(xDp, yDp, this@GridCropState.topLeft, this@GridCropState.size) -> {
                setTopLeft(
                    x = this@GridCropState.topLeft.x,
                    y = boundedNewY(
                        topLeft = this@GridCropState.topLeft,
                        dragAmount = dragAmount,
                        size = this@GridCropState.size,
                        maxHeightPx = maxHeightPx
                    )
                )

                setSize(
                    width = boundedWidth(
                        this@GridCropState.size.width + dragAmount.x,
                        maxWidthPx
                    ),
                    height = boundedHeight(
                        this@GridCropState.size.height - dragAmount.y,
                        maxHeightPx
                    )
                )
            }

            isBottomLeftCorner(xDp, yDp, this@GridCropState.topLeft, this@GridCropState.size) -> {
                setTopLeft(
                    x = boundedNewX(
                        topLeft = this@GridCropState.topLeft,
                        dragAmount = dragAmount,
                        size = this@GridCropState.size,
                        maxWidthPx = maxWidthPx
                    ),
                    y = this@GridCropState.topLeft.y
                )

                setSize(
                    width = boundedWidth(
                        this@GridCropState.size.width - dragAmount.x,
                        maxWidthPx
                    ),
                    height = boundedHeight(
                        this@GridCropState.size.height + dragAmount.y,
                        maxHeightPx
                    )
                )
            }

            isBottomRightCorner(
                xDp,
                yDp,
                this@GridCropState.topLeft,
                this@GridCropState.size
            ) -> {
                setSize(
                    width = boundedWidth(
                        this@GridCropState.size.width + dragAmount.x,
                        maxWidthPx
                    ),
                    height = boundedHeight(
                        this@GridCropState.size.height + dragAmount.y,
                        maxHeightPx
                    )
                )
            }

            isMiddle(xDp, yDp, this@GridCropState.topLeft, this@GridCropState.size) -> {
                val newX = boundedNewX(
                    this@GridCropState.topLeft,
                    dragAmount,
                    this@GridCropState.size,
                    maxWidthPx
                )
                val newY = boundedNewY(
                    this@GridCropState.topLeft,
                    dragAmount,
                    this@GridCropState.size,
                    maxHeightPx
                )

                setTopLeft(
                    x = newX,
                    y = newY
                )
            }

            isLeftEdge(xDp, yDp, this@GridCropState.topLeft, this@GridCropState.size) -> {
                setTopLeft(
                    x = boundedNewX(
                        topLeft = this@GridCropState.topLeft,
                        dragAmount = dragAmount,
                        size = this@GridCropState.size,
                        maxWidthPx = maxWidthPx
                    ),
                    y = this@GridCropState.topLeft.y
                )

                setSize(
                    width = boundedWidth(
                        this@GridCropState.size.width - dragAmount.x,
                        maxWidthPx
                    ),
                    height = this@GridCropState.size.height
                )
            }

            isRightEdge(xDp, yDp, this@GridCropState.topLeft, this@GridCropState.size) -> {
                setSize(
                    width = boundedWidth(
                        this@GridCropState.size.width + dragAmount.x,
                        maxWidthPx
                    ),
                    height = this@GridCropState.size.height
                )
            }

            else -> Unit
        }
    }


    private fun updateTopAndSizeByAllowedAreaIfNeeded(
        allowedArea: Size,
    ) {
        val allowedWidth = allowedArea.width
        val allowedHeight = allowedArea.height
        _topLeft.value = topLeftInAllowedArea(this.topLeft, allowedWidth, allowedHeight)
        _size.value = sizeInLimits(this.size, allowedWidth, allowedHeight)
    }

    private fun topLeftInAllowedArea(
        topLeft: Offset,
        allowedWidth: Float,
        allowedHeight: Float,
    ): Offset {
        var currentTopLeft = topLeft

        if (currentTopLeft.x < 0) {
            currentTopLeft = Offset(0f, currentTopLeft.y)
        }

        if (currentTopLeft.y < 0) {
            currentTopLeft = Offset(currentTopLeft.x, 0f)
        }

        if (currentTopLeft.x + this.size.width > allowedWidth) {
            currentTopLeft = Offset(allowedWidth - this.size.width, currentTopLeft.y)
        }

        if (currentTopLeft.y + this.size.height > allowedHeight) {
            currentTopLeft = Offset(currentTopLeft.x, allowedHeight - this.size.height)
        }

        return currentTopLeft
    }

    private fun sizeInLimits(
        size: Size,
        allowedWidth: Float,
        allowedHeight: Float,
    ): Size {
        var currentSize = size

        if (currentSize.width > allowedWidth) {
            currentSize = Size(allowedWidth, currentSize.height)
        }

        if (currentSize.height > allowedHeight) {
            currentSize = Size(currentSize.width, allowedHeight)
        }

        if (currentSize.width < minSize.width) {
            currentSize = Size(minSize.width, currentSize.height)
        }

        if (currentSize.height < minSize.height) {
            currentSize = Size(currentSize.width, minSize.height)
        }

        return currentSize
    }


    private fun setTopLeft(x: Float, y: Float) {
        val newTopLeft =
            topLeftInAllowedArea(
                topLeft = Offset(x, y),
                allowedWidth = gridAllowedArea.width,
                allowedHeight = gridAllowedArea.height
            )
        _topLeft.value = newTopLeft
    }

    override fun setSize(width: Float, height: Float) {
        val newSize = sizeInLimits(
            size = Size(width, height),
            allowedWidth = gridAllowedArea.width,
            allowedHeight = gridAllowedArea.height
        )
        _size.value = newSize
    }

    internal fun setGridAllowedArea(width: Float, height: Float) {
        gridAllowedArea = Size(width, height)
    }

    fun setAspectRatio(ratio: Ratio) {
        val currentRatio = this.size.width / this.size.height
        val targetRatio = ratio.value
        if (currentRatio == targetRatio) {
            return
        }

        var appliedWidth = this.size.width
        var appliedHeight: Float

        do {
            val nextHeight = appliedWidth / targetRatio

            val heightBiggerThanAllowedHeight = isHeightBiggerThanAllowedHeight(
                targetHeight = nextHeight,
                allowedHeight = gridAllowedArea.height,
                topY = this.topLeft.y
            )
            val heightLessThanMinHeight = isHeightLessThanMinHeight(
                targetHeight = nextHeight,
                minHeight = minSize.height
            )
            val applyNewHeight = !heightBiggerThanAllowedHeight && !heightLessThanMinHeight
            appliedHeight = when {
                heightBiggerThanAllowedHeight -> gridAllowedArea.height - this.topLeft.y
                heightLessThanMinHeight -> minSize.height
                else -> nextHeight
            }

            if (applyNewHeight) {
                break
            }

            appliedWidth = appliedHeight * targetRatio

            val widthBiggerThanAllowedWidth = isWidthBiggerThanAllowedWidth(
                targetWidth = appliedWidth,
                allowedWidth = gridAllowedArea.width,
                leftX = this.topLeft.x
            )
            val widthLessThanMinWidth = isWidthLessThanMinWidth(
                targetWidth = appliedWidth,
                minWidth = minSize.width
            )
            val applyNewWidth = !widthBiggerThanAllowedWidth && !widthLessThanMinWidth

            if (applyNewWidth) {
                break
            }

            appliedWidth = when {
                widthBiggerThanAllowedWidth -> gridAllowedArea.width - this.topLeft.x
                else -> minSize.width
            }

        } while (true);

        _size.value = Size(appliedWidth, appliedHeight)
    }
}

@Composable
fun rememberGridCropState(
    size: Size = Size.Zero,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    minSize: Size = Size(100.dp.toPx(), 100.dp.toPx()),
) = remember(size) {
    GridCropState(
        topLeft = Offset.Zero,
        size = size,
        coroutineScope = coroutineScope,
        minSize = minSize
    )
}
