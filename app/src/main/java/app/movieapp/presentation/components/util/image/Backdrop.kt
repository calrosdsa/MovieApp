package app.movieapp.presentation.components.util.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.movieapp.presentation.components.util.drawForegroundGradientScrim

@Composable
fun Backdrop(
  imageModel: Any?,
  modifier: Modifier = Modifier,
  shape: Shape = MaterialTheme.shapes.medium,
  overline: (@Composable () -> Unit)? = null,
  title: (@Composable () -> Unit)? = null,
) {
  Surface(
    color = MaterialTheme.colorScheme.onSurface
      .copy(alpha = 0.2f)
      .compositeOver(MaterialTheme.colorScheme.surface),
    contentColor = MaterialTheme.colorScheme.onSurface,
    shape = shape,
    modifier = modifier,
  ) {
    Box {
      if (imageModel != null) {
//        val strings = LocalStrings.current

        AsyncImage(
          model =  "https://image.tmdb.org/t/p/w500/$imageModel",
          contentDescription = "poster",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .fillMaxSize()
            .let { mod ->
              if (title != null) {
                mod.drawForegroundGradientScrim(
                  MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
                )
              } else {
                mod
              }
            },
        )
      }

      Column(
        Modifier
          .align(Alignment.BottomStart)
          .padding(10.dp),
      ) {
        if (overline != null) {
          CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.labelSmall,
          ) {
            overline()
          }
        }
        if (title != null) {
          CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.headlineSmall,
          ) {
            title()
          }
        }
      }
    }
  }
}
