package global.msnthrp.smarty_smart_cli.base

import android.content.Context
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.extensions.subscribeSmart
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.network.model.BaseResponse
import global.msnthrp.smarty_smart_cli.storage.Lg
import global.msnthrp.smarty_smart_cli.storage.Prefs
import global.msnthrp.smarty_smart_cli.utils.Cryptool
import global.msnthrp.smarty_smart_cli.utils.SECRET
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<M, V: MvpLceView<M>>(protected open val prefs: Prefs,
                                                  protected open val api: ApiService,
                                                  private val context: Context) : MvpBasePresenter<V>() {

    protected val disposables: CompositeDisposable = CompositeDisposable()

    private fun updateToken(pullToRefresh: Boolean = false) {
        ifViewAttached { view ->
            view.showLoading(pullToRefresh)
            Lg.i("get seed")
            api.getSeed()
                    .subscribeSmart({ seed ->
                        prefs.token = Cryptool(SECRET).encrypt(seed)
                        Lg.i("seed obtained and encrypted: ${prefs.token}")
                        onTokenUpdated()
                    }, defaultError())
        }
    }

    protected fun defaultError(pullToRefresh: Boolean = false, block: () -> Unit = {}) = { code: Int, error: String ->
        if (code == 401) {
            Lg.i("server returned 401")
            updateToken(pullToRefresh)
        } else {
            ifViewAttached { view ->
                Lg.i("error occurred: $error")
                view.showError(Throwable(wrapError(error)), pullToRefresh)
                block.invoke()
            }
        }
    }

    protected inline fun <T1, T2, R> combineResponses(t1: BaseResponse<T1>,
                                               t2: BaseResponse<T2>,
                                               combiner: (T1?, T2?) -> R?): BaseResponse<R> {
        var error: Int? = null
        var errorMessage: String? = null
        when {
            t2.errorCode != null -> {
                error = t2.errorCode
                errorMessage = t2.errorMessage
            }
            t1.errorCode != null -> {
                error = t1.errorCode
                errorMessage = t1.errorMessage
            }
        }
        return BaseResponse(combiner.invoke(t1.result, t2.result), error, errorMessage)
    }

    private fun wrapError(error: String) = "$error\n${context.getString(R.string.try_again)}"

    /**
     * when token is updated we can continue executing requests
     */
    abstract fun onTokenUpdated()

    override fun destroy() {
        super.destroy()
        disposables.clear()
    }

}