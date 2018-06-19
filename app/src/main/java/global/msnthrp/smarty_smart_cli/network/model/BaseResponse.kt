package global.msnthrp.smarty_smart_cli.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class BaseResponse<T>(
        @SerializedName("result")
        @Expose
        val result: T?,

        @SerializedName("error")
        @Expose
        val errorCode: Int? = 0,

        @SerializedName("message")
        @Expose
        val errorMessage: String? = null
)