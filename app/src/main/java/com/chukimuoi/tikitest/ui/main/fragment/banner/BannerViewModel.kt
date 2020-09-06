package com.chukimuoi.tikitest.ui.main.fragment.banner

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.chukimuoi.tikitest.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : TikiTest
 * Created by chukimmuoi on 9/5/20.
 */
class BannerViewModel
@Inject constructor(application: Application) : BaseViewModel(application) {

    private val subscription by lazy { CompositeDisposable() }

    var linkImage = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    init {

    }
}