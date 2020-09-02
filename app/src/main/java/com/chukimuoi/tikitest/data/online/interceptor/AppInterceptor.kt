package com.chukimuoi.tikitest.data.online.interceptor

import android.content.Context
import com.chukimmuoi.mbase.BuildConfig
import com.vinsmart.vinbrowserround3.data.cache.PreferenceSettings
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

class AppInterceptor (context: Context,
                      private val preferenceSettings: PreferenceSettings): Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                //.addHeader("Authorization", "key ${ BuildConfig.API_DEVELOPER_TOKEN }")
                .build()

        val response = chain.proceed(request)
        handlerCodeResult(response.code())

        if (BuildConfig.DEBUG) showInformationService(chain, request, response)

        return response
    }

    private fun showInformationService(
        chain: Interceptor.Chain,
        request: Request,
        response: Response
    ) {

        val timeStart = System.nanoTime()
        Timber.i(
            String.format(
                "Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()
            )
        )

        val timeEnd = System.nanoTime()
        Timber.i(
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request().url(), (timeEnd - timeStart) / 1e6, response.headers()
            )
        )
    }

    private fun handlerCodeResult(code: Int) =
        when(code) {
            200 -> "Success"
            201 -> "Successfully created item"
            204 -> "Item deleted successfully"
            400 -> "Something was wrong with the format of your request"
            401 -> "Unauthorized - your API key is invalid"
            403 -> "Forbidden - you do not have access to operate on the requested item(s)"
            404 -> "Item not found"
            429 -> "Request was throttled - you are sending too many requests too fast.\n" +
                    "Normal user accounts are allowed to send on average one request/sec, with some small allowance for burst traffic.\n" +
                    "Example response: { \"detail\": \"Request was throttled. Expected available in 2 seconds.\" }"
            else -> "Response Codes not exist"
        }
}