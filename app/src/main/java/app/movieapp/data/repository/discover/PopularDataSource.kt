package app.movieapp.data.repository.discover

import app.movieapp.data.model.dto.movies.MoviePaginationResult
import app.moviebase.tmdb.model.TmdbMoviePageResult
import app.moviebase.tmdb.model.TmdbShowDetail

interface PopularDataSource {
    suspend fun getPopularMovies(page:Int):MoviePaginationResult
    suspend fun getNowPlayingMovies(page:Int):MoviePaginationResult
    suspend fun getTopRatedMovies(page:Int):MoviePaginationResult
    suspend fun getUpcomingMovies(page:Int):MoviePaginationResult

}