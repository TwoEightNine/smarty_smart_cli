package global.msnthrp.smarty_smart_cli.main.features

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feature(

        @SerializedName("name")
        @Expose
        val name: String,

        @SerializedName("value")
        @Expose
        val value: String,

        @SerializedName("action")
        @Expose
        val action: String? = null,

        @SerializedName("params")
        @Expose
        val params: List<String>? = null,

        @SerializedName("thumb")
        @Expose
        val thumb: String? = null
) : Parcelable {
        companion object {
            const val ACTION_RGB = "rgb"
        }
}
