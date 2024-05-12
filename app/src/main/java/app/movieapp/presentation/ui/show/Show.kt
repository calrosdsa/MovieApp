package app.movieapp.presentation.ui.show

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import app.movieapp.data.model.dto.movieDetail.Genre
import app.movieapp.data.model.dto.movieDetail.MovieDetailDto
import app.movieapp.data.model.dto.movies.MovieDto
import app.movieapp.presentation.components.util.button.RefreshButton
import app.movieapp.presentation.components.util.button.ScrimmedIconButton
import app.movieapp.presentation.components.util.image.AsyncImage
import app.movieapp.presentation.components.util.image.Backdrop
import app.movieapp.presentation.components.util.text.ExpandingText
import app.movieapp.presentation.screens.DiscoverScreen
import app.movieapp.presentation.screens.ShowScreen
import app.movieapp.presentation.ui.discover.DiscoverUiEvent
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject


@Inject
class ShowUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is ShowScreen -> {
            ui<ShowUiState> { state, modifier ->
                Show(state, modifier)
            }
        }

        else -> null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Show(
    state: ShowUiState,
    modifier: Modifier = Modifier,
) {
    val eventSink =state.eventSink
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            ShowDetailsAppBar(
                title = null,
                isRefreshing = state.loading,
                onNavigateUp = { eventSink(ShowUiEvent.NavigateUp)},
                onRefresh = {eventSink(ShowUiEvent.Refresh())},
                scrollBehavior = scrollBehavior,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)) {paddingValues ->
        state.showDetail?.let { movie->

        LazyColumn(
//            contentPadding = ,
            modifier = Modifier.padding(paddingValues)
        ) {

            item(key = "backdrop") {
                Backdrop(
                    imageModel = movie.backdrop_path,
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 11),
                )
            }

            item {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.displaySmall,
                        letterSpacing = (-1).sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .fillMaxWidth(),
                    )
            }

            item(key = "poster_info") {
                PosterInfoRow(
                    show = movie,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            item(key = "header_summary") {
                Header("About show")
            }

            item(key = "summary") {
                ExpandingText(
                    text = movie.overview,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                )
            }

            item(key = "genres") {
                Genres(movie.genres)
            }


        }
    }
}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Genres(genres: List<Genre>) {

    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            genres.forEach { genre ->
                Card {
                    Text(
                        text = genre.name,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}


@Composable
private fun PosterInfoRow(
    show: MovieDetailDto,
    modifier: Modifier = Modifier,
) {
    Row(modifier.padding(horizontal = 10.dp)) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${show.poster_path}",
            contentDescription = "image",
            modifier = Modifier
                .height(180.dp)
                .width(140.dp)
                .clip(MaterialTheme.shapes.medium),
            alignment = Alignment.TopStart,
        )

        Spacer(modifier = Modifier.width(15.dp))

        InfoPanels(
            show = show,
            modifier = Modifier.weight(1f),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InfoPanels(
    show: MovieDetailDto,
    modifier: Modifier = Modifier,
) {
    val itemMod = Modifier.padding(bottom = 10.dp)
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier,
    ) {

            TraktRatingInfoPanel(
                rating = show.vote_average.toFloat(),
                votes = show.vote_count,
                modifier = itemMod,
            )
//            InfoPanel(
//                title = "Revenue",
//                value = show.revenue.toString(),
//                modifier = itemMod,
//            )
//        InfoPanel(
//            title = "Budget",
//            value = show.budget .toString(),
//            modifier = itemMod,
//        )
        InfoPanel(
            title = "Tag line",
            value = show.tagline,
            modifier = itemMod,
        )
            ShowStatusPanel(showStatus = show.status, modifier = itemMod)
//        if (show.certification != null) {
//            CertificateInfoPanel(certification = show.certification!!, modifier = itemMod)
//        }
//        if (show.runtime != null) {
//            RuntimeInfoPanel(runtime = show.runtime, modifier = itemMod)
//        }
//        if (show.airsDay != null && show.airsTime != null && show.airsTimeZone != null &&
//            (show.status == ShowStatus.IN_PRODUCTION || show.status == ShowStatus.RETURNING)
//        ) {
//            AirsInfoPanel(show = show, modifier = itemMod)
//        }
    }
}

@Composable
private fun RuntimeInfoPanel(
    runtime: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = "Length",
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = runtime.toString(),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


@Composable
private fun ShowStatusPanel(
    showStatus: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = "Status",
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = showStatus,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


@Composable
private fun InfoPanel(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun TraktRatingInfoPanel(
    rating: Float,
    votes: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = "Trakt Rating",
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                modifier = Modifier.size(32.dp),
            )

            Spacer(Modifier.width(4.dp))

            Column {
                Text(
                    text = (rating * 10f).toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    text = "${(votes / 1000f)}.1fk votes",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDetailsAppBar(
    title: String?,
    isRefreshing: Boolean,
    onNavigateUp: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = {
            if (title != null) {
                Text(text = title)
            }
        },
        navigationIcon = {
            ScrimmedIconButton(
                showScrim = scrollBehavior.state.contentOffset > -4,
                onClick = onNavigateUp,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                )
            }
        },
        actions = {
            RefreshButton(
                showScrim = scrollBehavior.state.contentOffset > -4,
                refreshing = isRefreshing,
                onClick = onRefresh,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

