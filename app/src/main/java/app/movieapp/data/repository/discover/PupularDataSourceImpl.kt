package app.movieapp.data.repository.discover

import app.movieapp.data.model.dto.movies.MoviePaginationResult
import app.moviebase.tmdb.Tmdb3
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import me.tatarka.inject.annotations.Inject

@Inject
class PupularDataSourceImpl(
    private val tmdb: Tmdb3,
    private val client: HttpClient

):PopularDataSource {
    override suspend fun getPopularMovies(page:Int): MoviePaginationResult {
        val res =  client.get("/3/movie/popular?language=en-US&page=$page"){
//            header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZTQzNTRhN2U3NzJjY2E3NmU1NTQzNTBlMzgyZGU2MSIsInN1YiI6IjY0MjU3ZmFlOTYwY2RlMDA3NzExYzc1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Jk5r97HkyY204CQKiMC0Hrxwr2ttrsk2urJP75B6r1c")
        }.body<MoviePaginationResult>()
        return res
    }
    override suspend fun getNowPlayingMovies(page:Int): MoviePaginationResult {
        val res =  client.get("/3/movie/now_playing?language=en-US&page=$page"){
//            header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZTQzNTRhN2U3NzJjY2E3NmU1NTQzNTBlMzgyZGU2MSIsInN1YiI6IjY0MjU3ZmFlOTYwY2RlMDA3NzExYzc1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Jk5r97HkyY204CQKiMC0Hrxwr2ttrsk2urJP75B6r1c")
        }.body<MoviePaginationResult>()
        return res
    }
    override suspend fun getUpcomingMovies(page:Int): MoviePaginationResult {
        val res =  client.get("/3/movie/upcoming?language=en-US&page=$page"){
//            header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZTQzNTRhN2U3NzJjY2E3NmU1NTQzNTBlMzgyZGU2MSIsInN1YiI6IjY0MjU3ZmFlOTYwY2RlMDA3NzExYzc1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Jk5r97HkyY204CQKiMC0Hrxwr2ttrsk2urJP75B6r1c")
        }.body<MoviePaginationResult>()
        return res
    }
    override suspend fun getTopRatedMovies(page:Int): MoviePaginationResult {
        val res =  client.get("/3/movie/top_rated?language=en-US&page=$page"){
//            header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ZTQzNTRhN2U3NzJjY2E3NmU1NTQzNTBlMzgyZGU2MSIsInN1YiI6IjY0MjU3ZmFlOTYwY2RlMDA3NzExYzc1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Jk5r97HkyY204CQKiMC0Hrxwr2ttrsk2urJP75B6r1c")
        }.body<MoviePaginationResult>()
        return res
    }
}