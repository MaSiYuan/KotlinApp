package com.drgonfly.kotlinapp.view.text

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import com.drgonfly.kotlinapp.R

class ImgTextView(ctx: Context, attr: AttributeSet) : FrameLayout(ctx, attr) {

    companion object {
        val TAG: String = ImgTextView::class.java.simpleName
    }

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val fontMetrics: Paint.FontMetrics = Paint.FontMetrics()
    private val measuredWidth = FloatArray(1)
    private val imgView: ImageView = ImageView(ctx)

    var paddingWithText:Float = 0f
    var text: String = "Here is the content."

    var imgWidth:Float = 0f
    var imgHeight:Float = 0f

    private var imgEngine: ImgEngine? = null

    init {
        val array: TypedArray = context.theme.obtainStyledAttributes(attr, R.styleable.ImgTextView, 0, 0)
        imgWidth = array.getDimension(R.styleable.ImgTextView_img_width, 0f)
        imgHeight = array.getDimension(R.styleable.ImgTextView_img_height, 0f)
        paddingWithText = array.getDimension(R.styleable.ImgTextView_padding_with_text, 0f)
        val textSize = array.getDimension(R.styleable.ImgTextView_text_size, 0f)
        val textColor = array.getColor(R.styleable.ImgTextView_text_color, Color.DKGRAY)
        array.recycle()

        imgView.layoutParams = FrameLayout.LayoutParams(imgWidth.toInt(), imgHeight.toInt(), Gravity.TOP)

        imgView.setBackgroundColor(Color.LTGRAY)
        addView(imgView)

        paint.textSize = textSize
        paint.color = textColor
        paint.getFontMetrics(fontMetrics)
    }

    override fun onDraw(canvas: Canvas?) {

        var startIndex = 0
        val textLength = text.length

        var drawWidth: Float

        var x: Float

        var bottom = -fontMetrics.top + paddingTop
        var top = paddingTop

        while (startIndex < textLength) {
            if (bottom > paddingTop && top < imgHeight + paddingTop) {
                x = imgWidth + paddingLeft + paddingWithText
                drawWidth = width.toFloat() - paddingLeft - paddingRight - paddingWithText - imgWidth
            } else {
                x = paddingLeft.toFloat()
                drawWidth = width.toFloat() - paddingLeft - paddingRight
            }

            val textCount = paint.breakText(text, startIndex, textLength, true, drawWidth, measuredWidth)
            if (height - bottom < 1.5 * paint.fontSpacing) {
                val lastLineText: String = if (textCount > 3) {
                    text.substring(startIndex, startIndex + textCount - 3) + "..."
                } else {
                    text.substring(startIndex) + "..."
                }
                canvas?.drawText(lastLineText, x, bottom, paint)
                break
            } else {
                canvas?.drawText(text, startIndex, startIndex + textCount, x, bottom, paint)
            }

            startIndex += textCount

            bottom += paint.fontSpacing
            top = (bottom + fontMetrics.top).toInt()
        }
    }

    fun showText(text: String) {
        this.text = text
        invalidate()
    }

    fun addImgEngine(imgEngine: ImgEngine) {
        this.imgEngine = imgEngine
        imgEngine.showImg(imgView)
    }

    interface ImgEngine {
        fun showImg(iv: ImageView)
    }
}