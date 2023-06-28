package com.oguzhanaslann.compose.cropview.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.compose.ui.graphics.ColorMatrix


fun Bitmap.rotate(angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.cropped(topLeftX: Float, topLeftY: Float, width: Float, height: Float): Bitmap {
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

fun Bitmap.blur(radius: Float): Bitmap {
    val width = width
    val height = height
    val pixels = IntArray(width * height)
    this.getPixels(pixels, 0, width, 0, 0, width, height)
    var radius = radius.toInt()
    while (radius >= 1) {
        for (i in radius until (height - radius)) {
            for (j in radius until width - radius) {
                val tl = pixels[(i - radius) * width + j - radius]
                val tr = pixels[(i - radius) * width + j + radius]
                val tc = pixels[(i - radius) * width + j]
                val bl = pixels[(i + radius) * width + j - radius]
                val br = pixels[(i + radius) * width + j + radius]
                val bc = pixels[(i + radius) * width + j]
                val cl = pixels[i * width + j - radius]
                val cr = pixels[i * width + j + radius]
                pixels[i * width + j] = -0x1000000 or (
                        (tl and 0xFF) + (tr and 0xFF) + (tc and 0xFF) + (bl and 0xFF) +
                                (br and 0xFF) + (bc and 0xFF) + (cl and 0xFF) + (cr and 0xFF) shr 3 and 0xFF) or (
                        ((tl and 0xFF00) + (tr and 0xFF00) + (tc and 0xFF00) + (bl and 0xFF00)
                                + (br and 0xFF00) + (bc and 0xFF00) + (cl and 0xFF00) + (cr and 0xFF00)) shr 3 and 0xFF00) or (
                        (tl and 0xFF0000) + (tr and 0xFF0000) + (tc and 0xFF0000) +
                                (bl and 0xFF0000) + (br and 0xFF0000) + (bc and 0xFF0000) + (cl and 0xFF0000) +
                                (cr and 0xFF0000) shr 3 and 0xFF0000)
            }
        }
        radius /= 2
    }
    val blurred = Bitmap.createBitmap(width, height, config)
    blurred.setPixels(pixels, 0, width, 0, 0, width, height)
    return blurred
}

fun Bitmap.filtered(
    colorMatrix: ColorMatrix,
    brightness: Float?,
    contrast: Float?,
): Bitmap {

    val targetBmp: Bitmap = copy(Bitmap.Config.ARGB_8888, true)

    // Create a Canvas object to draw the modified image
    val canvas = Canvas(targetBmp)

    // Create a Paint object with ColorFilter to apply the color matrix
    val matrix = colorMatrix.values.clone()
    brightness?.let {
        matrix[4] = matrix[4] + it
        matrix[9] = matrix[9] + it
        matrix[14] = matrix[14] + it
    }

    contrast?.let {
        val scale = it + 1f
        matrix[0] = matrix[0] * scale
        matrix[6] = matrix[6] * scale
        matrix[12] = matrix[12] * scale
        matrix[18] = matrix[18] * scale
    }

    val updatedMatrix = ColorMatrix(matrix)
    val paint = Paint().apply {
        colorFilter = ColorMatrixColorFilter(updatedMatrix.values)
    }

    canvas.drawBitmap(targetBmp, 0f, 0f, paint)

    return targetBmp
}

fun Bitmap.circularCropped(
    centerX: Float,
    centerY: Float,
    radius: Float,
): Bitmap {
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