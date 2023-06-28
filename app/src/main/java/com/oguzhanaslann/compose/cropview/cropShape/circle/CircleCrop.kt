package com.oguzhanaslann.compose.cropview.cropShape.circle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.oguzhanaslann.compose.cropview.cropShape.CropShape
import com.oguzhanaslann.compose.cropview.cropShape.cropState.CropState

class CircleCrop(
    private val circleCropState: CircularCropState,
    private val lineWidth: Dp = 2.dp,
    private val dotRadius: Dp = 3.dp,
) : CropShape {
    override val state: CropState
        get() = circleCropState

    @Composable
    override fun content(maxWidthPx: Float, maxHeightPx: Float) {
        val maxSize = Size(
            width = maxWidthPx,
            height = maxHeightPx
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        circleCropState
                            .resize(change, dragAmount, maxSize)
                            .invoke(this)
                    }
                },
            onDraw = {
                drawCircle(
                    center = circleCropState.center,
                    radius = circleCropState.radius,
                    color = Color.White,
                    style = Stroke(width = lineWidth.toPx())
                )

                // dot in the center
                drawCircle(
                    center = circleCropState.center,
                    radius = dotRadius.toPx(),
                    color = Color.White,
                )
            }
        )
    }
}

@Composable
fun rememberCircularCrop(
    circleCropState: CircularCropState = rememberCircularCropState(),
    lineWidth: Dp = 2.dp,
    dotRadius: Dp = 3.dp,
) = remember(circleCropState) {
    CircleCrop(
        circleCropState = circleCropState,
        lineWidth = lineWidth,
        dotRadius = dotRadius
    )
}