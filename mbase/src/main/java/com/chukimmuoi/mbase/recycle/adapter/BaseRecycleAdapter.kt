package com.chukimmuoi.mbase.recycle.adapter

import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import timber.log.Timber
import java.util.Collections
import kotlin.math.min

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-23.
 */
open class BaseRecycleAdapter : ListDelegationAdapter<MutableList<Any>>(), IRecycleAdapter {

    private lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun setListItem(listItem: List<Any>) {
        if (items == null) {
            items = listItem.toMutableList()
        } else {
            items.clear()
            items.addAll(listItem)
        }
        notifyDataSetChanged()
    }

    override fun insertItem(position: Int, any: Any, isScroll: Boolean) {
        mRecyclerView.post {
            any?.let {
                val index = mapRangePosition(position)
                items.add(index, it)
                notifyItemInserted(index)

                if (isScroll) {
                    mRecyclerView.scrollToPosition(index)
                }
            }
        }
    }

    override fun insertItem(any: Any, isScroll: Boolean) {
        insertItem(itemCount, any, isScroll)
    }

    override fun insertItems(positionStart: Int, insertList: List<Any>, isScroll: Boolean) {
        mRecyclerView.post {
            insertList?.let {
                val index = mapRangePosition(positionStart)
                val count = insertList.size
                if (count > 0) {
                    items.addAll(index, insertList)
                    notifyItemRangeInserted(index, count)

                    if (isScroll) {
                        mRecyclerView.scrollToPosition(index)
                    }
                }
            }
        }
    }

    override fun insertItems(insertList: List<Any>, isScroll: Boolean) {
        insertItems(itemCount, insertList, isScroll)
    }

    override fun loadMoreItem(insertList: List<Any>) {
        removeItem(items.lastIndex)
        insertItems(insertList)
    }

    override fun updateItem(position: Int, isScroll: Boolean) {
        handlerCheckUpdateOrRemove(position) {
            notifyItemChanged(position)

            if (isScroll) {
                mRecyclerView.scrollToPosition(position)
            }
        }
    }

    //https://stackoverflow.com/questions/32769009/recyclerview-notifyitemrangechanged-doesnt-show-new-data
    override fun updateItem(positionStart: Int, range: Int, isScroll: Boolean) {
        handlerCheckUpdateOrRemoveWithRange(positionStart, range) {
            notifyItemRangeChanged(positionStart, it)

            if (isScroll) {
                mRecyclerView.scrollToPosition(positionStart)
            }
        }
    }

    override fun removeItem(position: Int, isScroll: Boolean) {
        handlerCheckUpdateOrRemove(position) {
            items.removeAt(position)
            notifyItemRemoved(position)

            if (isScroll) {
                val position = position - 1
                mRecyclerView.scrollToPosition(if (position >= 0) position else 0)
            }
        }
    }

    override fun removeItem(positionStart: Int, range: Int, isScroll: Boolean) {
        handlerCheckUpdateOrRemoveWithRange(positionStart, range) {
            for (i in 0 until it) {
                items.removeAt(positionStart)
            }
            notifyItemRangeRemoved(positionStart, it)

            if (isScroll) {
                val position = positionStart - 1
                mRecyclerView.scrollToPosition(if (position >= 0) position else 0)
            }
        }
    }

    override fun movedItem(fromPosition: Int, toPosition: Int, isScroll: Boolean) {
        handlerCheckMovedOrSwap(fromPosition, toPosition) {
            val itemFrom = items[fromPosition]
            items.removeAt(fromPosition)
            items.add(toPosition, itemFrom)
            notifyItemMoved(fromPosition, toPosition)

            if (isScroll) {
                mRecyclerView.scrollToPosition(toPosition)
            }
        }
    }

    override fun swapItem(firstPosition: Int, secondPosition: Int, isScroll: Boolean) {
        handlerCheckMovedOrSwap(firstPosition, secondPosition) {
            Collections.swap(items, firstPosition, secondPosition)
            notifyItemMoved(firstPosition, secondPosition)

            if (isScroll) {
                val position = min(firstPosition, secondPosition)
                mRecyclerView.scrollToPosition(position)
            }
        }
    }

    override fun clearAll() {
        mRecyclerView.post {
            items.clear()
            notifyDataSetChanged()
        }
    }

    override fun reloadAll(isScroll: Boolean, position: Int) {
        mRecyclerView.post {
            notifyDataSetChanged()

            if (isScroll) {
                mRecyclerView.scrollToPosition(position)
            }
        }
    }

    private fun mapRangePosition(position: Int) : Int {
        return when {
            position < 0 -> 0
            position > itemCount -> itemCount
            else -> position
        }
    }

    private fun handlerCheckUpdateOrRemove(position: Int, action: () -> Unit) {
        mRecyclerView.post {
            if (position in 0 until itemCount) {
                action()
            } else {
                showRangeError(listOf(position))
            }
        }
    }

    private fun handlerCheckUpdateOrRemoveWithRange(start: Int, range: Int, action: (Int) -> Unit) {
        mRecyclerView.post {
            if (start >= 0 && range > 0) {
                val inputSize = start + range
                val range = if (inputSize <= itemCount) range else (itemCount - start)
                action(range)
            } else {
                showRangeError(listOf(start, range))
            }
        }
    }

    private fun handlerCheckMovedOrSwap(first: Int, second: Int, action:() -> Unit) {
        mRecyclerView.post {
            if (first in 0 until itemCount && second in 0 until itemCount) {
                if (first != second) {
                    action()
                } else {
                    showRangeError(listOf(first, second))
                }
            } else {
                showRangeError(listOf(first, second))
            }
        }
    }

    private fun showRangeError(value: List<Int>) {
        Timber.e("Error range in 0..$itemCount | $value not in.")
    }
}