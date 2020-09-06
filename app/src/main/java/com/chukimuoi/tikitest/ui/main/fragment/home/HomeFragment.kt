package com.chukimuoi.tikitest.ui.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.chukimmuoi.mbase.api.Resource
import com.chukimmuoi.mbase.extensions.gone
import com.chukimmuoi.mbase.extensions.visible
import com.chukimmuoi.mbase.fragment.BaseFragment
import com.chukimuoi.tikitest.R
import com.chukimuoi.tikitest.databinding.FragmentHomeBinding
import com.chukimuoi.tikitest.ui.main.MainActivity
import com.chukimuoi.tikitest.ui.main.fragment.banner.adapter.BannerAdapter
import com.chukimuoi.tikitest.utils.extensions.inflate
import com.chukimuoi.tikitest.utils.extensions.injectViewModel
import com.developers.chukimmuoi.indicator.PagesLessException
import kotlinx.android.synthetic.main.fragment_home.*

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

    private lateinit var adapter: BannerAdapter

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
        swipeContainer.setOnRefreshListener {
            viewModel.getBannerAndQuickLink()
        }

        initQuickLinkRecycle()
        initFlashDealRecycle()

        initBannerStatus()
        initBannerSessionStatus()
        initQuickLinkStatus()
        initFlashDealStatus()
    }

    override fun createVariableNormal(savedInstanceState: Bundle?) {
    }

    private fun initBannerStatus() {
        viewModel.loadBannerStatus.observe(
            this,
            Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        bannerPager.visible()
                        loadingBanner.start()
                    }
                    Resource.Status.NEXT -> {
                        it.data?.let {
                            val mobileUrls = it.data.map { it.mobileUrl }
                            val size = mobileUrls.size
                            if (size > 0) {
                                bannerPager.visible()
                                viewIndicator.visible()

                                displayBannerPager(mobileUrls)
                                viewModel.loadBannerSession(size)
                            } else {
                                bannerPager.gone()
                                viewIndicator.gone()
                            }
                            loadingBanner.stop()
                            loadingBanner.gone()
                        }

                        viewModel.loadQuickLinkStatus.apply {
                            postValue(Resource.loading())
                            postValue(Resource.next(viewModel.quickLink))
                        }
                    }
                }
            }
        )
    }

    private fun initBannerSessionStatus() {
        viewModel.loadBannerSessionStatus.observe(
            this,
            Observer {
                when (it.status) {
                    Resource.Status.NEXT -> {
                        it.data?.let {
                            loadChangeImage(it)
                        }
                    }
                }
            }
        )
    }

    private fun initQuickLinkStatus() {
        viewModel.loadQuickLinkStatus.observe(
            this,
            Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingQuickLink.start()
                    }
                    Resource.Status.NEXT -> {
                        it.data?.let {
                            val data = it.data
                            val size = data.size
                            if (size > 0) {
                                quickLinkRecycler.visible()

                                viewModel.quickLinkAdapter.setListItem(data[0])
                                viewModel.quickLinkAdapter.setListener {
                                    // TODO: Click action.
                                }
                            } else {
                                quickLinkRecycler.gone()
                            }
                            loadingQuickLink.stop()
                            loadingQuickLink.gone()
                        }

                        viewModel.getFlashDeal()
                    }
                }
            }
        )
    }

    private fun initQuickLinkRecycle() {
        binding.quickLinkRecycler.initGridLayoutManager(
            space = 0,
            spanCount = 2,
            isHorizontal = true,
            isReverse = false
        )
    }

    private fun displayBannerPager(list: List<String>) {
        adapter = BannerAdapter(childFragmentManager, list)
        bannerPager.offscreenPageLimit = 3
        bannerPager.adapter = adapter
        bannerPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }
        })

        try {
            viewIndicator.setViewpager(bannerPager)
        } catch (e: PagesLessException) {
            e.printStackTrace()
        }
    }

    private fun loadChangeImage(index: Int) {
        bannerPager.setCurrentItem(index, true)
    }

    private fun initFlashDealStatus() {
        viewModel.loadFlashDealStatus.observe(
            this,
            Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingFlashDeal.start()
                    }
                    Resource.Status.NEXT -> {
                        it.data?.let {
                            val data = it.data
                            val size = data.size
                            if (size > 0) {
                                flashDealRecycler.visible()

                                viewModel.flashDealAdapter.setListItem(data)
                                viewModel.flashDealAdapter.setListener {
                                    // TODO: Click action.
                                }
                            } else {
                                flashDealRecycler.gone()
                            }
                            loadingFlashDeal.stop()
                            loadingFlashDeal.gone()
                        }
                    }
                }
            }
        )
    }

    private fun initFlashDealRecycle() {
        binding.flashDealRecycler.initLinearLayoutManager(
            space = 0,
            isHorizontal = true)
    }
}