package app.movieapp.presentation.ui.discover

import app.movieapp.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface DiscoverComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindDiscoverPresenterFactory(factory: DiscoverUiPresenterFactory): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindDiscoverUiFactoryFactory(factory: DiscoverUiFactory): Ui.Factory = factory
}