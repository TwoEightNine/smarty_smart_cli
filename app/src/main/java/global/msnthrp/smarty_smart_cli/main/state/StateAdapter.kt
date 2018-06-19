package global.msnthrp.smarty_smart_cli.main.state

import android.content.Context
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
            tvValue.text = pair.second
        }
    }

    companion object {
        fun stateToPairs(state: State): ArrayList<Pair<String, String>> {
            val result = arrayListOf<Pair<String, String>>()
            result.add(Pair("Air temp, °C", "${state.airTemp}"))
            result.add(Pair("Water temp, °C", "${state.waterTemp}"))
            result.add(Pair("Water fullness, %", "${state.waterFullness}"))
            return result
        }
    }

    inner class StateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName: TextView by view(R.id.tvName)
        val tvValue: TextView by view(R.id.tvValue)
    }
}