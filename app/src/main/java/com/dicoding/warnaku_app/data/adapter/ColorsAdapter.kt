package com.dicoding.warnaku_app.data.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.api.response.ColorsItem

class ColorsAdapter(private val colors: List<ColorsItem?>) : RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorName: TextView = itemView.findViewById(R.id.tv_colour_name)
        val colorDescription: TextView = itemView.findViewById(R.id.tv_description)
        val colorCode: TextView = itemView.findViewById(R.id.tv_color_code)
        val cardColor: CardView = itemView.findViewById(R.id.cv_color_code)

        val colorImage: ImageView = itemView.findViewById(R.id.result_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_colour, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        holder.colorName.text = color?.name
        holder.colorDescription.text = color?.description
        holder.colorCode.text = color?.code
        val colorCode = color?.code

        if (colorCode != null) {
            val colorInt = Color.parseColor(colorCode)

            val colorStateList = ColorStateList.valueOf(colorInt)

            holder.cardColor.setCardBackgroundColor(colorStateList)
        }
        Glide.with(holder.itemView.context).load(color?.image).into(holder.colorImage)
    }

    override fun getItemCount(): Int {
        return colors.size
    }
}