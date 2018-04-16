package com.markcadag.movieviewer.ui.custom.seatview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.ImageButton


/**
 * Created by markcadag on 4/8/18.
 */
class SeatNameView : ImageButton {
    internal var mText = ""
    internal var mTextPaint: Paint = Paint()

    internal var mViewWidth: Int = 0
    internal var mViewHeight: Int = 0
    internal var mTextBaseline: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        parseAttrs(attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        parseAttrs(attrs)
        init()
    }

    /**
     * Dig out Attributes to find text setting
     *
     * This could be expanded to pull out settings for textColor, etc if desired
     *
     * @param attrs
     */

    private fun parseAttrs(attrs: AttributeSet) {
        for (i in 0 until attrs.getAttributeCount()) {
            val s = attrs.getAttributeName(i)
            if (s.equals("text", ignoreCase = true)) {
                mText = attrs.getAttributeValue(i)
            }
        }
    }

    fun setText(text: CharSequence) {
        mText = text.toString()
        onSizeChanged(width, height, width, height)
    }

    /**
     * initialize Paint for text, it will be modified when the view size is set
     */
    private fun init() {

        mTextPaint = TextPaint()
        mTextPaint.setTextAlign(Paint.Align.CENTER)
        mTextPaint.setAntiAlias(true)
    }

    /**
     * set the scale of the text Paint objects so that the text will draw and
     * take up the full screen width
     */
    internal fun adjustTextScale() {
        // do calculation with scale of 1.0 (no scale)
        mTextPaint.setTextScaleX(1.0f)
        val bounds = Rect()
        // ask the paint for the bounding rect if it were to draw this
        // text.
        mTextPaint.getTextBounds(mText, 0, mText.length, bounds)

        // determine the width
        val w = bounds.right - bounds.left

        // calculate the baseline to use so that the
        // entire text is visible including the descenders
        val text_h = bounds.bottom - bounds.top
        mTextBaseline = bounds.bottom + (mViewHeight - text_h) / 2

        // determine how much to scale the width to fit the view
        val xscale = (mViewWidth - paddingLeft - paddingRight).toFloat() / w

        // set the scale for the text paint
        mTextPaint.setTextScaleX(xscale)
    }

    /**
     * determine the proper text size to use to fill the full height
     */
    internal fun adjustTextSize() {
        // using .isEmpty() isn't backward compatible with older API versions
        if (mText.length == 0) {
            return
        }
        mTextPaint.setTextSize(100f)
        mTextPaint.setTextScaleX(1.0f)
        val bounds = Rect()
        // ask the paint for the bounding rect if it were to draw this
        // text
        mTextPaint.getTextBounds(mText, 0, mText.length, bounds)

        // get the height that would have been produced
        val h = bounds.bottom - bounds.top

        // make the text text up 70% of the height
        val target = mViewHeight.toFloat() * .7f

        // figure out what textSize setting would create that height
        // of text
        val size = target / h * 100f

        // and set it into the paint
        mTextPaint.setTextSize(size)
    }

    /**
     * When the view size is changed, recalculate the paint settings to have the
     * text on the fill the view area
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // save view size
        mViewWidth = w
        mViewHeight = h

        // first determine font point size
        adjustTextSize()
        // then determine width scaling
        // this is done in two steps in case the
        // point size change affects the width boundary
        adjustTextScale()
        // we have changed this view, now we need to redraw
        // Note: redraw is not automatic if you are sending button clicks to this object
        // programmatically.
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        // let the ImageButton paint background as normal
        super.onDraw(canvas)

        // draw the text
        // position is centered on width
        // and the baseline is calculated to be positioned from the
        // view bottom
        setBackgroundResource(android.R.color.transparent)
        canvas.drawText(mText, mViewWidth.toFloat() / 2, mViewHeight.toFloat() - mTextBaseline,
                mTextPaint)

    }
}

