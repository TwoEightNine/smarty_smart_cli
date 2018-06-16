package global.msnthrp.smarty_smart_cli.storage

import android.util.Log
import global.msnthrp.smarty_smart_cli.utils.getTime
import global.msnthrp.smarty_smart_cli.utils.time

object Lg {

    private const val APP_TAG = "smarty"
    private const val ALLOWED_LEN = 300
    val logs = arrayListOf<String>()

    fun i(s: String) {
        Log.i(APP_TAG, s)
        logs.add("[${getTime(time())}] $s")
        truncate()
    }

    fun wtf(s: String) {
        Log.wtf(APP_TAG, s)
        logs.add("!![${getTime(time())}]!! $s")
        truncate()
    }

    private fun truncate() {
        while (logs.size > ALLOWED_LEN) {
            logs.removeAt(0)
        }
    }

}