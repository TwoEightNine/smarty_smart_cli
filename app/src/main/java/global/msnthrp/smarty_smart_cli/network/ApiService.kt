package global.msnthrp.smarty_smart_cli.network

import global.msnthrp.smarty_smart_cli.actions.Action
import global.msnthrp.smarty_smart_cli.actions.State
import global.msnthrp.smarty_smart_cli.events.Event
import global.msnthrp.smarty_smart_cli.network.model.BaseResponse
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @GET("/getEvents")
    fun getEvents(): Single<BaseResponse<ArrayList<Event>>>

    @GET("/getSeed")
    fun getSeed(): Single<BaseResponse<String>>

    @FormUrlEncoded
    @POST("/execute")
    fun execute(@Field("action") action: String,
                @FieldMap params: Map<String, String>): Single<BaseResponse<Int>>

    @GET("/supportedActions")
    fun getActions(): Single<BaseResponse<ArrayList<Action>>>

    @GET("/getState")
    fun getState(): Single<BaseResponse<State>>
}