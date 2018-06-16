package global.msnthrp.smarty_smart_cli.events

import global.msnthrp.smarty_smart_cli.base.BasePresenter
import global.msnthrp.smarty_smart_cli.extensions.subscribeSmart
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.storage.Prefs

class EventsPresenter(prefs: Prefs,
                      api: ApiService)
    : BasePresenter<ArrayList<Event>, EventsContract.View>(prefs, api), EventsContract.Presenter {

    override fun loadEvents(pullToRefresh: Boolean) {
        ifViewAttached { view ->
            view.showLoading(pullToRefresh)
            api.getEvents()
                    .subscribeSmart({ events ->
                        view.setData(events)
                        view.showContent()
                    }, defaultError(pullToRefresh))
        }
    }

    override fun onTokenUpdated() {
        loadEvents()
    }
}