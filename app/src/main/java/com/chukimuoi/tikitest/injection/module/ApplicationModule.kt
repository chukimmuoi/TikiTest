package com.chukimuoi.tikitest.injection.module

import android.app.Application
import android.content.Context
import com.chukimuoi.tikitest.injection.ApplicationContext
import com.vinsmart.vinbrowserround3.data.cache.PreferenceSettings
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

@Module
@Suppress("unused")
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication() : Application = application

    @Provides
    @Singleton
    @ApplicationContext
    internal fun providesContext() : Context = application

    @Provides
    @Singleton
    internal fun providesEventBus() : EventBus = EventBus.getDefault()

    @Provides
    @Singleton
    internal fun providesPreferenceSettings(@ApplicationContext context: Context) = PreferenceSettings(context)
}