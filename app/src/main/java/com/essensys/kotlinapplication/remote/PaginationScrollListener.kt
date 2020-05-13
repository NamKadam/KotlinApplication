package com.essensys.kotlinapplication.remote

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Pagination
 * Created by Suleiman19 on 10/15/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */
abstract class PaginationScrollListener
/**
 * Supporting only LinearLayoutManager for now.
 *
 * @param layoutManager
 */(internal var layoutManager: LinearLayoutManager?) : RecyclerView.OnScrollListener() {

    abstract var totalPageCount: Int
    //
    abstract val isLastPage: Boolean

    abstract var isLoading: Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager!!.childCount
        val totalItemCount = layoutManager!!.itemCount
        val firstVisibleItemPosition = layoutManager!!.findFirstVisibleItemPosition()

        //        if (!isLoading() && !isLastPage()) {
        //            if (!isLoading() ) {
        //
        //                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
        //                    && firstVisibleItemPosition >= 0
        //                    && totalItemCount >= getTotalPageCount()) {
        //                loadMoreItems();
        //            }
        //        }

        val view = recyclerView.getChildAt(recyclerView.childCount - 1)
        if (view != null) {
            val diff = view.bottom - (recyclerView.height + recyclerView.scrollY)
            // if diff is zero, then the bottom has been reached
            if (diff == 0) {
                loadMoreItems()
            }

        }

    }


    protected abstract fun loadMoreItems()

}

