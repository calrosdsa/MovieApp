package app.movieapp.data.model.dto.movieDetail

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String
)