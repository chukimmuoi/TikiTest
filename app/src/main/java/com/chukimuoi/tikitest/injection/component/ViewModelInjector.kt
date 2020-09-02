package com.chukimuoi.tikitest.injection.component

import com.chukimuoi.tikitest.TikiApplication
import com.chukimuoi.tikitest.data.online.interceptor.AppInterceptor
import com.chukimuoi.tikitest.injection.module.ApplicationModule
import com.chukimuoi.tikitest.injection.module.NetworkModule
import com.chukimuoi.tikitest.ui.main.MainViewModel
import com.chukimuoi.tikitest.ui.main.fragment.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (ApplicationModule::class)
    , (NetworkModule::class)
])
interface ViewModelInjector {

    fun inject(tikiApplication: TikiApplication)
    fun inject(appInterceptor: AppInterceptor)


    fun inject(viewModel: MainViewModel)
    fun inject(viewModel: HomeViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun applicationModule(applicationModule: ApplicationModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder
    }
}