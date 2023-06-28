package com.oguzhanaslann.compose.cropview.cropShape.circle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import com.oguzhanaslann.compose.cropview.cropShape.cropState.CircularCropShapeState
import com.oguzhanaslann.compose.cropview.isCenter
import com.oguzhanaslann.compose.cropview.isInCircle
import com.oguzhanaslann.compose.cropview.util.boundedNewXCircular
import com.oguzhanaslann.compose.cropview.util.boundedNewYCircular
import com.oguzhanaslann.compose.cropview.util.boundedRadius

class CircularCropState(
    center: Offset,
    radius: Float,
    val minRadius: Float = 0f,
) : CircularCropShapeState {

    private val _center = mutableStateOf(center)
    override val center: Offset by _center

    private val _radius = mutableStateOf(radius)
    override val radius: Float by _radius

    override val size: Size
        get() = Size(width = radius * 2, height = radius * 2)

    override fun resize(
        change: PointerInputChange,
        dragAmount: Offset,
        maxSize: Size,
    ): PointerInputScope.() -> Unit = {
        val maxWidthPx = maxSize.width
        val maxHeightPx = maxSize.height
        when {
            isCenter(change.position, center) -> {
                val newX = boundedNewXCircular(
                    center = center,
                    dragAmount = dragAmount,
                    radius = radius,
                    maxWidthPx = maxWidthPx,
                )

                val newY = boundedNewYCircular(
                    center = center,
                    dragAmount = dragAmount,
                    radius = radius,
                    maxHeightPx = maxHeightPx,
                )

                _center.value = Offset(x = newX, y = newY)
            }

            isInCircle(change.position, center, radius) -> {
                val newRadius = radius + dragAmount.x
                if (newRadius in minRadius..maxWidthPx.coerceAtLeast(maxHeightPx)) {

                    _radius.value = boundedRadius(
                        center = center,
                        radius = newRadius,
                        maxWidthPx = maxWidthPx,
                        maxHeightPx = maxHeightPx,
                        dragAmount = dragAmount,
                        minWidth = minRadius,
                    )
                }
            }
        }

    }

    override fun setSize(width: Float, height: Float) {
        val minEdge = width.coerceAtMost(height)
        _radius.value = minEdge / 2
    }
}


@Composable
fun rememberCircularCropState(
    center: Offset = Offset.Zero,
    radius: Float = 0f,
) = remember {
    CircularCropState(
        center = center,
        radius = radius,
    )
}