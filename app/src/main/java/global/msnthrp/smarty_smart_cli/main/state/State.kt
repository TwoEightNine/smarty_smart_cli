package global.msnthrp.smarty_smart_cli.main.state

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class State(

        @SerializedName("air_temp")
        @Expose
        val airTemp: Double,

        @SerializedName("water_temp")
        @Expose
        val waterTemp: Double,

        @SerializedName("water_fullness")
        @Expose
        val waterFullness: Int
) : Parcelable