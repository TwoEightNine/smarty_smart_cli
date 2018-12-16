package global.msnthrp.smarty_smart_cli.main.features

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.flask.colorpicker.Utils
import com.squareup.picasso.Picasso
import global.msnthrp.smarty_smart_cli.R
import global.msnthrp.smarty_smart_cli.base.BaseAdapter
import global.msnthrp.smarty_smart_cli.extensions.setVisible
import global.msnthrp.smarty_smart_cli.extensions.view

class FeaturesAdapter(context: Context,
                      private val onClick: (Feature) -> Unit)
    : BaseAdapter<Feature, FeaturesAdapter.FeatureViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = FeatureViewHolder(inflater.inflate(R.layout.item_feature, null))

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        val feature = items[position]

        with(holder) {
            tvName.text = feature.name
            with(tvValue) {
                if (feature.action == Feature.ACTION_RGB) {
                    text = BRICKS
                    val color = Integer.parseInt(feature.value, 16) or (0xff shl 24)
                    val shColor = if (color == Color.BLACK) {
                        Color.BLACK
                    } else {
                        Utils.colorAtLightness(color, 1f)
                    }
                    setTextColor(shColor)
                } else {
                    text = feature.value
                    setTextColor(Color.WHITE)
                }
            }
            if (!feature.thumb.isNullOrBlank()) {
                Picasso.with(context)
                        .load(feature.thumb)
                        .into(ivBackground)
            }
        }
    }

    companion object {
        const val BRICKS = "███"
    }

    inner class FeatureViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val cardView: CardView by view(R.id.cardView)
        val tvName: TextView by view(R.id.tvName)
        val tvValue: TextView by view(R.id.tvValue)
        val ivBackground: ImageView by view(R.id.ivBackground)

        init {
            cardView.setOnClickListener { onClick.invoke(items[adapterPosition]) }
        }
    }
}