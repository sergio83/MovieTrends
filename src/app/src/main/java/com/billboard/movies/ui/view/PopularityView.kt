package com.billboard.movies.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.graphics.ColorUtils
import com.billboard.movies.R
import com.billboard.movies.utils.extensions.px
import java.lang.Math.ceil

class PopularityView : View {

    var stroke = 5f.px
    var padding = 3f.px
    var lowPopularitycolor = Color.RED
    var mediumPopularityColor = Color.YELLOW
    var hightPopularityColor = Color.GREEN
    var innerBackgroundColor = Color.BLACK
    var textSize = spToPx(13f, context).toFloat()
    var textColor = Color.WHITE
    var popularityColor = Color.RED
    var percentage = 0
        set(value) {
            field = value
            popularityColor = when (percentage) {
                in 0..30 -> lowPopularitycolor
                in 30..69 -> mediumPopularityColor
                else -> hightPopularityColor
            }
        }
    private var arc = RectF()

    private val mBackgroundPaint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.DITHER_FLAG or Paint.ANTI_ALIAS_FLAG)

    private val mPaint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.DITHER_FLAG or Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PopularityView, 0, 0
        ).apply {

            try {
                percentage = getInt(R.styleable.PopularityView_percentage, 100)
                stroke = getDimension(R.styleable.PopularityView_stroke, stroke)
                padding = getDimension(R.styleable.PopularityView_padding, padding)
                lowPopularitycolor = getColor(R.styleable.PopularityView_lowPopularitycolor, lowPopularitycolor)
                mediumPopularityColor =
                        getColor(R.styleable.PopularityView_mediumPopularityColor, mediumPopularityColor)
                hightPopularityColor = getColor(R.styleable.PopularityView_hightPopularityColor, hightPopularityColor)
                innerBackgroundColor = getColor(R.styleable.PopularityView_innerBackgroundColor, innerBackgroundColor)
                textSize = getDimension(R.styleable.PopularityView_textSize, textSize)
                textColor = getColor(R.styleable.PopularityView_textColor, textColor)

            } finally {
                recycle()
            }
        }
    }


    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        mPaint.strokeWidth = stroke

        arc.apply {
            set(
                (stroke / 2 + padding),
                (stroke / 2 + padding),
                (width - padding - stroke / 2),
                (height - padding - stroke / 2)
            )
        }

        //draw background
        mBackgroundPaint.color = innerBackgroundColor
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, mBackgroundPaint)

        //First draw full arc as background.
        mPaint.color = ColorUtils.setAlphaComponent(popularityColor, 75)
        canvas.drawArc(arc, -90f, 360f, false, mPaint)

        //Then draw arc progress with actual value.
        mPaint.color = popularityColor
        canvas.drawArc(arc, -90f, percentage.toFloat() * 3.6f, false, mPaint)

        //Draw text value.
        mTextPaint.textSize = when (percentage) {
            in 0..99 -> textSize
            else -> ceil((textSize * 0.8f).toDouble()).toFloat()
        }
        mTextPaint.color = textColor
        canvas.drawText(
            percentage.toString() + "%",
            width / 2f,
            (height - mTextPaint.ascent()) / 2,
            mTextPaint
        )

    }

    private fun spToPx(sp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
    }
}