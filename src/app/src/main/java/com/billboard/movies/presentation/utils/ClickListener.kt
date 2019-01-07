package com.billboard.movies.presentation.utils

import android.view.View

interface ClickListener {
    fun onClick(view: View, position: Int)
    fun onLongClick(view: View, position: Int, xx: Float, yy: Float)
}
