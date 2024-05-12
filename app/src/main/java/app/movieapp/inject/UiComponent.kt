package app.movieapp.inject

import app.movieapp.presentation.RootUiComponent
import app.movieapp.presentation.ui.discover.DiscoverComponent
import app.movieapp.presentation.ui.show.ShowComponent
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.Provides

interface UiComponent:
    ShowComponent,
    DiscoverComponent,
    RootUiComponent
{

//    @Provides
//    @ActivityScope
//    fun provideLyricist(): TiviStrings = Lyricist(
//        defaultLanguageTag = Locales.EN,
//        translations = Strings,
//    ).strings

    @Provides
    @ActivityScope
    fun provideCircuit(
        uiFactories: Set<Ui.Factory>,
        presenterFactories: Set<Presenter.Factory>,
    ): Circuit = Circuit.Builder()
        .addUiFactories(uiFactories)
        .addPresenterFactories(presenterFactories)
        .build()
}