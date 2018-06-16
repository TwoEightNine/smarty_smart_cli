package global.msnthrp.smarty_smart_cli.network

import global.msnthrp.smarty_smart_cli.events.Event
import global.msnthrp.smarty_smart_cli.network.model.BaseResponse
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @POST("/getEvents")
    fun getEvents(): Single<BaseResponse<ArrayList<Event>>>

    @GET("/getSeed")
    fun getSeed(): Single<BaseResponse<String>>

    @FormUrlEncoded
    @POST("/execute")
    fun execute(): Single<BaseResponse<Int>>

}