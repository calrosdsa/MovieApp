package app.movieapp.presentation.ui.discover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.SnapPositionInLayout
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import app.movieapp.data.model.dto.movies.MovieDto
import app.movieapp.presentation.components.discover.BackdropCard
import app.movieapp.presentation.components.util.AutoSizedCircularProgressIndicator
import app.movieapp.presentation.components.util.image.ParallaxAlignment
import app.movieapp.presentation.components.util.StartToStart
import app.movieapp.presentation.components.util.button.RefreshButton
import app.movieapp.presentation.screens.DiscoverScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject


@Inject
class DiscoverUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is DiscoverScreen -> {
            ui<DiscoverUiState> { state, modifier ->
                Discover(state, modifier)
            }
        }

        else -> null
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun Discover(
    state: DiscoverUiState,
    modifier: Modifier = Modifier,
) {
    val eventSink = state.eventSink
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Discover") },
                actions = {
                    RefreshButton(
                        showScrim = scrollBehavior.state.contentOffset > -4,
                        refreshing = state.refreshing,
                        onClick = {eventSink(DiscoverUiEvent.Refresh())},
                    )
                }, scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp),
            modifier = Modifier.padding(paddingValues)
        ) {

            item(key = "carousel_popular") {
                CarouselWithHeader(
                    items = state.popularMovies,
                    title = "Popular Movies",
                    refreshing = state.popularRefreshing,
                    onItemClick = { eventSink(DiscoverUiEvent.OpenShowDetails(it.id)) },
                    onMoreClick = {},
                    modifier = Modifier.animateItemPlacement(),
                )
            }

            item(key = "carousel_trending") {
                CarouselWithHeader(
                    items = state.topRatedMovies,
                    title = "Top Rated",
                    refreshing = state.topRatedRefreshing,
                    onItemClick = { eventSink(DiscoverUiEvent.OpenShowDetails(it.id)) },
                    onMoreClick = {},
                    modifier = Modifier.animateItemPlacement(),
                )
            }

            item(key = "carousel_upcoming") {
                CarouselWithHeader(
                    items = state.upcomingMovies,
                    title = "Upcoming",
                    refreshing = state.upcomingRefreshing,
                    onItemClick = { eventSink(DiscoverUiEvent.OpenShowDetails(it.id)) },
                    onMoreClick = {},
                    modifier = Modifier.animateItemPlacement(),
                )
            }

            item(key = "carousel_nowplaying") {
                CarouselWithHeader(
                    items = state.nowPlayingMovies,
                    title = "Now Playing",
                    refreshing = state.nowPlayingRefreshing,
                    onItemClick = { eventSink(DiscoverUiEvent.OpenShowDetails(it.id)) },
                    onMoreClick = {},
                    modifier = Modifier.animateItemPlacement(),
                )
            }

        }
    }
}




@Composable
private fun  CarouselWithHeader(
    items: List<MovieDto>,
    title: String,
    refreshing: Boolean,
    onItemClick: (MovieDto) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        if (refreshing || items.isNotEmpty()) {
            Spacer(Modifier.height(20.dp))

            Header(
                title = title,
                loading = refreshing,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                TextButton(
                    onClick = onMoreClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.secondary,
                    ),
                    modifier = Modifier.alignBy(FirstBaseline),
                ) {
                    Text(text = "Ver mas")
                }
            }
        }
        if (items.isNotEmpty()) {
            EntryShowCarousel(
                items = items,
                onItemClick = onItemClick,
                modifier = Modifier
                    .testTag("discover_carousel")
                    .fillMaxWidth(),
            )
        }
        // TODO empty state
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun  EntryShowCarousel(
    items: List<MovieDto>,
    onItemClick: (MovieDto) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    LazyRow(
        state = lazyListState,
        modifier = modifier
            .clip(MaterialTheme.shapes.extraLarge),
        flingBehavior = rememberSnapFlingBehavior(
            snapLayoutInfoProvider = remember(lazyListState) {
                SnapLayoutInfoProvider(
                    lazyListState = lazyListState,
                    positionInLayout = SnapPositionInLayout.StartToStart,
                )
            },
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = items,
            key = { it.id },
        ) { item ->
            BackdropCard(
                show = item,
                onClick = { onItemClick(item) },
                alignment = remember {
                    ParallaxAlignment(
                        horizontalBias = {
                            val layoutInfo = lazyListState.layoutInfo
                            val itemInfo = layoutInfo.visibleItemsInfo.first {
                                it.key == item.id
                            }

                            val adjustedOffset = itemInfo.offset - layoutInfo.viewportStartOffset
                            (adjustedOffset / itemInfo.size.toFloat()).coerceIn(-1f, 1f)
                        },
                    )
                },
                modifier = Modifier
                    .testTag("discover_carousel_item")
                    .animateItemPlacement()
                    .height(160.dp)
                    .width(220.dp)
//                    .aspectRatio(16 / 11f)
                ,
            )
        }
    }
}


@Composable
private fun Header(
    title: String,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    content: @Composable RowScope.() -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(Modifier.weight(1f))

        AnimatedVisibility(visible = loading) {
            AutoSizedCircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(16.dp),
            )
        }

        content()
    }
}