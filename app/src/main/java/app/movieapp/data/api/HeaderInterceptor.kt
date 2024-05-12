package app.movieapp.data.api

import android.util.Log
import app.movieapp.AppApplication
import app.movieapp.BuildConfig
//import app.movieapp.BuildConfig
import me.tatarka.inject.annotations.Inject
import okhttp3.Interceptor
import okhttp3.Response

@Inject
class HeaderInterceptor(
//    private val locale: Locale
    ):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        application.getString()
        val apikey = BuildConfig.TMDB_API_KEY
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $apikey")

            .build()
        return chain.proceed(request)
    }
}