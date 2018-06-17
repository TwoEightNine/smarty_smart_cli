package global.msnthrp.smarty_smart_cli.actions

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.base.BaseAdapter
import global.msnthrp.smarty_smart_cli.extensions.view

class ActionsAdapter(context: Context,
                     private val onClick: ((Action) -> Unit)? = null)
    : BaseAdapter<Action, ActionsAdapter.ActionViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder(inflater.inflate(R.layout.item_action, null))
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val action = items[position]

        with(holder) {
            tvName.text = action.name
            if (action.thumb.isNotBlank()) {
                Picasso.with(context)
                        .load(action.thumb)
                        .into(ivBackground)
            }
        }
    }

    inner class ActionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName: TextView by view(R.id.tvName)
        val ivBackground: ImageView by view(R.id.ivBackground)

        init {
            itemView.setOnClickListener {
                onClick?.invoke(items[adapterPosition])
            }
        }

    }

}