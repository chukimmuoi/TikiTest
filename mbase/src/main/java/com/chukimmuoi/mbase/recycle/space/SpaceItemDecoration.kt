package com.chukimmuoi.mbase.recycle.space

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-23.
 */

/**
 * Xac dinh khoang cach cho cac item trong RecyclerView.
 *
 * Decorator which adds spacing around the tiles in a Grid layout RecyclerView. Apply to a RecyclerView with:
 * SpaceItemDecoration decoration = new SpaceItemDecoration(16);
 * mRecyclerView.addItemDecoration(decoration);
 * <p>
 * Feel free to add any value you wish for SpaceItemDecoration. That value determines the amount of spacing.
 */

 //https://github.com/jaychang0917/SimpleRecyclerView/blob/master/library/src/main/java/com/jaychang/srv/decoration/LinearSpacingItemDecoration.java
 //https://github.com/cymcsg/UltimateRecyclerView/blob/master/UltimateRecyclerView/ultimaterecyclerview/src/main/java/com/marshalchen/ultimaterecyclerview/grid/GridSpacingItemDecoration.java
 //https://gist.github.com/liangzhitao/e57df3c3232ee446d464

class SpaceItemDecoration(builder: Builder) : RecyclerView.ItemDecoration() {

    private var mSpace: Int            = 0
    private var mSpanCount: Int        = 0
    private var mLinearLayoutType: Int = 0

    init {
        mSpace            = builder.space
        mSpanCount        = builder.spanCount
        mLinearLayoutType = builder.linearLayoutType
    }

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        if (mSpace > 0) {
            if (mSpanCount > 0) {
                // Grid.
                outRect.left   = mSpace
                outRect.right  = mSpace
                outRect.bottom = mSpace
                outRect.top    = mSpace
            } else {
                // Linear.
                if (mLinearLayoutType == LinearLayoutManager.HORIZONTAL) {
                    outRect.right  = mSpace
                } else if (mLinearLayoutType == LinearLayoutManager.VERTICAL) {
                    outRect.bottom = mSpace
                }
            }
        }
    }

    class Builder(val space: Int) {
        var spanCount        = 0
        var linearLayoutType = 0

        fun setSpanCount(spanCount: Int) = apply { this.spanCount = spanCount }

        fun setLinearLayoutType(linearLayoutType: Int) = apply { this.linearLayoutType = linearLayoutType }

        fun build() = SpaceItemDecoration(this)
    }
}