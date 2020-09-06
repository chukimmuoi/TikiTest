package com.chukimuoi.tikitest.ui.main.fragment.home.adapter.delegate

import android.app.Application
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chukimmuoi.mbase.extensions.gone
import com.chukimmuoi.mbase.extensions.onClickDebounce
import com.chukimmuoi.mbase.extensions.visible
import com.chukimuoi.tikitest.R
import com.chukimuoi.tikitest.databinding.ItemFlashDealBinding
import com.chukimuoi.tikitest.model.FlashDeal
import com.chukimuoi.tikitest.ui.main.fragment.home.adapter.ItemFlashDealViewModel
import com.chukimuoi.tikitest.utils.extensions.inflate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/6/20.
 */
class FlashDealDelegate (
    private val application: Application,
    private val onClick: (FlashDeal.Data) -> Unit) : AdapterDelegate<MutableList<Any>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding: ItemFlashDealBinding = parent.inflate(R.layout.item_flash_deal)
        return ViewHolder(binding)
    }

    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean {
        return items[position] is FlashDeal.Data
    }

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>) {

        val viewHolder = holder as ViewHolder
        val item = items[position] as FlashDeal.Data
        viewHolder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemFlashDealBinding)
        : RecyclerView.ViewHolder(binding.root) {

        private val viewModel = ItemFlashDealViewModel(application)

        fun bind(data: FlashDeal.Data) {
            viewModel.bind(data)
            binding.viewModel = viewModel

            with(data) {
                if (product.discount > 0) {
                    binding.textDiscount.visible()
                } else {
                    binding.textDiscount.gone()
                }

                binding.root.onClickDebounce {
                    onClick(this)
                }
            }
        }
    }
}