package global.msnthrp.smarty_smart_cli.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.ParcelableListLceViewState
import global.msnthrp.smarty_smart_cli.App
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.base.BaseActivity
import global.msnthrp.smarty_smart_cli.events.EventsActivity
import global.msnthrp.smarty_smart_cli.extensions.view
import global.msnthrp.smarty_smart_cli.main.features.Feature
import global.msnthrp.smarty_smart_cli.main.features.FeaturesAdapter
import global.msnthrp.smarty_smart_cli.storage.Lg
import global.msnthrp.smarty_smart_cli.storage.Prefs
import global.msnthrp.smarty_smart_cli.utils.closeNotifications
import global.msnthrp.smarty_smart_cli.utils.restartApp
import global.msnthrp.smarty_smart_cli.utils.showToast
import global.msnthrp.smarty_smart_cli.views.ColorAlertDialog
import global.msnthrp.smarty_smart_cli.views.InputSessionAlertDialog
import javax.inject.Inject

class MainActivity : BaseActivity<SwipeRefreshLayout, List<Feature>, MainContract.View, MainPresenter>(),
        MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var injectedPresenter: MainPresenter

    @Inject
    lateinit var prefs: Prefs

    private val rvFeatures: RecyclerView by view(R.id.rvFeatures)

    private val adapter: FeaturesAdapter by lazy { FeaturesAdapter(this, ::onClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            R.id.menuNetwork -> {
                InputSessionAlertDialog(this, ::onIpChanged).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setData(data: List<Feature>?) {
        Lg.i("loaded features: ${data?.size} actions")
        adapter.update(data ?: return)
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.loadData(pullToRefresh)
    }

    override fun createPresenter() = injectedPresenter

    override fun createViewState(): LceViewState<List<Feature>, MainContract.View> {
        return ParcelableListLceViewState<List<Feature>, MainContract.View>()
    }

    override fun getData() = presenter.data

    override fun getErrorMessage(e: Throwable?, pullToRefresh: Boolean) = e?.message

    override fun onFeatureExecuted(feature: Feature) {
        Lg.i("${feature.name} sent as a request")
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

    private fun onClick(feature: Feature) {
        if (feature.action == null) return

        when(feature.action) {
            Feature.ACTION_RGB -> chooseColor(feature)
            else -> presenter.execute(feature)
        }

    }

    private fun chooseColor(feature: Feature) {
        ColorAlertDialog(this, feature.value) {
            presenter.execute(feature, arrayListOf(it))
        }.show()
    }

    private fun initRecyclerViews() {
        rvFeatures.layoutManager = GridLayoutManager(this, COLUMNS)
        rvFeatures.adapter = adapter
    }

    private fun onIpChanged(ip: String) {
        prefs.ip = ip
//        Handler().post { FirebaseInstanceId.getInstance().deleteInstanceId() }
        restartApp(this)
    }

    companion object {
        const val COLUMNS = 2
    }
}