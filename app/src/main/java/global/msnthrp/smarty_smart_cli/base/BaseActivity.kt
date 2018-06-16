package global.msnthrp.smarty_smart_cli.base

import android.view.View
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateActivity

abstract class BaseActivity<CV : View, M, V : MvpLceView<M>, P : BasePresenter<M, V>>
    : MvpLceViewStateActivity<CV, M, V, P>()