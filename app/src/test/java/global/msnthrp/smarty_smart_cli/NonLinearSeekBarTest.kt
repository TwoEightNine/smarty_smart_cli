package global.msnthrp.smarty_smart_cli

import global.msnthrp.smarty_smart_cli.views.NonLinearSeekBar
import org.junit.Test
import org.junit.Assert.*
import java.util.*

class NonLinearSeekBarTest {

    @Test
    fun polynomialUsual_isCorrect() {
        val poly = NonLinearSeekBar.Polynomial(
                NonLinearSeekBar.X_AVG,
                NonLinearSeekBar.X_MAX,
                NonLinearSeekBar.Y_AVG,
                NonLinearSeekBar.Y_MAX
        )
        for (x in 0..NonLinearSeekBar.X_MAX.toInt()) {
            assertEquals(x, poly.fInv(poly.f(x)).toInt())
        }
    }

}