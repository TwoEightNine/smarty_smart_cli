package global.msnthrp.smarty_smart_cli.actions

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
import global.msnthrp.smarty_smart_cli.utils.showToast
import javax.inject.Inject

class ActionsActivity : BaseActivity<SwipeRefreshLayout, ArrayList<Action>, ActionsContract.View, ActionsPresenter>(),
        ActionsContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var injectedPresenter: ActionsPresenter

    private val recyclerView: RecyclerView by view(R.id.recyclerView)
    private val adapter by lazy { ActionsAdapter(this, ::onActionClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actions)
        contentView.setOnRefreshListener(this)
        contentView.setColorSchemeResources(R.color.colorPrimary)
        initRecyclerView()
    }

    override fun setData(data: ArrayList<Action>?) {
        adapter.update(data ?: return)
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.loadActions(pullToRefresh)
    }

    override fun createPresenter() = injectedPresenter

    override fun createViewState(): LceViewState<ArrayList<Action>, ActionsContract.View> {
        return ParcelableListLceViewState<ArrayList<Action>, ActionsContract.View>()
    }

    override fun getData() = adapter.items

    override fun getErrorMessage(e: Throwable?, pullToRefresh: Boolean) = e?.message

    override fun onActionExecuted(action: Action) {
        showToast(this, "Executed ${action.name}")
    }

    override fun showContent() {
        super.showContent()
        contentView.isRefreshing = false
    }

    override fun onRefresh() {
        presenter.loadActions(true)
    }

    private fun onActionClicked(action: Action) {
        val params = arrayListOf<String>()
        if (action.action == "led") {
            params.add("123456")
        }
        presenter.execute(action, params)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}