package global.msnthrp.smarty_smart_cli.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.ParcelableDataLceViewState
import global.msnthrp.smarty_smart_cli.App
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.base.BaseActivity
import global.msnthrp.smarty_smart_cli.events.EventsActivity
import global.msnthrp.smarty_smart_cli.extensions.view
import global.msnthrp.smarty_smart_cli.main.actions.Action
import global.msnthrp.smarty_smart_cli.main.actions.ActionsAdapter
import global.msnthrp.smarty_smart_cli.main.state.StateAdapter
import global.msnthrp.smarty_smart_cli.storage.Lg
import global.msnthrp.smarty_smart_cli.utils.isLandscape
import global.msnthrp.smarty_smart_cli.utils.showToast
import javax.inject.Inject

class MainActivity : BaseActivity<SwipeRefreshLayout, MainData, MainContract.View, MainPresenter>(),
        MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var injectedPresenter: MainPresenter

    private val rvActions: RecyclerView by view(R.id.rvActions)
    private val rvState: RecyclerView by view(R.id.rvState)
    private val nestedScrollView: NestedScrollView by view(R.id.nestedScrollView)

    private val actionsAdapter by lazy { ActionsAdapter(this, ::onActionClicked) }
    private val stateAdapter by lazy { StateAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actions)
        contentView.setOnRefreshListener(this)
        contentView.setColorSchemeResources(R.color.colorPrimary)
        initRecyclerViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menuEvents -> {
                startActivity(Intent(this, EventsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setData(data: MainData?) {
        Lg.i("loaded actions and state: ${data?.actions?.size} actions")
        nestedScrollView.isSmoothScrollingEnabled = true
        actionsAdapter.update(data?.actions ?: return)
        stateAdapter.update(StateAdapter.stateToPairs(data.state))
        nestedScrollView.isSmoothScrollingEnabled = false
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.loadData(pullToRefresh)
    }

    override fun createPresenter() = injectedPresenter

    override fun createViewState(): LceViewState<MainData, MainContract.View> {
        return ParcelableDataLceViewState<MainData, MainContract.View>()
    }

    override fun getData() = presenter.data

    override fun getErrorMessage(e: Throwable?, pullToRefresh: Boolean) = e?.message

    override fun onActionExecuted(action: Action) {
        showToast(this, "${action.name} executed")
        Lg.i("${action.name} executed")
    }

    override fun showContent() {
        super.showContent()
        contentView.isRefreshing = false
    }

    override fun onRefresh() {
        presenter.loadData(true)
    }

    private fun onActionClicked(action: Action) {
        val params = arrayListOf<String>()
        if (action.action == "led") {
            params.add("123456")
        }
        presenter.execute(action, params)
    }

    private fun initRecyclerViews() {
        rvActions.layoutManager = GridLayoutManager(this, COLUMNS)
        rvActions.adapter = actionsAdapter

        rvState.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvState.adapter = stateAdapter
    }

    companion object {
        const val COLUMNS = 2
    }
}