package global.msnthrp.smarty_smart_cli.network

import global.msnthrp.smarty_smart_cli.events.Event
import global.msnthrp.smarty_smart_cli.main.actions.Action
import global.msnthrp.smarty_smart_cli.main.features.Feature
import global.msnthrp.smarty_smart_cli.main.state.State
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

    @GET("/getFeatures")
    fun getFeatures(): Single<BaseResponse<ArrayList<Feature>>>

    @FormUrlEncoded
    @POST("/registerToken")
    fun registerToken(@Field("fcmToken") token: String): Single<BaseResponse<Int>>
}