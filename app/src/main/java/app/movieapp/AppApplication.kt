package app.movieapp

import android.app.Application
import app.movieapp.domain.ext.unsafeLazy
import app.movieapp.inject.AndroidApplicationComponent
import app.movieapp.inject.create

class AppApplication:Application() {
    val component: AndroidApplicationComponent by unsafeLazy { AndroidApplicationComponent::class.create(this) }

}