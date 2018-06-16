package global.msnthrp.smarty_smart_cli.dagger

import dagger.Component
import global.msnthrp.smarty_smart_cli.dagger.module.ContextModule
import global.msnthrp.smarty_smart_cli.dagger.module.NetworkModule
import global.msnthrp.smarty_smart_cli.events.EventsActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ContextModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(eventsActivity: EventsActivity)
}