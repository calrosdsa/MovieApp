package app.movieapp.data.model

import app.movieapp.data.model.dto.movies.MovieDto

data class ShowImageModel(
  val id: Long,
  val imageType: ImageType = ImageType.BACKDROP,
)

fun MovieDto.asImageModel(
  imageType: ImageType,
): ShowImageModel = ShowImageModel(id = id, imageType = imageType)
