package global.msnthrp.smarty_smart_cli.utils.firebase

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import global.msnthrp.smarty_smart_cli.App
import global.msnthrp.smarty_smart_cli.extensions.subscribeSmart
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.storage.Lg
import javax.inject.Inject


class MyFirebaseInstanceIdService : FirebaseInstanceIdService() {

    @Inject
    lateinit var api: ApiService

    override fun onTokenRefresh() {
        App.appComponent.inject(this)
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Lg.i("refreshed token: $refreshedToken")
        if (refreshedToken == null) return
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        api.registerToken(refreshedToken)
                .subscribeSmart({
                    Lg.i("registered successfully")
                }, { code, error ->
                    Lg.i("registering returned $code: $error")
                })
    }

}