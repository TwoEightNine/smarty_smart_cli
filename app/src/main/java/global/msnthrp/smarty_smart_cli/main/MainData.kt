package global.msnthrp.smarty_smart_cli.main

import android.os.Parcelable
import global.msnthrp.smarty_smart_cli.main.actions.Action
import global.msnthrp.smarty_smart_cli.main.state.State
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainData(
        val state: State,
        val actions: ArrayList<Action>
) : Parcelable