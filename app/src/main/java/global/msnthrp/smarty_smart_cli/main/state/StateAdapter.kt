package global.msnthrp.smarty_smart_cli.main.state

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.base.BaseAdapter
import global.msnthrp.smarty_smart_cli.extensions.view

class StateAdapter(context: Context) : BaseAdapter<Pair<String, String>, StateAdapter.StateViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        return StateViewHolder(inflater.inflate(R.layout.item_state, null))
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val pair = items[position]

        with(holder) {
            tvName.text = pair.first

            if (pair.second.startsWith("0x")) {
                val color = pair.second.substring(2)
                val intColor = Integer.parseInt(color, 16) or (0xff shl 24)
                tvValue.setTextColor(intColor)
                tvValue.text = BRICKS
            } else {
                tvValue.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                tvValue.text = pair.second
            }
        }
    }

    companion object {
        const val BRICKS = "███"

        fun stateToPairs(state: State): ArrayList<Pair<String, String>> {
            val result = arrayListOf<Pair<String, String>>()
//            result.add(Pair("Air temp, °C", "${state.airTemp}"))
//            result.add(Pair("Water temp, °C", "${state.waterTemp}"))
//            result.add(Pair("Water fullness, %", "${state.waterFullness}"))
//            result.add(Pair("Teapot", if (state.teapot) "ON" else "OFF"))
            result.add(Pair("Light", if (state.light) "ON" else "OFF"))
            result.add(Pair("RGB", if (state.rgb) "ON" else "OFF"))
            result.add(Pair("Amplifier", if (state.amplifier) "ON" else "OFF"))
//            result.add(Pair("LED", "0x${state.led}"))
            return result
        }
    }

    inner class StateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName: TextView by view(R.id.tvName)
        val tvValue: TextView by view(R.id.tvValue)
    }
}