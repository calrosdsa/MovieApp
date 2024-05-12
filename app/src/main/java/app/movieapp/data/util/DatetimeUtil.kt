package app.movieapp.data.util

import kotlin.time.Duration
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

inline val Duration.inPast: Instant
    get() = Clock.System.now() - this

fun Instant.durationSinceNow(): Duration = Clock.System.now() - this
