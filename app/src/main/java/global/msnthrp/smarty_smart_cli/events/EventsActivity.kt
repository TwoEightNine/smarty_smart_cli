package global.msnthrp.smarty_smart_cli.events

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.ParcelableListLceViewState
import global.msnthrp.smarty_smart_cli.App
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.base.BaseActivity
import global.msnthrp.smarty_smart_cli.extensions.view
import global.msnthrp.smarty_smart_cli.storage.Lg
import javax.inject.Inject

class EventsActivity : BaseActivity<SwipeRefreshLayout, ArrayList<Event>, EventsContract.View, EventsPresenter>(),
        EventsContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var injectedPresenter: EventsPresenter

    private val recyclerView: RecyclerView by view(R.id.recyclerView)
    private val tvLogs: TextView by view(R.id.tvLogs)

    private val adapter by lazy { EventsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        setTitle(R.string.events)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        contentView.setOnRefreshListener(this)
        contentView.setColorSchemeResources(R.color.colorPrimary)
        initRecyclerView()
    }

    override fun setData(data: ArrayList<Event>?) {
        Lg.i("events loaded: ${data?.size} events")
        adapter.update(data ?: return)
        tvLogs.text = Lg.logs.joinToString("\n")
    }

    override fun loadData(pullToRefresh: Boolean) = presenter.loadEvents(pullToRefresh)

    override fun createPresenter() = injectedPresenter

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
        recyclerView.isNestedScrollingEnabled = false
    }
}