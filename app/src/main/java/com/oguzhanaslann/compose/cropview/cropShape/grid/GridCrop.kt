package com.oguzhanaslann.compose.cropview.cropShape.grid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import com.oguzhanaslann.compose.cropview.cropShape.CropShape
import com.oguzhanaslann.compose.cropview.cropShape.cropState.CropState

class GridCrop(private val gridCropState : GridCropState) : CropShape {
    override val state: CropState
        get() = gridCropState
    @Composable
    override fun content(maxWidthPx: Float, maxHeightPx: Float) {
        GridView(
            gridCropState,
            maxSize = Size(
                width = maxWidthPx,
                height = maxHeightPx
            ),
            onDrawGrid = {
                gridCropState.setGridAllowedArea(
                    width = size.width,
                    height = size.height,
                )
            }
        )
    }

    fun setAspectRatio(ratio34: Ratio) {
        gridCropState.setAspectRatio(ratio34)
    }
}

@Composable
fun rememberGridCrop(
    gridCropState: GridCropState = rememberGridCropState()
) = remember(gridCropState) {
    GridCrop(gridCropState)
}