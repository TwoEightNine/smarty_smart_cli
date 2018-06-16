package global.msnthrp.smarty_smart_cli.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

fun View.getVisible() = visibility == VISIBLE

fun View.toggleVisibility() {
    setVisible(!getVisible())
}