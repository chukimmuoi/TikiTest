package com.chukimuoi.tikitest.ui.main.fragment.home.adapter.delegate

import android.app.Application
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chukimmuoi.mbase.extensions.onClickDebounce
import com.chukimuoi.tikitest.R
import com.chukimuoi.tikitest.databinding.ItemQuickLinkBinding
import com.chukimuoi.tikitest.model.QuickLink
import com.chukimuoi.tikitest.ui.main.fragment.home.adapter.ItemQuickLinkViewModel
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
class QuickLinkDelegate (
    private val application: Application,
    private val onClick: (QuickLink.Data) -> Unit) : AdapterDelegate<MutableList<Any>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding: ItemQuickLinkBinding = parent.inflate(R.layout.item_quick_link)
        return ViewHolder(binding)
    }

    override fun isForViewType(items: MutableList<Any>, position: Int): Boolean {
        return items[position] is QuickLink.Data
    }

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>) {

        val viewHolder = holder as ViewHolder
        val item = items[position] as QuickLink.Data
        viewHolder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemQuickLinkBinding)
        : RecyclerView.ViewHolder(binding.root) {

        private val viewModel = ItemQuickLinkViewModel(application)

        fun bind(data: QuickLink.Data) {
            viewModel.bind(data)
            binding.viewModel = viewModel

            with(data) {

                binding.root.onClickDebounce {
                    onClick(this)
                }
            }
        }
    }
}