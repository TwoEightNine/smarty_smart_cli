package global.msnthrp.smarty_smart_cli.events

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.ParcelableListLceViewState
import global.msnthrp.smarty_smart_cli.App
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.base.BaseActivity
import global.msnthrp.smarty_smart_cli.extensions.view
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.storage.Prefs
import javax.inject.Inject

class EventsActivity : BaseActivity<SwipeRefreshLayout, ArrayList<Event>, EventsContract.View, EventsPresenter>(),
        EventsContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var pref: Prefs
    @Inject
    lateinit var api: ApiService

    private val recyclerView: RecyclerView by view(R.id.recyclerView)
    private val adapter by lazy { EventsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        contentView.setOnRefreshListener(this)
        initRecyclerView()
    }

    override fun setData(data: ArrayList<Event>?) {
        adapter.update(data ?: return)
    }

    override fun loadData(pullToRefresh: Boolean) = presenter.loadEvents(pullToRefresh)

    override fun createPresenter() = EventsPresenter(pref, api)

    override fun createViewState(): LceViewState<ArrayList<Event>, EventsContract.View> {
        return ParcelableListLceViewState<ArrayList<Event>, EventsContract.View>()
    }

    override fun getData(): ArrayList<Event> = adapter.items

    override fun getErrorMessage(e: Throwable?, pullToRefresh: Boolean) = e?.message

    override fun onRefresh() = loadData(true)

    override fun showContent() {
        super.showContent()
        contentView.isRefreshing = false
    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}