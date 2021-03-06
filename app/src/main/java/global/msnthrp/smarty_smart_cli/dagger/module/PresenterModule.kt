package global.msnthrp.smarty_smart_cli.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import global.msnthrp.smarty_smart_cli.events.EventsPresenter
import global.msnthrp.smarty_smart_cli.main.MainPresenter
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.storage.Prefs
import javax.inject.Singleton

@Module
class PresenterModule {

    @Singleton
    @Provides
    fun provideEventsPresenter(prefs: Prefs, api: ApiService, context: Context) = EventsPresenter(prefs, api, context)

    @Singleton
    @Provides
    fun provideActionsPresenter(prefs: Prefs, api: ApiService, context: Context) = MainPresenter(prefs, api, context)
}