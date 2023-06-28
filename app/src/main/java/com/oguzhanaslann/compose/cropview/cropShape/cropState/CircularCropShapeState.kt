package com.oguzhanaslann.compose.cropview.cropShape.cropState

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import com.oguzhanaslann.compose.cropview.util.circularCropped

interface CircularCropShapeState : CropState {
    val center: Offset
    val radius: Float

    override fun crop(bitmap: Bitmap): Bitmap {
        return bitmap.circularCropped(
            centerX = center.x,
            centerY = center.y,
            radius = radius
        )
    }
}