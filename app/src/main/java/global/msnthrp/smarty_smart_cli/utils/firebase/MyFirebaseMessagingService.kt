package global.msnthrp.smarty_smart_cli.utils.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import global.msnthrp.smarty_smart_cli.storage.Lg
import global.msnthrp.smarty_smart_cli.utils.showNotification

class MyFirebaseMessagingService : FirebaseMessagingService() {

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
    }

    companion object {
        const val TITLE = "title"
        const val MESSAGE = "message"
    }
}