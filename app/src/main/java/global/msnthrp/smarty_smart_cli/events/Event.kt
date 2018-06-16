package global.msnthrp.smarty_smart_cli.events

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
        @SerializedName("id")
        @Expose
        val id: Int,

        @SerializedName("event")
        @Expose
        val event: String,

        @SerializedName("ip")
        @Expose
        val ip: String,

        @SerializedName("asserted")
        @Expose
        val asserted: Boolean,

        @SerializedName("time_stamp")
        @Expose
        val timeStamp: Int
) : Parcelable