package global.msnthrp.smarty_smart_cli.utils

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.support.annotation.StringRes
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * Created by msnthrp on 22/01/18.
 */

const val NOTIFICATION = 1753

fun showToast(context: Context?, text: String = "", duration: Int = Toast.LENGTH_SHORT) {
    if (context == null) return

    Toast.makeText(context, text, duration).show()
}

fun showToast(context: Context?, @StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) {
    showToast(context, context?.getString(textId) ?: "", duration)
}

fun hideKeyboard(activity: Activity) {
    val view = activity.currentFocus
    if (view != null) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun showAlert(context: Context?, text: String) {
    if (context == null) return

    val dialog = AlertDialog.Builder(context)
            .setMessage(text)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    dialog.show()
}

fun showConfirm(context: Context?, text: String, callback: (Boolean) -> Unit) {
    if (context == null) return

    val dialog = AlertDialog.Builder(context)
            .setMessage(text)
            .setPositiveButton(android.R.string.ok) { _, _ -> callback.invoke(true) }
            .setNegativeButton(android.R.string.cancel) { _, _ -> callback.invoke(false) }
            .create()
    dialog.show()
}

fun showNotification(context: Context?,
                     content: String?,
                     title: String?,
                     icon: Bitmap) {
    if (context == null) return

    val intent = getRestartIntent(context)
    val pIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    val mBuilder = NotificationCompat.Builder(context)
            .setLargeIcon(icon)
            .setContentTitle(title)
            .setContentText(Html.fromHtml(content))
            .setContentIntent(pIntent)

    val mNotifyMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    mNotifyMgr.notify(NOTIFICATION, mBuilder.build())
}

fun closeNotification(context: Context?) {
    if (context == null) return

    val mNotifyMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    mNotifyMgr.cancel(NOTIFICATION)
}