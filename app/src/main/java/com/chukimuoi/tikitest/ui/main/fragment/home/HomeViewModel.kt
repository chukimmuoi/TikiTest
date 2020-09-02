package com.chukimuoi.tikitest.ui.main.fragment.home

import android.app.Application
import com.chukimuoi.tikitest.data.repository.TikiRepository
import com.chukimuoi.tikitest.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
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

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    init {
        getBannerAndQuickLink()
    }

    fun getBannerAndQuickLink() {
        subscription.add(
            tikiRepository.getBannerAndQuickLink()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    Timber.e("BannerAndQuickLink: onSubscribe")
                }
                .subscribeBy(
                    onNext = {
                        Timber.e("BannerAndQuickLink: onNext $it")
                    },
                    onComplete = {
                        Timber.e("BannerAndQuickLink: onComplete")
                        getFlashDeal()
                    },
                    onError = {
                        Timber.e("BannerAndQuickLink: onError $it")
                    }
                )
        )
    }

    private fun getFlashDeal() {
        subscription.add(
            tikiRepository.getFlashDeal().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    Timber.e("FlashDeal: onSubscribe")
                }
                .subscribeBy(
                    onNext = {
                        Timber.e("FlashDeal: onNext $it")
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
}