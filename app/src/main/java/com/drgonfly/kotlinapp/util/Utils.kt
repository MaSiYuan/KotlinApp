package com.drgonfly.kotlinapp.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue


class Utils {

    companion object {
        fun dp2px(dp: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                    Resources.getSystem().displayMetrics)
        }

        fun getAvatar(width: Int, resId: Int, ctx: Context): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(ctx.resources, resId, options)
            options.inJustDecodeBounds = false
            options.inDensity = options.outWidth
            options.inTargetDensity = width
            return BitmapFactory.decodeResource(ctx.resources, resId, options)
        }
    }
}