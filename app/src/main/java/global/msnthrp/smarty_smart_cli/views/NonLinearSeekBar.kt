package global.msnthrp.smarty_smart_cli.views

import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar

class NonLinearSeekBar : SeekBar {

    companion object {
        const val OUT_MAX = 1f

        const val X_AVG = 8
        const val Y_AVG = .25f

        const val X_MAX = 16
        const val Y_MAX = 1f

    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val a = (Y_AVG * X_MAX - Y_MAX * X_AVG) / (X_AVG * X_MAX * (X_AVG - X_MAX))
    private val b = Y_MAX / X_MAX - a * X_MAX

    private val aInv = (X_AVG * Y_MAX - X_MAX * Y_AVG) / (Y_AVG * Y_MAX * (Y_AVG - Y_MAX))
    private val bInv = X_MAX / Y_MAX - aInv * Y_MAX

    var progress: Float = OUT_MAX
        get() = f(getProgress())
        set(value) {
            setProgress(fInv(value).toInt())
            field = value
        }

    private fun f(x: Int) = a * x * x + b * x

    private fun fInv(y: Float) = aInv * y * y + bInv * y
}