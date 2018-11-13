package global.msnthrp.smarty_smart_cli.views

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import com.flask.colorpicker.ColorPickerView
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.extensions.view
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class ColorAlertDialog(context: Context,
                       currentColor: String,
                       private val onColorChanged: (String) -> Unit) : AlertDialog(context) {

    private val colorPicker: ColorPickerView by view(R.id.colorPicker)

    private var disposable: Disposable? = null

    init {
        val view = View.inflate(context, R.layout.dialog_color, null)
        setView(view)
        view.post {
            with(colorPicker) {
                setInitialColor(colorToInt(currentColor), false)
                addOnColorChangedListener(::onColorChanged)
            }
        }

    }

    private fun onColorChanged(color: Int) {
        disposable?.dispose()
        disposable = Observable.timer(DELAY, TimeUnit.MILLISECONDS)
                .subscribe { onColorChanged.invoke(colorToStr(color)) }
    }

    override fun dismiss() {
        disposable?.dispose()
        super.dismiss()
    }

    private fun colorToInt(color: String) = Integer.parseInt(color, 16) or (0xff shl 24)

    private fun colorToStr(color: Int) = Integer.toHexString(color).substring(2)

    companion object {
        const val DELAY = 200L
    }
}