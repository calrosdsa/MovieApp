package app.movieapp.data.repository.show

import app.movieapp.data.model.dto.movieDetail.MovieDetailDto

interface ShowDataSource {
    suspend fun getShow(id:Long): MovieDetailDto
}