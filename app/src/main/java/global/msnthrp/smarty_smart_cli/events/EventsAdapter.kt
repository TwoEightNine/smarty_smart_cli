package global.msnthrp.smarty_smart_cli.events

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.base.BaseAdapter
import global.msnthrp.smarty_smart_cli.extensions.setVisible
import global.msnthrp.smarty_smart_cli.extensions.view
import global.msnthrp.smarty_smart_cli.utils.getTime

class EventsAdapter(context: Context) : BaseAdapter<Event, EventsAdapter.EventViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(inflater.inflate(R.layout.item_event, null))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = items[position]

        with(holder) {
            tvEvent.text = event.event
            tvTime.text = getTime(event.timeStamp, full = true, format = "HH:mm:ss")
            tvIp.text = context.getString(R.string.from_ip, event.ip)
            ivNoAuth.setVisible(!event.asserted)
        }
    }

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvEvent: TextView by view(R.id.tvEvent)
        val tvIp: TextView by view(R.id.tvIp)
        val tvTime: TextView by view(R.id.tvTime)
        val ivNoAuth: ImageView by view(R.id.ivNoAuth)

    }
}