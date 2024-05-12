package app.movieapp.inject

import app.movieapp.data.api.HeaderInterceptor
import app.movieapp.data.api.TmdbImageUrlProvider
import app.movieapp.data.api.TmdbManager
import app.moviebase.tmdb.Tmdb3
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

interface NetworkComponent {

    @ApplicationScope
    @Provides
    fun provideInterceptor():Interceptor = HeaderInterceptor()

    @ApplicationScope
    @Provides
    fun provideOkHttpClient(
        interceptor: Interceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(10,TimeUnit.SECONDS)
        .readTimeout(10,TimeUnit.SECONDS)
        .build()

    @ApplicationScope
    @Provides
    fun provideKtorClient(
        client:OkHttpClient
    ) :HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            defaultRequest {
                host = "api.themoviedb.org"
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
            engine {
                preconfigured = client
            }
        }
    }

    @ApplicationScope
    @Provides
    fun provideTmdb(
        client: OkHttpClient
    ) = Tmdb3 {
        tmdbApiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZTQzNTRhN2U3NzJjY2E3NmU1NTQzNTBlMzgyZGU2MSIsInN1YiI6IjY0MjU3ZmFlOTYwY2RlMDA3NzExYzc1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Jk5r97HkyY204CQKiMC0Hrxwr2ttrsk2urJP75B6r1c"
        expectSuccess = false // if you want to disable exceptions
        useCache = true
        useTimeout = true
        maxRetriesOnException = 3 // retries when network calls throw an exception

        httpClient(OkHttp) {
            engine {
                preconfigured = client
            }
        }
    }
    @ApplicationScope
    @Provides
    fun provideTmdbImageUrlProvider(tmdbManager: TmdbManager): TmdbImageUrlProvider {
        return tmdbManager.getLatestImageProvider()
    }

//    @Provides
//    @IntoSet
//    fun provideTmdbInitializer(bind: TmdbInitializer): AppInitializer = bind
}