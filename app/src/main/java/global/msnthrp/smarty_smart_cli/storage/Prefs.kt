package global.msnthrp.smarty_smart_cli.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Prefs @Inject constructor(private val context: Context) {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    var token: String
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var ip: String
        get() = prefs.getString(IP, DEFAULT_IP)
        set(value) = prefs.edit().putString(IP, value).apply()

    companion object {
        const val NAME = "prefs"

        const val TOKEN = "token"
        const val IP = "ip"
        const val DEFAULT_IP = "0.0.0.0"
    }
}