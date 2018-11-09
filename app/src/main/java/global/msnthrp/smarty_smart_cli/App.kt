package global.msnthrp.smarty_smart_cli

import android.app.Application
import global.msnthrp.smarty_smart_cli.dagger.AppComponent
import global.msnthrp.smarty_smart_cli.dagger.DaggerAppComponent
import global.msnthrp.smarty_smart_cli.dagger.module.ContextModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}