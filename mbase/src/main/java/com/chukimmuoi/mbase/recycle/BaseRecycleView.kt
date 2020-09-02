package com.chukimmuoi.mbase.recycle

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chukimmuoi.mbase.recycle.loadmore.EndlessRecyclerViewScrollListener
import com.chukimmuoi.mbase.recycle.loadmore.OnEndlessScrolling
import com.chukimmuoi.mbase.recycle.space.SpaceItemDecoration
import com.chukimmuoi.mbase.recycle.tv.TvRecyclerView
import timber.log.Timber
import kotlin.math.max
import kotlin.math.min

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-23.
 */
class BaseRecycleView : TvRecyclerView, IRecycleView.View {

    companion object {
        const val TYPE_LINEAR_LAYOUT         = 0xa
        const val TYPE_GRID_LAYOUT           = 0xb
        const val TYPE_STAGGERED_GRID_LAYOUT = 0xc
    }

    private var mContext: Context
    private var mTypeLayout = TYPE_LINEAR_LAYOUT

    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var mGridLayoutManager: GridLayoutManager
    private lateinit var mStaggeredGridLayoutManager: StaggeredGridLayoutManager

    private lateinit var mItemDecoration: ItemDecoration

    private var mEndlessScrollListener: EndlessRecyclerViewScrollListener? = null
    private var mOnEndlessScrolling: OnEndlessScrolling? = null

    private var mItemWidth: Int = 0
    private var mSpanCount: Int = 0

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        mContext = context
    }

    init {
        itemAnimator = DefaultItemAnimator()
        setHasFixedSize(true)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)

        layoutManager?.let { updateSpanCountWhenRuntime(it, mTypeLayout, mSpanCount, mItemWidth) }
    }

    override fun initLayoutManager(typeLayout: Int, space: Int, spanCount: Int, isHorizontal: Boolean, isReverse: Boolean) {
        mTypeLayout = typeLayout
        when (mTypeLayout) {
            TYPE_LINEAR_LAYOUT -> {
                mLinearLayoutManager = LinearLayoutManager(
                    mContext,
                    if (isHorizontal) {
                        LinearLayoutManager.HORIZONTAL
                    } else {
                        LinearLayoutManager.VERTICAL
                    },
                    isReverse
                )

                mItemDecoration = SpaceItemDecoration
                    .Builder(space)
                    .setLinearLayoutType(
                        if (isHorizontal) {
                            DividerItemDecoration.HORIZONTAL
                        } else {
                            DividerItemDecoration.VERTICAL
                        }
                    ).build()

                layoutManager = mLinearLayoutManager
            }
            TYPE_GRID_LAYOUT -> {
                if (spanCount > 0) {
                    mGridLayoutManager = GridLayoutManager(
                        mContext,
                        spanCount,
                        if (isHorizontal) {
                            GridLayoutManager.HORIZONTAL
                        } else {
                            GridLayoutManager.VERTICAL
                        },
                        isReverse
                    )

                    mItemDecoration = SpaceItemDecoration
                        .Builder(space)
                        .setSpanCount(spanCount)
                        .build()
                    setMargin(space)

                    layoutManager = mGridLayoutManager
                }
            }
            TYPE_STAGGERED_GRID_LAYOUT -> {
                if (spanCount > 0) {
                    mStaggeredGridLayoutManager = StaggeredGridLayoutManager(
                        spanCount,
                        if (isHorizontal)
                            StaggeredGridLayoutManager.HORIZONTAL
                        else
                            StaggeredGridLayoutManager.VERTICAL
                    )

                    mItemDecoration = SpaceItemDecoration
                        .Builder(space)
                        .setSpanCount(spanCount)
                        .build()
                    setMargin(space)

                    layoutManager = mStaggeredGridLayoutManager
                }
            }
            else -> {
                initLinearLayoutManager()
            }
        }

        removeAllAndAddItemDecoration {
            addItemDecoration(mItemDecoration)
        }
    }

    override fun initLinearLayoutManager(space: Int, isHorizontal: Boolean, isReverse: Boolean) {
        initLayoutManager(TYPE_LINEAR_LAYOUT, space, isHorizontal = isHorizontal, isReverse = isReverse)
    }

    override fun initGridLayoutManager(space: Int, spanCount: Int, itemWidth: Int,
                                       isHorizontal: Boolean, isReverse: Boolean) {
        mItemWidth = itemWidth
        mSpanCount = spanCount
        initLayoutManager(TYPE_GRID_LAYOUT, space, spanCount, isHorizontal, isReverse)
    }

    override fun initStaggeredGridLayoutManager(space: Int, spanCount: Int, itemWidth: Int,
                                                isHorizontal: Boolean, isReverse: Boolean) {
        mItemWidth = itemWidth
        mSpanCount = spanCount
        initLayoutManager(TYPE_STAGGERED_GRID_LAYOUT, space, spanCount, isHorizontal, isReverse)
    }

    override fun updateSpanCountWhenRuntime(layoutManager: LayoutManager, typeLayout: Int,
                                            spanCount: Int, itemWidth: Int) {
        if (itemWidth > 0) {
            val spanCount = max(min(spanCount, measuredWidth / itemWidth), 1)
            when (typeLayout) {
                TYPE_GRID_LAYOUT -> {
                    (layoutManager as GridLayoutManager).spanCount = spanCount
                    if (adapter is IRecycleView.GridLayout) {
                        (adapter as IRecycleView.GridLayout).handlerSpanCount(this, spanCount)
                    }
                }
                TYPE_STAGGERED_GRID_LAYOUT -> {
                    (layoutManager as StaggeredGridLayoutManager).spanCount = spanCount
                }
            }
        }
    }

    override fun setLinearLayoutSnapHelper(typeGravity: Int, isPager: Boolean) {
        mLinearLayoutManager?.let {
            when (typeGravity) {
                Gravity.CENTER -> {
                    val snapHelper = LinearSnapHelper()
                    onFlingListener = null
                    snapHelper.attachToRecyclerView(this)
                }
                else ->
                    if (isPager) {
                        //val gravityPagerSnapHelper = GravityPagerSnapHelper(typeGravity)
                        onFlingListener = null
                        //gravityPagerSnapHelper.attachToRecyclerView(this)
                    }
                    else {
                        //val gravitySnapHelper = GravitySnapHelper(typeGravity)
                        onFlingListener = null
                        //gravitySnapHelper.attachToRecyclerView(this)
                    }
            }
        }
    }

    fun onEndlessScrolling(onEndlessScrolling: OnEndlessScrolling) {
        this.mOnEndlessScrolling = onEndlessScrolling

        when (mTypeLayout) {
            TYPE_LINEAR_LAYOUT -> mEndlessScrollListener = object : EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView, actionIncrease: () -> Unit) {
                    actionLoadMore(page, totalItemsCount, view, actionIncrease)
                }
            }
            TYPE_GRID_LAYOUT -> mEndlessScrollListener = object : EndlessRecyclerViewScrollListener(mGridLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView, actionIncrease: () -> Unit) {
                    actionLoadMore(page, totalItemsCount, view, actionIncrease)
                }
            }
            TYPE_STAGGERED_GRID_LAYOUT -> mEndlessScrollListener = object : EndlessRecyclerViewScrollListener(mStaggeredGridLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView, actionIncrease: () -> Unit) {
                    actionLoadMore(page, totalItemsCount, view, actionIncrease)
                }
            }
        }

        mEndlessScrollListener?.let { addOnScrollListener(it) }
    }

    override fun resetEndlessScrolling() : Boolean {
        mEndlessScrollListener?.let {
            it.resetState()
            return true
        }
        return false
    }

    private fun actionLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView, actionIncrease: () -> Unit) {
        view.post {
            Timber.i("Action Load More: page = $page, totalItemsCount = $totalItemsCount")
            mOnEndlessScrolling?.loadNextPage(page, totalItemsCount, view, actionIncrease)
        }
    }

    override fun setMargin(value: Int) {
        val marginLayoutParams = layoutParams as MarginLayoutParams
        marginLayoutParams.setMargins(value, value, value, value)
        layoutParams = marginLayoutParams
    }

    override fun removeAllAndAddItemDecoration(addAction: () -> Unit) {
        if (itemDecorationCount > 0) {
            for (i in 0 until itemDecorationCount) {
                removeItemDecorationAt(i)
            }
        }

        addAction()
    }
}