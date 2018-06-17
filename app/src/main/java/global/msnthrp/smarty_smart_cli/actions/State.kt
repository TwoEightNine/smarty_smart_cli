package global.msnthrp.smarty_smart_cli.actions

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class State(

        @SerializedName("temp")
        @Expose
        val temp: Double
) : Parcelable