package com.oguzhanaslann.compose.cropview.cropShape.grid

@JvmInline
value class Ratio private constructor(
    val value: Float,
) {
    companion object {
        val RATIO_16_9 = Ratio(16f / 9f)
        val RATIO_9_16 = Ratio(9f / 16f)
        val RATIO_4_3 = Ratio(4f / 3f)
        val RATIO_3_4 = Ratio(3f / 4f)
        val RATIO_1_1 = Ratio(1f / 1f)
    }
}