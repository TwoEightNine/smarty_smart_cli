package global.msnthrp.smarty_smart_cli.dagger

import dagger.Component
import global.msnthrp.smarty_smart_cli.dagger.module.ContextModule
import global.msnthrp.smarty_smart_cli.dagger.module.NetworkModule
import global.msnthrp.smarty_smart_cli.dagger.module.PresenterModule
import global.msnthrp.smarty_smart_cli.events.EventsActivity
import global.msnthrp.smarty_smart_cli.main.MainActivity
import global.msnthrp.smarty_smart_cli.utils.firebase.MyFirebaseInstanceIdService
import global.msnthrp.smarty_smart_cli.utils.firebase.MyFirebaseMessagingService
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, NetworkModule::class, PresenterModule::class])
interface AppComponent {

    fun inject(eventsActivity: EventsActivity)
    fun inject(actionsActivity: MainActivity)
    fun inject(myFirebaseInstanceIdService: MyFirebaseInstanceIdService)
    fun inject(myFirebaseMessagingService: MyFirebaseMessagingService)
}