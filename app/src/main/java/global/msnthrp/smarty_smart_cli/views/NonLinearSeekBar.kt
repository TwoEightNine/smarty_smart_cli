package global.msnthrp.smarty_smart_cli.views

import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import kotlin.math.sqrt

class NonLinearSeekBar : SeekBar {

    companion object {
        const val OUT_MAX = 1f

        const val X_AVG = 8f
        const val Y_AVG = .25f

        const val X_MAX = 16f
        const val Y_MAX = 1f
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val poly = Polynomial(X_AVG, X_MAX, Y_AVG, Y_MAX)

    var progress: Float = OUT_MAX
        get() = poly.f(getProgress())
        set(value) {
            setProgress(poly.fInv(value).toInt())
            field = value
        }

    class Polynomial(
            xAvg: Float,
            xMax: Float,
            yAvg: Float,
            yMax: Float
    ) {

        private val a = (yAvg * xMax - yMax * xAvg) / (xAvg * xMax * (xAvg - xMax))
        private val b = yMax / xMax - a * xMax

        fun f(x: Int) = a * x * x + b * x

        fun fInv(y: Float) = (sqrt(b * b + 4 * a * y) - b) / (2 * a)
    }
}