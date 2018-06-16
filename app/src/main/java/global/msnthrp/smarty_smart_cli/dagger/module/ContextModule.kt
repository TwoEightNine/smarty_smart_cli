package global.msnthrp.smarty_smart_cli.dagger.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import global.msnthrp.smarty_smart_cli.storage.Prefs
import javax.inject.Singleton


@Module
class ContextModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun providePrefs(context: Context): Prefs = Prefs(context)


}