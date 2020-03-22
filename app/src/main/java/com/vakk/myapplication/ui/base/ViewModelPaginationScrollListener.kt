package com.vakk.myapplication.ui.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

/**
 * Use this scroll listener with LinearLayoutManager.
 */
class ViewModelPaginationScrollListener(
    paginationViewModel: PaginationViewModel,
    val pagesThreshold: Int = LOAD_MORE_PAGES_TRESHOLD,
    val visibleItemsPerPageMultiplier: Int = VISIBLE_ITEMS_PER_PAGE_MULTIPLIER
) : RecyclerView.OnScrollListener() {

    companion object {
        private const val VISIBLE_ITEMS_PER_PAGE_MULTIPLIER = 3
        private const val LOAD_MORE_PAGES_TRESHOLD = 3
    }

    private val viewModelWR = WeakReference(paginationViewModel)

    private val viewModel: PaginationViewModel?
        get() = viewModelWR.get()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val viewModel = viewModel ?: return
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (viewModel.itemsPerPage < visibleItemCount * visibleItemsPerPageMultiplier) {
            viewModel.itemsPerPage = visibleItemCount * visibleItemsPerPageMultiplier
        }

        val isNeedToLoadMore = (firstVisibleItemPosition + visibleItemCount >= (totalItemCount - viewModel.itemsPerPage * pagesThreshold) && firstVisibleItemPosition >= 0)
        if (isNeedToLoadMore && !viewModel.isLastPage && !viewModel.isPaginationInProcess) {
            viewModel.loadMoreItems()
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        when (newState) {
            RecyclerView.SCROLL_STATE_SETTLING -> onScrolled(recyclerView, 0, 0)
            else -> super.onScrollStateChanged(recyclerView, newState)
        }
    }
}

interface PaginationViewModel {
    val isLastPage: Boolean
    val isPaginationInProcess: Boolean

    var itemsPerPage: Int // this is a value for initial pagination part. For the next time - it will be calculated by visible items count.

    fun loadMoreItems()
}