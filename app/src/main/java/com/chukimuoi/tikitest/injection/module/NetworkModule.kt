package com.chukimuoi.tikitest.injection.module

import android.app.Application
import android.content.Context
import com.chukimuoi.tikitest.data.online.TikiApi
import com.chukimuoi.tikitest.data.online.interceptor.AppInterceptor
import com.chukimuoi.tikitest.injection.ApplicationContext
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vinsmart.vinbrowserround3.data.cache.PreferenceSettings
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@Suppress("unused")
object NetworkModule {

    private const val VALUES_ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    private const val VALUES_CONNECT_TIMEOUT = 15L
    private const val VALUES_READ_TIMEOUT    = 15L
    private const val VALUES_WRITE_TIMEOUT   = 15L

    @Provides
    @Reusable
    @JvmStatic
    fun provideGson(): Gson {
        return GsonBuilder()
            .setDateFormat(VALUES_ISO_FORMAT)
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            //.setLenient()
            .create()
    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideInterceptor(@ApplicationContext context: Context,
                           preferenceSettings: PreferenceSettings
    ) : Interceptor = AppInterceptor(context,preferenceSettings)

    @Provides
    @Reusable
    @JvmStatic
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB.
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideOkHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor) // or addNetworkInterceptor(interceptor)
            .connectTimeout(VALUES_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(VALUES_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(VALUES_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(TikiApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApi(retrofit: Retrofit): TikiApi {
        return retrofit.create(TikiApi::class.java)
    }
}