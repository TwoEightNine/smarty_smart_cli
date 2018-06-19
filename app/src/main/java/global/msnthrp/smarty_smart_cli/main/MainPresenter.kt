package global.msnthrp.smarty_smart_cli.main

import global.msnthrp.smarty_smart_cli.base.BasePresenter
import global.msnthrp.smarty_smart_cli.extensions.subscribeSmart
import global.msnthrp.smarty_smart_cli.main.actions.Action
import global.msnthrp.smarty_smart_cli.main.state.State
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.network.model.BaseResponse
import global.msnthrp.smarty_smart_cli.storage.Prefs
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class MainPresenter(prefs: Prefs,
                    api: ApiService) : BasePresenter<MainData, MainContract.View>(prefs, api),
        MainContract.Presenter {

    override fun onTokenUpdated() {
        loadData()
    }

    override fun loadData(pullToRefresh: Boolean) {
        ifViewAttached { view ->
            view.showLoading(pullToRefresh)
            val actions = api.getActions()
            val state = api.getState()
            Single.zip(actions, state, BiFunction {
                a: BaseResponse<ArrayList<Action>>,
                s: BaseResponse<State> ->
                combineResponses(a, s) {
                    aRes, sRes ->
                    if (aRes == null || sRes == null) {
                        null
                    } else {
                        MainData(sRes, aRes)
                    }
                }
            }).subscribeSmart({
                view.showContent()
                view.setData(it)
            }, defaultError(pullToRefresh))
        }
    }

    override fun execute(action: Action, params: ArrayList<String>) {
        ifViewAttached { view ->
            view.showLoading(true)
            api.execute(action.action, mapify(action, params))
                    .subscribeSmart({
                        view.showContent()
                        view.onActionExecuted(action)
                    }, defaultError())
        }
    }

    private fun mapify(action: Action, params: ArrayList<String>): Map<String, String> {
        val result = hashMapOf<String, String>()
        if (params.size != action.params.size) return result

        for (pos in action.params.indices) {
            result[action.params[pos]] = params[pos]
        }
        return result
    }
}