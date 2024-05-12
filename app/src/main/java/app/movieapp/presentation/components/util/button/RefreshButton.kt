package app.movieapp.presentation.components.util.button

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.movieapp.presentation.components.util.AutoSizedCircularProgressIndicator

@Composable
fun RefreshButton(
  refreshing: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  showScrim: Boolean = false,
) {
  ScrimmedIconButton(
    showScrim = showScrim,
    onClick = onClick,
    enabled = !refreshing,
    modifier = modifier,
  ) {
    Crossfade(refreshing) { targetRefreshing ->
      if (targetRefreshing) {
        AutoSizedCircularProgressIndicator(
          modifier = Modifier
            .size(20.dp)
            .padding(2.dp),
        )
      } else {
        Icon(
          imageVector = Icons.Default.Refresh,
          contentDescription = "refresh",
        )
      }
    }
  }
}

@Composable
fun RefreshButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  IconButton(
    onClick = onClick,
    modifier = modifier,
  ) {
    Icon(
      imageVector = Icons.Default.Refresh,
      contentDescription = "refresh",
    )
  }
}
