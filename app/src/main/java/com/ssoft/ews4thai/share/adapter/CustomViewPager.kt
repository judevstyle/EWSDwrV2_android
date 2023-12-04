package com.ssoft.ews4thai.share.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager

class CustomViewPager(context: Context, attrs: AttributeSet) :
    ViewPager(context, attrs) {


    private companion object {
        const val DEFAULT_SPEED = 1000
    }

    init {
        setScrollerSpeed(DEFAULT_SPEED)
    }

    var scrollDuration = DEFAULT_SPEED
        set(millis) {
            setScrollerSpeed(millis)
        }

    private fun setScrollerSpeed(millis: Int) {
        try {
            ViewPager::class.java.getDeclaredField("mScroller")
                .apply {
                    isAccessible = true
                    set(this@CustomViewPager, OwnScroller(millis))
                }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class OwnScroller(private val durationScrollMillis: Int) : Scroller(context, AccelerateDecelerateInterpolator()) {
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, durationScrollMillis)
        }
    }
}