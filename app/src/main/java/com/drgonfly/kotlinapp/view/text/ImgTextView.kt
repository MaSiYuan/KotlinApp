package com.drgonfly.kotlinapp.view.text

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import com.drgonfly.kotlinapp.R
import com.drgonfly.kotlinapp.util.Utils

class ImgTextView(ctx: Context, attr: AttributeSet) : FrameLayout(ctx, attr) {

    val TAG = ImgTextView::class.java.simpleName

    val MARGIN = Utils.dp2px(20f)

    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var bitmap: Bitmap? = null
    var text: String = "尽管Activity是系统提供的您的应用界面的入口点，但在相互分享数据以及转场方面，Activity表现得不够灵活，这就让它不适合作为构建您的应用内导航的理想架构。今天，我们宣布推出导航组件，作为构建您的应用内界面的框架，重点是让单 Activity 应用成为首选架构。利用导航组件对 Fragment 的原生支持，您可以获得架构组件的所有好处（例如生命周期和 ViewModel），同时让此组件为您处理 FragmentTransaction 的复杂性。此外，导航组件还可以让您声明我们为您处理的转场。它可以自动构建正确的“向上”和“返回”行为，包含对深层链接的完整支持，并提供了帮助程序，用于将导航关联到合适的 UI 小部件，例如抽屉式导航栏和底部导航。但这些并不是全部！"
    var textSize: Float = 80f

    var imgWidth = 300
    var imgHeight = 300

    val fontMetrics: Paint.FontMetrics = Paint.FontMetrics()

    var measuredWidth = FloatArray(1)

    init {
        val iv = ImageView(ctx)
        val lp: FrameLayout.LayoutParams = FrameLayout.LayoutParams(imgWidth, imgHeight, Gravity.TOP)
        lp.topMargin = MARGIN.toInt()
        lp.leftMargin = MARGIN.toInt()
        iv.layoutParams = lp
        addView(iv)

        iv.setImageResource(R.drawable.ic_launcher_background)

        paint.textSize = textSize
        paint.getFontMetrics(fontMetrics)

        Log.i(TAG, "fontMetrics.top = " + fontMetrics.top)
        Log.i(TAG, "fontMetrics.bottom = " + fontMetrics.bottom)
        Log.i(TAG, "fontMetrics.ascent = " + fontMetrics.ascent)
        Log.i(TAG, "fontMetrics.descent = " + fontMetrics.descent)
        Log.i(TAG, "fontMetrics.leading = " + fontMetrics.leading)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var startIndex = 0
        val textLength = text.length

        var drawWidth: Float

        var x: Float
        var y = -fontMetrics.ascent + MARGIN

        while (startIndex < textLength) {
            if (y > MARGIN && y + fontMetrics.ascent < imgHeight + MARGIN) {
                x = imgWidth.toFloat() + MARGIN
                drawWidth = width.toFloat() - MARGIN * 2 - imgWidth
            } else {
                x = MARGIN
                drawWidth = width.toFloat() - MARGIN * 2
            }

            val textCount = paint.breakText(text, true, drawWidth, measuredWidth)
            val endIndex = Math.min(startIndex + textCount, textLength)

            Log.e(TAG, "startIndex = $startIndex endIndex = $endIndex textCount = $textCount drawWidth = $drawWidth")

            canvas?.drawText(text, startIndex, endIndex, x, y, paint)

            startIndex += textCount
            y += paint.fontSpacing
        }
    }
}