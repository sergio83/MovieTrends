package com.billboard.movies.utils

import android.content.Context
import android.util.TypedValue


class Utils {

    companion object {
        fun spToPx(sp: Float, context: Context): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
        }

    }

}