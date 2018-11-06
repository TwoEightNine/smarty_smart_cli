package global.msnthrp.smarty_smart_cli.main

import android.content.Context
import global.msnthrp.smarty_smart_cli.base.BasePresenter
import global.msnthrp.smarty_smart_cli.extensions.subscribeSmart
import global.msnthrp.smarty_smart_cli.main.features.Feature
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.storage.Lg
import global.msnthrp.smarty_smart_cli.storage.Prefs

class MainPresenter(prefs: Prefs,
                    api: ApiService,
                    context: Context) : BasePresenter<List<Feature>, MainContract.View>(prefs, api, context),
        MainContract.Presenter {

    var data: List<Feature>? = null
        private set

    override fun onTokenUpdated() {
        loadData()
    }

    override fun loadData(pullToRefresh: Boolean) {
        ifViewAttached { view ->
            view.showLoading(pullToRefresh)
            api.getFeatures()
                    .subscribeSmart({
                        data = it
                        view.showContent()
                        view.setData(it)
                    }, defaultError(pullToRefresh))
        }
    }

    override fun execute(feature: Feature, params: ArrayList<String>) {
        ifViewAttached { view ->
            if (feature.action == null) return@ifViewAttached
            view.showLoading(true)
            Lg.i("execute ${feature.action} with $params")
            api.execute(feature.action, mapify(feature, params))
                    .subscribeSmart({
                        view.showContent()
                        view.onFeatureExecuted(feature)
                    }, defaultError())
        }
    }

    private fun mapify(feature: Feature, params: ArrayList<String>): Map<String, String> {
        val result = hashMapOf<String, String>()
        if (params.size != feature.params?.size) return result

        for (pos in feature.params.indices) {
            result[feature.params[pos]] = params[pos]
        }
        return result
    }
}