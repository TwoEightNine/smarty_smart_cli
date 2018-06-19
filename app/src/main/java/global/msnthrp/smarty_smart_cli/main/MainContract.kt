package global.msnthrp.smarty_smart_cli.main

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView
import global.msnthrp.smarty_smart_cli.main.actions.Action

interface MainContract {

    interface View : MvpLceView<MainData> {
        fun onActionExecuted(action: Action)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData(pullToRefresh: Boolean = false)
        fun execute(action: Action, params: ArrayList<String> = arrayListOf())
    }
}