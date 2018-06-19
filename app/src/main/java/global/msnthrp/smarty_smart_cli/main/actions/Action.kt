package global.msnthrp.smarty_smart_cli.main.actions

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Action(
        @SerializedName("name")
        @Expose
        val name: String,

        @SerializedName("action")
        @Expose
        val action: String,

        @SerializedName("thumb")
        @Expose
        val thumb: String,

        @SerializedName("params")
        @Expose
        val params: ArrayList<String>

) : Parcelable