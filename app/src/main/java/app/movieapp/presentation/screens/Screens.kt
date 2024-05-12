package app.movieapp.presentation.screens

import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
object DiscoverScreen : AppScreen(name = "DiscoverUi()")

@Parcelize
data class ShowScreen(val id: Long) : AppScreen(name = "ShowDetails()") {
    override val arguments get() = mapOf("id" to id)
}

abstract class AppScreen(val name: String) : Screen {
    open val arguments: Map<String, *>? = null
}
