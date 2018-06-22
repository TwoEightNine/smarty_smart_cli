package global.msnthrp.smarty_smart_cli.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
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
import global.msnthrp.smarty_smart_cli.utils.closeNotifications
import global.msnthrp.smarty_smart_cli.utils.showToast
import javax.inject.Inject

class MainActivity : BaseActivity<SwipeRefreshLayout, MainData, MainContract.View, MainPresenter>(),
        MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var injectedPresenter: MainPresenter

    private val rvActions: RecyclerView by view(R.id.rvActions)
    private val rvState: RecyclerView by view(R.id.rvState)

    private val actionsAdapter by lazy { ActionsAdapter(this, ::onActionClicked) }
    private val stateAdapter by lazy { StateAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actions)
        contentView.setOnRefreshListener(this)
        contentView.setColorSchemeResources(R.color.colorPrimary)
        initRecyclerViews()
        closeNotifications(this)
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
        actionsAdapter.update(data?.actions ?: return)
        stateAdapter.update(StateAdapter.stateToPairs(data.state))
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
        onRefresh()
    }

    override fun showContent() {
        super.showContent()
        contentView.isRefreshing = false
    }

    override fun onRefresh() {
        contentView.isRefreshing = true
        presenter.loadData(true)
    }

    private fun onActionClicked(action: Action) {
        if (action.action == "led") {
            chooseColor(action)
        } else {
            presenter.execute(action)
        }
    }

    private fun chooseColor(action: Action) {
        ColorPickerDialogBuilder.with(this)
                .setTitle(R.string.led_color)
                .initialColor(Color.WHITE)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .lightnessSliderOnly()
                .density(12)
                .setPositiveButton(R.string.ok) { _, color, _ ->
                    presenter.execute(action, arrayListOf(Integer.toHexString(color).substring(2)))
                }
                .setNegativeButton(R.string.cancel, null)
                .build()
                .show()
    }

    private fun initRecyclerViews() {
        rvActions.layoutManager = GridLayoutManager(this, COLUMNS)
        rvActions.adapter = actionsAdapter
        rvActions.isNestedScrollingEnabled = false

        rvState.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvState.adapter = stateAdapter
    }

    companion object {
        const val COLUMNS = 2
    }
}