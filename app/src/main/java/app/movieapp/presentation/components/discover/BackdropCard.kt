package app.movieapp.presentation.components.discover

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.movieapp.data.model.dto.movies.MovieDto
import app.movieapp.presentation.components.util.image.AsyncImage
import app.movieapp.presentation.components.util.drawForegroundGradientScrim
import app.movieapp.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackdropCard(
  show: MovieDto,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  alignment: Alignment = Alignment.Center,
) {
  Card(
    onClick = onClick,
    shape = MaterialTheme.shapes.large,
    modifier = modifier,
  ) {
    MovieAppTheme() {
      BackdropCardContent(
        show = show,
        alignment = alignment,
      )
    }
  }
}

@Composable
private fun BackdropCardContent(
  show: MovieDto,
  alignment: Alignment = Alignment.Center,
) {
  Box(modifier = Modifier.fillMaxSize()) {
    AsyncImage(
      model = "https://image.tmdb.org/t/p/w500/${show.backdrop_path}",
      contentDescription = show.title ?: "show",
      modifier = Modifier.matchParentSize(),
      contentScale = ContentScale.Crop,
      alignment = alignment,
    )

    Spacer(
      Modifier
        .matchParentSize()
        .drawForegroundGradientScrim(MaterialTheme.colorScheme.surface),
    )

    Text(
      text = show.title ?: "No title",
      style = MaterialTheme.typography.labelLarge,
      color = MaterialTheme.colorScheme.onSurface,
      modifier = Modifier
        .padding(16.dp)
        .align(Alignment.BottomStart),
    )
  }
}