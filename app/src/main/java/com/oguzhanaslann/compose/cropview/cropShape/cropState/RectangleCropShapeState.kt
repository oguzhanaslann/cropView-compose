package com.oguzhanaslann.compose.cropview.cropShape.cropState

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import com.oguzhanaslann.compose.cropview.util.cropped

interface RectangleCropShapeState : CropState {
    val topLeft: Offset

    override fun crop(bitmap: Bitmap): Bitmap {
        return bitmap.cropped(
            topLeftX = topLeft.x,
            topLeftY = topLeft.y,
            width = size.width,
            height = size.height
        )
    }
}