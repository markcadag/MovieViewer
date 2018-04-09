package com.markcadag.movieviewer.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView


/**
 * Created by markcadag on 4/9/18.
 */
class ProportionalImageView : ImageView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = getDrawable()
        if (d != null) {
            val w = MeasureSpec.getSize(widthMeasureSpec)
            val h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth()
            setMeasuredDimension(w, h)
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}