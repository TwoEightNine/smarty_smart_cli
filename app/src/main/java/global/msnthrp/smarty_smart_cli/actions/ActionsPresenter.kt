package global.msnthrp.smarty_smart_cli.actions

import global.msnthrp.smarty_smart_cli.base.BasePresenter
import global.msnthrp.smarty_smart_cli.extensions.subscribeSmart
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.storage.Prefs

class ActionsPresenter(prefs: Prefs,
                       api: ApiService) : BasePresenter<ArrayList<Action>, ActionsContract.View>(prefs, api),
        ActionsContract.Presenter {

    override fun onTokenUpdated() {
        loadActions()
    }

    override fun loadActions(pullToRefresh: Boolean) {
        ifViewAttached { view ->
            view.showLoading(pullToRefresh)
            api.getActions()
                    .subscribeSmart({ actions ->
                        view.setData(actions)
                        view.showContent()
                    }, defaultError())
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