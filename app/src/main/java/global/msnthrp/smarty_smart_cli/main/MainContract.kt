package global.msnthrp.smarty_smart_cli.main

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView
import global.msnthrp.smarty_smart_cli.main.features.Feature

interface MainContract {

    interface View : MvpLceView<List<Feature>> {
        fun onFeatureExecuted(feature: Feature)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData(pullToRefresh: Boolean = false)
        fun execute(feature: Feature, params: ArrayList<String> = arrayListOf())
    }
}