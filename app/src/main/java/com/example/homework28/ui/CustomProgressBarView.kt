package com.example.homework28.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import com.example.homework28.R

class CustomProgressBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = 0.0f
    private var colorOfDot = Color.BLACK
    private var colorOfCircle = Color.LTGRAY
    private val pointPosition: PointF = PointF(0.0f, 0.0f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomProgressBarView,
            0, 0
        ).apply {
            try {
                colorOfDot = getColor(R.styleable.CustomProgressBarView_color_of_dot, Color.BLACK)
                colorOfCircle =
                    getColor(R.styleable.CustomProgressBarView_color_of_circle, Color.LTGRAY)
            } finally {
                recycle()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (Integer.min(w, h) / 2.0 * 0.8).toFloat()
    }

    private fun PointF.computeXYForStart(radius: Float) {
        x = (width / 2).toFloat()
        y = height / 2 - radius
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        pointPosition.computeXYForStart(radius)
        paint.color = colorOfDot
        canvas?.drawCircle(pointPosition.x, pointPosition.y, radius / 8, paint)

        paint.color = colorOfCircle
        paint.style = Paint.Style.STROKE
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
    }
}