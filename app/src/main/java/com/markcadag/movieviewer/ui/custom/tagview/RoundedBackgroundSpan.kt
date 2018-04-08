package com.markcadag.movieviewer.ui.custom.tagview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import com.markcadag.movieviewer.R


/**
 * Created by markcadag on 4/8/18.
 */
class RoundedBackgroundSpan(val context: Context) : ReplacementSpan() {
    private var backgroundColor = 0
    private var textColor = 0

    init {
        context.let {
            backgroundColor = it.getResources().getColor(R.color.movieViewerRed)
            textColor = it.getResources().getColor(android.R.color.white)
        }

    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val rect = RectF(x, top.toFloat(), x + measureText(paint, text, start, end), bottom.toFloat())
        paint.setColor(backgroundColor)
        canvas.drawRoundRect(rect, context.resources.getDimension(R.dimen._2sdp), context.resources.getDimension(R.dimen._2sdp), paint)
        paint.setColor(textColor)
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return Math.round(paint.measureText(text, start, end))
    }

    private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float {
        return paint.measureText(text, start, end)
    }
}