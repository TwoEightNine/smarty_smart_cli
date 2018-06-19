package global.msnthrp.smarty_smart_cli.base

import android.view.MenuItem
import android.view.View
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateActivity

abstract class BaseActivity<CV : View, M, V : MvpLceView<M>, P : BasePresenter<M, V>>
    : MvpLceViewStateActivity<CV, M, V, P>() {

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
}