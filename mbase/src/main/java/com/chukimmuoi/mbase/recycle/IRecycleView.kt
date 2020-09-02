package com.chukimmuoi.mbase.recycle

import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-23.
 */
object IRecycleView {
    const val SPACES_ITEM = 16

    interface View {
        /**
         * Su dung duoc cho toan bo cac layout manager
         * <p>
         * @param typeLayout bap gom 3 loai
         * @param spanCount so cot su dung cho layout manager dang grid
         * @param isHorizontal true: hien thi theo chieu ngang, false: hien thi theo chieu doc
         * @param isReverse true: dao huong scroll
         * */
        fun initLayoutManager(typeLayout: Int,
                              space: Int = SPACES_ITEM,
                              spanCount: Int = 0,
                              isHorizontal: Boolean = false,
                              isReverse: Boolean = false)

        /**
         * Su dung cho LinearLayoutManager
         * <p>
         * @param isHorizontal
         * @param isReverse
         * */
        fun initLinearLayoutManager(space: Int = SPACES_ITEM,
                                    isHorizontal: Boolean = false,
                                    isReverse: Boolean = false)

        /**
         * Su dung cho GridLayoutManager
         * <p>
         * @param spanCount so cot nho nhat mac dinh
         * @param itemWidth kich thuoc cua 1 item
         * @param isHorizontal
         * @param isReverse
         * */
        fun initGridLayoutManager(space: Int = SPACES_ITEM,
                                  spanCount: Int,
                                  itemWidth: Int = -1,
                                  isHorizontal: Boolean = false,
                                  isReverse: Boolean = false)

        /**
         * Su dung cho StaggeredGridLayoutManager
         * <p>
         * @param spanCount so cot nho nhat mac dinh
         * @param itemWidth kich thuoc cua 1 item (dp)
         * @param isHorizontal
         * @param isReverse
         * */
        fun initStaggeredGridLayoutManager(space: Int = SPACES_ITEM,
                                           spanCount: Int,
                                           itemWidth: Int = -1,
                                           isHorizontal: Boolean = false,
                                           isReverse: Boolean = false)

        /**
         * Update spanCount theo kich thuoc man hinh
         * <p>
         * @param layoutManager
         * @param typeLayout
         * @param spanCount so cot mac dinh
         * @param itemWidth kich thuoc cua 1 item (dp)
         * */
        fun updateSpanCountWhenRuntime(layoutManager: RecyclerView.LayoutManager,
                                       typeLayout: Int,
                                       spanCount: Int,
                                       itemWidth: Int)

        fun setLinearLayoutSnapHelper(typeGravity: Int = Gravity.CENTER, isPager: Boolean = false)

        fun resetEndlessScrolling(): Boolean

        fun setMargin(value: Int)

        fun removeAllAndAddItemDecoration(addAction: () -> Unit)
    }

    interface GridLayout {
        fun handlerSpanCount(recyclerView: RecyclerView, spanCount: Int)
    }
}