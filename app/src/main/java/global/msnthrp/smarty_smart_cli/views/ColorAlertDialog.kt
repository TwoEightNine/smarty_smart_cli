package global.msnthrp.smarty_smart_cli.views

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.Utils
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.extensions.view
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class ColorAlertDialog(context: Context,
                       currentColor: String,
                       private val onColorChanged: (String) -> Unit) : AlertDialog(context) {

    private val colorPicker: ColorPickerView by view(R.id.colorPicker)
    private val tvBlack: TextView by view(R.id.tvBlack)
    private val seekBar: NonLinearSeekBar by view(R.id.seekBar)

    private var disposable: Disposable? = null

    init {
        val view = View.inflate(context, R.layout.dialog_color, null)
        setView(view)
        view.post {
            setInitialColor(currentColor)
            colorPicker.addOnColorChangedListener { onColorChanged() }
            tvBlack.setOnClickListener { turnOff() }
            seekBar.setOnSeekBarChangeListener(BrightnessListener())
        }
    }

    private fun onColorChanged() {
        var color = colorPicker.selectedColor
        val brightness = seekBar.progress
        color = Utils.colorAtLightness(color, brightness)
        disposable?.dispose()
        disposable = Observable.timer(DELAY, TimeUnit.MILLISECONDS)
                .subscribe { onColorChanged.invoke(colorToStr(color)) }
    }

    private fun turnOff() {
        disposable?.dispose()
        onColorChanged.invoke(colorToStr(Color.BLACK))
        dismiss()
    }

    override fun dismiss() {
        disposable?.dispose()
        super.dismiss()
    }

    private fun colorToInt(color: String) = Integer.parseInt(color, 16) or (0xff shl 24)

    private fun colorToStr(color: Int) = Integer.toHexString(color).substring(2)

    private fun setInitialColor(color: String) {
        var intColor = colorToInt(color)
        if (intColor == Color.BLACK) intColor = Color.WHITE
        val brightness = Utils.lightnessOfColor(intColor)
        intColor = Utils.colorAtLightness(intColor, 1f)
        colorPicker.setInitialColor(intColor, false)
        seekBar.progress = brightness
    }

    private inner class BrightnessListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onColorChanged()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    companion object {
        const val BRIGHTNESS_MAX = 16
        const val DELAY = 200L
    }
}