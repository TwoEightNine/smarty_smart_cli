package global.msnthrp.smarty_smart_cli.actions

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView

interface ActionsContract {

    interface View : MvpLceView<ArrayList<Action>> {
        fun onActionExecuted(action: Action)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadActions(pullToRefresh: Boolean = false)
        fun execute(action: Action, params: ArrayList<String> = arrayListOf())
    }
}