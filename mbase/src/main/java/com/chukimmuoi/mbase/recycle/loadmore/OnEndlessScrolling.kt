package com.chukimmuoi.mbase.recycle.loadmore

import androidx.recyclerview.widget.RecyclerView
import com.chukimmuoi.mbase.recycle.BaseRecycleView
import com.chukimmuoi.mbase.recycle.adapter.BaseRecycleAdapter

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-23.
 */
interface OnEndlessScrolling {
    /**
     * Load new data
     *
     * @see {http://stackoverflow.com/questions/39445330/cannot-call-notifyiteminserted-method-in-a-scroll-callback-recyclerview-v724-2}
     * <p>
     * view.post(() -> mAdapter.insertItem(positionStart, insertList));
     * [BaseRecycleAdapter.insertItem]
     */
     fun loadNextPage(page: Int, totalItemsCount: Int, view: RecyclerView, actionIncrease: () -> Unit)

    /**
     * Clear all data ---> new action.
     * <p>
     * mList.clear();
     * mAdapter.notifyDataSetChanged(); [BaseRecycleAdapter.reloadAll]
     * mEndlessScrollListener.resetState(); [BaseRecycleView.resetEndlessScrolling]
     */
     fun resetEndless()
}