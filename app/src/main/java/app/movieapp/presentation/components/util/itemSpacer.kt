
package app.movieapp.presentation.components.util

import android.text.Layout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapPositionInLayout
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

inline fun LazyListScope.itemSpacer(height: Dp) {
  item {
    Spacer(
      Modifier
        .height(height)
        .fillParentMaxWidth(),
    )
  }
}

inline fun LazyListScope.gutterSpacer() {
  item {
    Spacer(
      Modifier
        .height(10.dp)
        .fillParentMaxWidth(),
    )
  }
}

inline fun LazyGridScope.gutterSpacer() {
  fullSpanItem {
    Spacer(
      Modifier
        .height(10.dp)
        .fillMaxWidth(),
    )
  }
}

inline fun LazyGridScope.itemSpacer(height: Dp) {
  fullSpanItem {
    Spacer(
      Modifier
        .height(height)
        .fillMaxWidth(),
    )
  }
}

inline fun LazyGridScope.fullSpanItem(
  key: Any? = null,
  contentType: Any? = null,
  noinline content: @Composable LazyGridItemScope.() -> Unit,
) {
  item(
    key = key,
    span = { GridItemSpan(maxLineSpan) },
    contentType = contentType,
    content = content,
  )
}

inline fun LazyStaggeredGridScope.fullSpanItem(
  key: Any? = null,
  contentType: Any? = null,
  noinline content: @Composable LazyStaggeredGridItemScope.() -> Unit,
) {
  item(
    key = key,
    span = StaggeredGridItemSpan.FullLine,
    contentType = contentType,
    content = content,
  )
}

@ExperimentalFoundationApi
val SnapPositionInLayout.Companion.StartToStart: SnapPositionInLayout
  get() = SnapPositionInLayout { _, _, _, _, _ -> 0 }
