package global.msnthrp.smarty_smart_cli.extensions

import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.View

fun <T: View> AppCompatActivity.view(@IdRes viewId: Int) = lazy { findViewById<T>(viewId) }