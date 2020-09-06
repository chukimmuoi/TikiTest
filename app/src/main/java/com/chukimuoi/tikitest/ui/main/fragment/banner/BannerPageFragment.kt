package com.chukimuoi.tikitest.ui.main.fragment.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chukimmuoi.mbase.fragment.BaseFragment
import com.chukimuoi.tikitest.R
import com.chukimuoi.tikitest.databinding.FragmentBannerBinding
import com.chukimuoi.tikitest.ui.main.MainActivity
import com.chukimuoi.tikitest.utils.extensions.inflate
import com.chukimuoi.tikitest.utils.extensions.injectViewModel

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/5/20.
 */
class BannerPageFragment : BaseFragment() {

    companion object {
        const val BUNDLE_LINK_IMAGE_VALUES = "bundle_link_image_values"

        fun newInstance(linkImage: String): BannerPageFragment {
            val fragment = BannerPageFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_LINK_IMAGE_VALUES, linkImage)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentBannerBinding
    lateinit var viewModel: BannerViewModel

    private val activity: MainActivity by lazy {
        baseActivity as MainActivity
    }

    override fun injection() {
        viewModel = injectViewModel()
    }

    override fun createVariableStart(bundle: Bundle) {
        viewModel.linkImage.postValue(bundle.getString(BUNDLE_LINK_IMAGE_VALUES))
    }

    override fun createVariableReload(bundle: Bundle) {

    }

    override fun bindingData(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ): View {
        binding = container.inflate(R.layout.fragment_banner)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun createVariableView(view: View?, savedInstanceState: Bundle?) {

    }

    override fun createVariableNormal(savedInstanceState: Bundle?) {

    }
}