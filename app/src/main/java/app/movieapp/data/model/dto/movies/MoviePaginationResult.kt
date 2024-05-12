package app.movieapp.data.model.dto.movies

import kotlinx.serialization.Serializable

@Serializable
data class MoviePaginationResult(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)