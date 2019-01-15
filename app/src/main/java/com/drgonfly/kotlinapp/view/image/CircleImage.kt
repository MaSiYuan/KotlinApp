package com.drgonfly.kotlinapp.view.image

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.drgonfly.kotlinapp.util.Utils

class CircleImage(ctx: Context, attr: AttributeSet) : AppCompatImageView(ctx, attr) {

    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val xfermode: Xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    val savedArea: RectF = RectF()

    var centerX = 0f
    var centerY = 0f
    var radius = 0f

    var bitmap: Bitmap? = null

    init {
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        bitmap = Utils.drawable2Bitmap(drawable!!)
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        bitmap = Utils.drawable2Bitmap(drawable!!)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = (width / 2).toFloat()
        centerY = (height / 2).toFloat()
        radius = (width / 2).toFloat()

        savedArea.set(0f, 0f, width.toFloat(), height.toFloat())
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)

        val saved: Int? = canvas?.saveLayer(savedArea, paint)

        //draw circle image
//        canvas?.drawCircle(centerX, centerY, radius, paint)

        //draw circle image border
//        val saved: Int? = canvas?.saveLayer(savedArea, paint)
//        canvas?.drawCircle(centerX, centerY, radius-10, paint)

        //draw round rect image
        canvas?.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(),
                Utils.dp2px(20f), Utils.dp2px(20f), paint)

        paint.xfermode = xfermode
        canvas?.drawBitmap(bitmap!!, 0f, 0f, paint)
        paint.xfermode = null
        canvas?.restoreToCount(saved!!)
    }
}