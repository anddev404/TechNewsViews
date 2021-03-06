package com.anddev404.tech_news_views.tools.scrolling

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Scrolling(
    onScrollToEndListListeener: OnScrollToEndListListener,
    recyclerView: RecyclerView
) {

    private var waitingForNewData = false
    private var onScrollToEndListListener: OnScrollToEndListListener? = null

    init {
        this.onScrollToEndListListener = onScrollToEndListListeener

        recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            var totalItemCount = 0
            var lastVisibleItem = 0
            var visibleThreshold = 10

            override fun onScrolled(
                recyclerView: RecyclerView, dx: Int, dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager: LinearLayoutManager? =
                    recyclerView.getLayoutManager() as LinearLayoutManager

                totalItemCount = linearLayoutManager?.itemCount ?: 0
                lastVisibleItem = linearLayoutManager?.findLastVisibleItemPosition() ?: 0
                if (!waitingForNewData && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    waitingForNewData = true
                    onScrollToEndListListeener?.endOfList()
                }
            }
        })
    }

    fun unlockListenerAfterUpdateList() {
        waitingForNewData = false
    }

}