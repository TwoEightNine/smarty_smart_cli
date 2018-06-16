package global.msnthrp.smarty_smart_cli.extensions

import global.msnthrp.smarty_smart_cli.network.model.BaseResponse
import global.msnthrp.smarty_smart_cli.utils.applySchedulersFlowable
import global.msnthrp.smarty_smart_cli.utils.applySchedulersSingle
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by msnthrp on 22/01/18.
 */

fun <T> Single<BaseResponse<T>>.subscribeSmart(onSuccess: (T) -> Unit,
                                               onError: (Int, String) -> Unit) {
    this.compose(applySchedulersSingle())
            .subscribe({ response ->
                if (response.result != null) {
                    onSuccess.invoke(response.result)
                } else if (response.errorCode != 0 && response.errorMessage != null) {
                    onError.invoke(response.errorCode!!, response.errorMessage)
                }
            }, {
                onError.invoke(666, it.message ?: "Unknown error")
            })
}

fun <T> Flowable<BaseResponse<T>>.subscribeSmart(onSuccess: (T) -> Unit,
                                                 onError: (Int, String) -> Unit) {
    this.compose(applySchedulersFlowable())
            .subscribe({ response ->
                if (response.result != null) {
                    onSuccess.invoke(response.result)
                } else if (response.errorCode != 0 && response.errorMessage != null) {
                    onError.invoke(response.errorCode!!, response.errorMessage)
                }
            }, {
                onError.invoke(666, it.message ?: "Unknown error")
            })
}