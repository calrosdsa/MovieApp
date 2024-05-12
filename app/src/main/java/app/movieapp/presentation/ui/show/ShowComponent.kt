package app.movieapp.presentation.ui.show

import app.movieapp.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface ShowComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindShowPresenterFactory(factory: ShowUiPresenterFactory): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindShowUiFactoryFactory(factory: ShowUiFactory): Ui.Factory = factory
}