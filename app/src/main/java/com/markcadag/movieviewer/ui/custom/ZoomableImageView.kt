//package com.markcadag.movieviewer.ui.custom
//
//import android.view.animation.DecelerateInterpolator
//import android.animation.ValueAnimator
//import android.view.MotionEvent
//import android.view.GestureDetector
//import android.view.ScaleGestureDetector
//import android.graphics.Bitmap
//import android.graphics.PointF
//import android.graphics.Matrix
//import android.graphics.Point
//import android.util.AttributeSet
//import android.view.View
//import android.widget.ImageView
//import com.markcadag.movieviewer.R
//import android.content.Context;
//
///**
// * Created by markcadag on 4/7/18.
// */
//class ZoomableImageView(context: Context, attr: AttributeSet) : ImageView(context, attr) {
//
//    private val newMatrix = Matrix()
//    private var mode = NONE
//
//    private var isClick: Boolean = false
//
//    private val last = PointF()
//    private val start = PointF()
//    private val minScale = 1f
//    private val maxScale = 3.0f
//    private val m: FloatArray
//
//    private var redundantXSpace: Float = 0.toFloat()
//    private var redundantYSpace: Float = 0.toFloat()
//    private var width: Float = 0.toFloat()
//    private var height: Float = 0.toFloat()
//    private var saveScale = 1f
//    private var right: Float = 0.toFloat()
//    private var bottom: Float = 0.toFloat()
//    private var origWidth: Float = 0.toFloat()
//    private var origHeight: Float = 0.toFloat()
//    private var bmWidth: Float = 0.toFloat()
//    private var bmHeight: Float = 0.toFloat()
//
//    private val mScaleDetector: ScaleGestureDetector
//    private val mGestureDetector: GestureDetector
//    private var shouldOnMeasureBeCalled = true
//    private var onMeasure = 0
//
//    private var listener: ImageClickListener? = null
//    private var zoomByDoubleTap: Boolean = false
//
//    init {
//        val a = context.getTheme().obtainStyledAttributes(attr, R.styleable.ZoomableImageView, 0, 0)
//        try {
//            zoomByDoubleTap = a.getBoolean(R.styleable.ZoomableImageView_doubleTap, true)
//        } finally {
//            a.recycle()
//        }
//        super.setClickable(true)
//        mScaleDetector = ScaleGestureDetector(context, ScaleListener())
//        mGestureDetector = GestureDetector(context, GestureListener())
//        newMatrix.setTranslate(1f, 1f)
//        m = FloatArray(9)
//        setImageMatrix(newMatrix)
//        setScaleType(ScaleType.MATRIX)
//
//        setOnTouchListener(object : OnTouchListener {
//
//            override fun onTouch(v: View, event: MotionEvent): Boolean {
//                mScaleDetector.onTouchEvent(event)
//                mGestureDetector.onTouchEvent(event)
//
//                newMatrix.getValues(m)
//                val x = m[Matrix.MTRANS_X]
//                val y = m[Matrix.MTRANS_Y]
//                val curr = PointF(event.x, event.y)
//
//                when (event.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        last.set(event.x, event.y)
//                        start.set(last)
//                        mode = DRAG
//                        isClick = true
//                    }
//                    MotionEvent.ACTION_POINTER_DOWN -> {
//                        last.set(event.x, event.y)
//                        start.set(last)
//                        mode = ZOOM
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        if (isClick && (Math.abs(curr.x - last.x) > CLICK || Math.abs(curr.y - last.y) > CLICK)) {
//                            isClick = false
//                        }
//                        if (mode == ZOOM || mode == DRAG && saveScale > minScale) {
//                            var deltaX = curr.x - last.x
//                            var deltaY = curr.y - last.y
//                            val scaleWidth = Math.round(origWidth * saveScale).toFloat()
//                            val scaleHeight = Math.round(origHeight * saveScale).toFloat()
//                            if (scaleWidth < width) {
//                                deltaX = 0f
//                                if (y + deltaY > 0)
//                                    deltaY = -y
//                                else if (y + deltaY < -bottom)
//                                    deltaY = -(y + bottom)
//                            } else if (scaleHeight < height) {
//                                deltaY = 0f
//                                if (x + deltaX > 0)
//                                    deltaX = -x
//                                else if (x + deltaX < -right)
//                                    deltaX = -(x + right)
//                            } else {
//                                if (x + deltaX > 0)
//                                    deltaX = -x
//                                else if (x + deltaX < -right)
//                                    deltaX = -(x + right)
//
//                                if (y + deltaY > 0)
//                                    deltaY = -y
//                                else if (y + deltaY < -bottom)
//                                    deltaY = -(y + bottom)
//                            }
//                            newMatrix.postTranslate(deltaX, deltaY)
//                            last.set(curr.x, curr.y)
//                        }
//                    }
//
//                    MotionEvent.ACTION_UP -> {
//                        mode = NONE
//                        val xDiff = Math.abs(curr.x - start.x).toInt()
//                        val yDiff = Math.abs(curr.y - start.y).toInt()
//                        if (xDiff < CLICK && yDiff < CLICK)
//                            performClick()
//                        if (isClick) {
//                            v.performClick()
//                            if (listener != null) {
//                                listener!!.onClick(Point(((event.x - x) / m[0]).toInt(), ((event.y - y) / m[0]).toInt()))
//                            }
//                        }
//                    }
//
//                    MotionEvent.ACTION_POINTER_UP -> mode = NONE
//                }
//                setImageMatrix(newMatrix)
//                invalidate()
//                return true
//            }
//
//        })
//    }
//
//    fun setClickListener(listener: ImageClickListener) {
//        this.listener = listener
//    }
//
//    fun setZoomByDoubleTap(zoomByDoubleTap: Boolean) {
//        this.zoomByDoubleTap = zoomByDoubleTap
//    }
//
//    override fun performClick(): Boolean {
//        return super.performClick()
//    }
//
//    override fun setImageBitmap(bm: Bitmap) {
//        super.setImageBitmap(bm)
//        bmWidth = bm.width.toFloat()
//        bmHeight = bm.height.toFloat()
//    }
//
//    fun setShouldOnMeasureBeCalled(b: Boolean) {
//        if (b) {
//            onMeasure = 0
//        }
//        this.shouldOnMeasureBeCalled = b
//    }
//
//    fun zoom(zoomIn: Boolean) {
//        var endScale: Float
//        if (zoomIn)
//            endScale = saveScale * ANIMATION_ZOOM_PARAMETER
//        else
//            endScale = saveScale / ANIMATION_ZOOM_PARAMETER
//        if (endScale > maxScale) endScale = maxScale
//        if (endScale < minScale) endScale = minScale
//        val zoomAnimation = ValueAnimator.ofFloat(saveScale, endScale)
//        zoomAnimation.addUpdateListener { valueAnimator ->
//            val value = valueAnimator.animatedValue as Float
//            zoomScale(value)
//        }
//        zoomAnimation.duration = ANIMATION_ZOOM_DURATION.toLong()
//        zoomAnimation.start()
//    }
//
//    fun zoomScale(endScale: Float) {
//        scale(endScale / saveScale, width / 2, height / 2)
//        setImageMatrix(newMatrix)
//        invalidate()
//    }
//
//    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//
//        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
//            mode = ZOOM
//            return true
//        }
//
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            scale(detector.scaleFactor, detector.focusX, detector.focusY)
//            return true
//        }
//    }
//
//    private fun scale(scaleFactor: Float, xCenter: Float, yCenter: Float) {
//        var scaleFactor = scaleFactor
//        val origScale = saveScale
//        saveScale *= scaleFactor
//        if (saveScale > maxScale) {
//            saveScale = maxScale
//            scaleFactor = maxScale / origScale
//        } else if (saveScale < minScale) {
//            saveScale = minScale
//            scaleFactor = minScale / origScale
//        }
//        right = width * saveScale - width - 2f * redundantXSpace * saveScale
//        bottom = height * saveScale - height - 2f * redundantYSpace * saveScale
//        if (origWidth * saveScale <= width || origHeight * saveScale <= height) {
//            newMatrix.postScale(scaleFactor, scaleFactor, width / 2, height / 2)
//            if (scaleFactor < 1) {
//                newMatrix.getValues(m)
//                val x = m[Matrix.MTRANS_X]
//                val y = m[Matrix.MTRANS_Y]
//                if (scaleFactor < 1) {
//                    if (Math.round(origWidth * saveScale) < width) {
//                        if (y < -bottom)
//                            newMatrix.postTranslate(0f, -(y + bottom))
//                        else if (y > 0)
//                            newMatrix.postTranslate(0f, -y)
//                    } else {
//                        if (x < -right)
//                            newMatrix.postTranslate(-(x + right), 0f)
//                        else if (x > 0)
//                            newMatrix.postTranslate(-x, 0f)
//                    }
//                }
//            }
//        } else {
//            newMatrix.postScale(scaleFactor, scaleFactor, xCenter, yCenter)
//            newMatrix.getValues(m)
//            val x = m[Matrix.MTRANS_X]
//            val y = m[Matrix.MTRANS_Y]
//            if (scaleFactor < 1) {
//                if (x < -right)
//                    newMatrix.postTranslate(-(x + right), 0f)
//                else if (x > 0)
//                    newMatrix.postTranslate(-x, 0f)
//                if (y < -bottom)
//                    newMatrix.postTranslate(0f, -(y + bottom))
//                else if (y > 0)
//                    newMatrix.postTranslate(0f, -y)
//            }
//        }
//    }
//
//    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
//
//        override fun onDoubleTap(e: MotionEvent): Boolean {
//            if (zoomByDoubleTap)
//                zoom(true)
//            return false
//        }
//
//        override fun onLongPress(e: MotionEvent) {}
//
//        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
//            return false
//        }
//
//        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
//            if (e1 == null || e2 == null) return false
//            if (e1.pointerCount > 1 || e2.pointerCount > 1) return false
//            if (mScaleDetector.isInProgress || mode == ZOOM) return false
//            if ((Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY || Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) && (Math.abs(e2.x - e1.x) > SWIPE_MIN_DISTANCE || Math.abs(e2.y - e1.y) > SWIPE_MIN_DISTANCE)) {
//                val diffX = e2.x - e1.x
//                val diffY = e2.y - e1.y
//                //animateFling(velocityX/20, velocityY/20);
//                animateFling(diffX, diffY)
//                return true
//            }
//            return false
//        }
//    }
//
//    private fun animateFling(diffX: Float, diffY: Float) {
//        val flingAnimation = ValueAnimator.ofFloat(diffX / 5, 0f)
//        flingAnimation.addUpdateListener { valueAnimator ->
//            val newDiffX = valueAnimator.animatedValue as Float
//            val newDiffY = newDiffX * diffY / diffX
//            flingMatrix(newDiffX, newDiffY)
//        }
//        flingAnimation.duration = 300
//        flingAnimation.interpolator = DecelerateInterpolator()
//        flingAnimation.start()
//    }
//
//    private fun flingMatrix(xValue: Float, yValue: Float) {
//        var xValue = xValue
//        var yValue = yValue
//        right = width * saveScale - width - 2f * redundantXSpace * saveScale
//        bottom = height * saveScale - height - 2f * redundantYSpace * saveScale
//        newMatrix.getValues(m)
//        val x = m[Matrix.MTRANS_X]
//        val y = m[Matrix.MTRANS_Y]
//        var xFreeSpace = width - origWidth * saveScale
//        xFreeSpace = if (xFreeSpace < 0) 0.0f else xFreeSpace / 2
//        if (x + xValue - xFreeSpace <= -right) {
//            xValue = -(x + right + xFreeSpace)
//        } else if (x + xValue - xFreeSpace >= 0) {
//            xValue = -x
//        }
//        var yFreeSpace = height - origHeight * saveScale
//        yFreeSpace = if (yFreeSpace < 0) 0.0f else yFreeSpace / 2
//        if (y + yValue - yFreeSpace <= -bottom) {
//            yValue = -(y + bottom + yFreeSpace)
//        } else if (y + yValue - yFreeSpace >= 0) {
//            yValue = -y
//        }
//        newMatrix.postTranslate(xValue, yValue)
//        setImageMatrix(newMatrix)
//        invalidate()
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        if (!shouldOnMeasureBeCalled && onMeasure > 2) {
//            return
//        } else if (!shouldOnMeasureBeCalled) {
//            onMeasure++
//        }
//        width = MeasureSpec.getSize(widthMeasureSpec).toFloat()
//        height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
//        //Fit to screen.
//        val scale = Math.min(width / bmWidth, height / bmHeight)
//        newMatrix.setScale(scale, scale)
//        setImageMatrix(newMatrix)
//        saveScale = 1f
//
//        // Center the image
//        redundantYSpace = height - scale * bmHeight
//        redundantXSpace = width - scale * bmWidth
//        redundantYSpace /= 2f
//        redundantXSpace /= 2f
//
//        newMatrix.postTranslate(redundantXSpace, redundantYSpace)
//
//        origWidth = width - 2 * redundantXSpace
//        origHeight = height - 2 * redundantYSpace
//        right = width * saveScale - width - 2f * redundantXSpace * saveScale
//        bottom = height * saveScale - height - 2f * redundantYSpace * saveScale
//        setImageMatrix(newMatrix)
//    }
//
//    companion object {
//
//        private val ANIMATION_ZOOM_DURATION = 400
//        private val ANIMATION_ZOOM_PARAMETER = 1.3f
//        private val SWIPE_MIN_DISTANCE = 100
//        private val SWIPE_THRESHOLD_VELOCITY = 800
//
//        private val NONE = 0
//        private val DRAG = 1
//        private val ZOOM = 2
//        private val CLICK = 3
//    }
//
//}