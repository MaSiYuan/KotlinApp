package com.drgonfly.kotlinapp.view.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

class ShapeImage(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}