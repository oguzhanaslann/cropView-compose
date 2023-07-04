package com.oguzhanaslann.compose.cropview.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect

fun Bitmap.cropped(topLeftX: Float, topLeftY: Float, width: Float, height: Float): Bitmap {
    require(topLeftX >= 0 && topLeftY >= 0 && width >= 0 && height >= 0) {
        "topLeftX, topLeftY, width and height must be positive"
    }

    var width = width
    if (topLeftX + width > this.width) {
        width = this.width - topLeftX
    }

    var height = height
    if (topLeftY + height > this.height) {
        height = this.height - topLeftY
    }

    return Bitmap.createBitmap(
        this,
        topLeftX.toInt(),
        topLeftY.toInt(),
        width.toInt(),
        height.toInt()
    )
}


fun Bitmap.circularCropped(
    centerX: Float,
    centerY: Float,
    radius: Float,
): Bitmap {
    require(centerX >= 0 && centerY >= 0 && radius >= 0) {
        "centerX, centerY and radius must be positive"
    }
    val bitmap = this.copy(Bitmap.Config.ARGB_8888, true)
    val output = Bitmap.createBitmap(
        bitmap.width,
        bitmap.height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(output)
    val color = -0xbdbdbe
    val paint = Paint()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawCircle(
        centerX,
        centerY,
        radius,
        paint
    )
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)
    return output

}