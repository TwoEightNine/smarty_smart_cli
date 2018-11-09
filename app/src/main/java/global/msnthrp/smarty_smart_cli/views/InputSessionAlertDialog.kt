package global.msnthrp.smarty_smart_cli.views

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import global.msnthrp.smarty_smart_cli.App
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.extensions.view
import global.msnthrp.smarty_smart_cli.storage.Prefs
import global.msnthrp.smarty_smart_cli.utils.showAlert
import javax.inject.Inject

class InputSessionAlertDialog(context: Context,
                              listener: ((String) -> Unit)? = null) : AlertDialog(context) {

    @Inject
    lateinit var prefs: Prefs

    private val etIp: EditText by view(R.id.etIp)

    init {
        App.appComponent.inject(this)
        val view = View.inflate(context, R.layout.dialog_input, null)
        setView(view)
        setTitle(R.string.ip_title)
        view.post { etIp.setText(prefs.ip) }
        setButton(
                DialogInterface.BUTTON_POSITIVE,
                context.getString(android.R.string.ok)
        ) { d, i ->
            val ip = etIp.text.toString()
            if (ip.matches(REGEX_IP)) {
                listener?.invoke(ip)
            } else {
                showAlert(context, context.getString(R.string.invalid_ip))
            }
            dismiss()
        }
    }

    companion object {
        val REGEX_IP = Regex("^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$")
    }
}
