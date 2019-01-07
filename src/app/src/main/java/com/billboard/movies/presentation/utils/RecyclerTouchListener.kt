package com.billboard.movies.presentation.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView


class RecyclerTouchListener(val context: Context, val recycleView: RecyclerView, val clicklistener: ClickListener) :
    RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {

                val child = recycleView.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child), e.x, e.y)
                }
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        rv.findChildViewUnder(e.x, e.y)?.apply {
            val position = rv.getChildAdapterPosition(this)
            if (position >= 0 && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(this, position)
            }
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}