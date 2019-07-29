package com.jw.flickrviewr.util

import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView

/**
 * Extension method for RecyclerView to support shared element return transition.
 */
inline fun RecyclerView.afterMeasure(crossinline callback: RecyclerView.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                callback()
            }
        }
    })
}

fun RecyclerView.setRecyclerViewListeners(
    onScrolled: (recyclerView: RecyclerView, dx: Int, dy: Int) -> Unit,
    onScrollStateChanged: (recyclerView: RecyclerView, newState: Int) -> Unit) {

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onScrolled(recyclerView, dx, dy)
        }
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            onScrollStateChanged(recyclerView, newState)
        }
    })
}