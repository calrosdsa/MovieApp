package app.movieapp.data.repository.show

import app.movieapp.data.model.dto.movieDetail.MovieDetailDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import me.tatarka.inject.annotations.Inject

@Inject
class ShowDataSourceImpl(
    private val client: HttpClient,
):ShowDataSource {

    override suspend fun getShow(id:Long): MovieDetailDto {
        val res =  client.get("/3/movie/${id}").body<MovieDetailDto>()
        return res
    }
}