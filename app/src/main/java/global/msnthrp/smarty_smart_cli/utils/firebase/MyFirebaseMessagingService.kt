package global.msnthrp.smarty_smart_cli.utils.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import global.msnthrp.smarty_smart_cli.App
import global.msnthrp.smarty_smart_cli.storage.Lg
import global.msnthrp.smarty_smart_cli.storage.Prefs
import global.msnthrp.smarty_smart_cli.utils.restartApp
import global.msnthrp.smarty_smart_cli.utils.showNotification
import javax.inject.Inject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var prefs: Prefs

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Lg.i("remote message = $remoteMessage")
        if (remoteMessage == null) return

        handleMessage(remoteMessage)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {

        val title = remoteMessage.data[TITLE]
        val message = remoteMessage.data[MESSAGE]
        showNotification(applicationContext, title, message, remoteMessage.sentTime)

        if (IP in remoteMessage.data) {
            val ip = remoteMessage.data[IP]
            if (ip != null) {
                processNewIp(ip)
            }
        }
    }

    private fun processNewIp(ip: String) {
        App.appComponent.inject(this)
        prefs.ip = ip
        restartApp(applicationContext)
    }

    companion object {
        const val TITLE = "title"
        const val MESSAGE = "message"
        const val IP = "ip"
    }
}