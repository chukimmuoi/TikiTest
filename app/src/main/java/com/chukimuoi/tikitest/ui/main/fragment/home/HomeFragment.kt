package com.chukimuoi.tikitest.ui.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chukimmuoi.mbase.fragment.BaseFragment
import com.chukimuoi.tikitest.R
import com.chukimuoi.tikitest.databinding.FragmentHomeBinding
import com.chukimuoi.tikitest.ui.main.MainActivity
import com.chukimuoi.tikitest.utils.extensions.inflate
import com.chukimuoi.tikitest.utils.extensions.injectViewModel

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/2/20.
 */
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel

    private val activity: MainActivity by lazy {
        baseActivity as MainActivity
    }

    override fun injection() {
        viewModel = injectViewModel()
    }

    override fun createVariableStart(bundle: Bundle) {

    }

    override fun createVariableReload(bundle: Bundle) {

    }

    override fun bindingData(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        binding = container.inflate(R.layout.fragment_home)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun createVariableView(view: View?, savedInstanceState: Bundle?) {

    }

    override fun createVariableNormal(savedInstanceState: Bundle?) {

    }
}