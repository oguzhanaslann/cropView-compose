package com.oguzhanaslann.compose.cropview.cropShape.cropState

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope

interface CropState {
    val size: Size

    fun resize(
        change: PointerInputChange,
        dragAmount: Offset,
        maxSize: Size,
    ): PointerInputScope.() -> Unit

    fun crop(bitmap: Bitmap): Bitmap

    fun setSize(width: Float, height: Float)

}