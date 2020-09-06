package com.chukimuoi.tikitest.ui.main.fragment.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.chukimmuoi.mbase.api.Resource
import com.chukimuoi.tikitest.data.repository.TikiRepository
import com.chukimuoi.tikitest.model.Banner
import com.chukimuoi.tikitest.model.FlashDeal
import com.chukimuoi.tikitest.model.QuickLink
import com.chukimuoi.tikitest.ui.base.BaseViewModel
import com.chukimuoi.tikitest.ui.main.fragment.home.adapter.FlashDealAdapter
import com.chukimuoi.tikitest.ui.main.fragment.home.adapter.QuickLinkAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/2/20.
 */
class HomeViewModel
@Inject constructor(application: Application) : BaseViewModel(application) {

    private val subscription by lazy { CompositeDisposable() }

    @Inject lateinit var tikiRepository: TikiRepository
    @Inject lateinit var quickLinkAdapter: QuickLinkAdapter
    @Inject lateinit var flashDealAdapter: FlashDealAdapter

    val loadBannerStatus        = MutableLiveData<Resource<Banner>>()
    val loadBannerSessionStatus = MutableLiveData<Resource<Int>>()
    val loadQuickLinkStatus     = MutableLiveData<Resource<QuickLink>>()
    val loadFlashDealStatus     = MutableLiveData<Resource<FlashDeal>>()

    lateinit var banner: Banner
    lateinit var quickLink: QuickLink
    lateinit var flashDeal: FlashDeal

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    init {
        quickLinkAdapter.setListItem(listOf())
        flashDealAdapter.setListItem(listOf())

        getBannerAndQuickLink()
    }

    fun getBannerAndQuickLink() {
        subscription.add(
            tikiRepository.getBannerAndQuickLink()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    Timber.e("BannerAndQuickLink: onSubscribe")
                    loadBannerStatus.postValue(Resource.loading())
                }
                .subscribeBy(
                    onNext = {
                        Timber.e("BannerAndQuickLink: onNext $it")

                        banner    = it.first
                        quickLink = it.second

                        loadBannerStatus.postValue(Resource.next(banner))
                    },
                    onComplete = {
                        Timber.e("BannerAndQuickLink: onComplete")
                    },
                    onError = {
                        Timber.e("BannerAndQuickLink: onError $it")
                    }
                )
        )
    }

    fun getFlashDeal() {
        subscription.add(
            tikiRepository.getFlashDeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    Timber.e("FlashDeal: onSubscribe")
                    loadFlashDealStatus.postValue(Resource.loading())
                }
                .subscribeBy(
                    onNext = {
                        Timber.e("FlashDeal: onNext $it")

                        flashDeal = it
                        loadFlashDealStatus.postValue(Resource.next(flashDeal))
                    },
                    onComplete = {
                        Timber.e("FlashDeal: onComplete")
                    },
                    onError = {
                        Timber.e("FlashDeal: onError $it")
                    }
                )
        )
    }

    fun loadBannerSession(size: Int) {
        var count = 0
        var isAdd = true
        subscription.add(
            Observable.interval(3, TimeUnit.SECONDS)
                .map {
                    if (isAdd) {
                        count += 1
                    } else {
                        count -= 1
                    }
                    if (count >= (size - 1)) {
                        count = size - 1
                        isAdd = false
                    } else {
                        if (count <= 0) {
                            count = 0
                            isAdd = true
                        }
                    }
                    count
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onNext = {
                        Timber.e("loadBannerSession onNext")

                        loadBannerSessionStatus.postValue(Resource.next(it))
                    },
                    onComplete = {
                        Timber.e("loadBannerSession onComplete")
                    },
                    onError = {
                        Timber.e("loadBannerSession onError")
                    }
                )
        )
    }
}