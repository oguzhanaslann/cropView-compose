package com.oguzhanaslann.compose.cropview.cropShape

import androidx.compose.runtime.Composable
import com.oguzhanaslann.compose.cropview.cropShape.cropState.CropState

interface CropShape {
    val state: CropState

    @Composable
    fun content(maxWidthPx: Float, maxHeightPx: Float)
}