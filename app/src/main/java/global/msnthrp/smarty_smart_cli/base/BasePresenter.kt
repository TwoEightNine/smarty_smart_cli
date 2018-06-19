package global.msnthrp.smarty_smart_cli.base

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView
import global.msnthrp.smarty_smart_cli.SECRET
import global.msnthrp.smarty_smart_cli.extensions.subscribeSmart
import global.msnthrp.smarty_smart_cli.network.ApiService
import global.msnthrp.smarty_smart_cli.network.model.BaseResponse
import global.msnthrp.smarty_smart_cli.storage.Prefs
import global.msnthrp.smarty_smart_cli.utils.Cryptool
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<M, V: MvpLceView<M>>(protected open val prefs: Prefs,
                                                  protected open val api: ApiService) : MvpBasePresenter<V>() {

    protected val disposables: CompositeDisposable = CompositeDisposable()

    private fun updateToken(pullToRefresh: Boolean = false) {
        ifViewAttached { view ->
            view.showLoading(pullToRefresh)
            api.getSeed()
                    .subscribeSmart({ seed ->
                        prefs.token = Cryptool(SECRET).encrypt(seed)
                        onTokenUpdated()
                    }, defaultError())
        }
    }

    protected fun defaultError(pullToRefresh: Boolean = false, block: () -> Unit = {}) = { code: Int, error: String ->
        if (code == 401) {
            updateToken(pullToRefresh)
        } else {
            ifViewAttached { view ->
                view.showError(Throwable(error), pullToRefresh)
                block.invoke()
            }
        }
    }

    protected fun <T1, T2, R> combineResponses(t1: BaseResponse<T1>,
                                               t2: BaseResponse<T2>,
                                               combiner: (T1?, T2?) -> R?): BaseResponse<R> {
        var error: Int? = null
        var errorMessage: String? = null
        when {
            t2.errorCode != 0 -> {
                error = t2.errorCode
                errorMessage = t2.errorMessage
            }
            t1.errorCode != 0 -> {
                error = t1.errorCode
                errorMessage = t2.errorMessage
            }
        }
        return BaseResponse(combiner.invoke(t1.result, t2.result), error, errorMessage)
    }

    /**
     * when token is updated we can continue executing requests
     */
    abstract fun onTokenUpdated()

    override fun destroy() {
        super.destroy()
        disposables.clear()
    }

}