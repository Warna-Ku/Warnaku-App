package com.dicoding.warnaku_app.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.api.response.Customer
import com.dicoding.warnaku_app.view.history.detail.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView

class CustomersAdapter(private var customers: List<Customer?>) : RecyclerView.Adapter<CustomersAdapter.CustomerViewHolder>() {

    class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.item_nama)
        val email: TextView = itemView.findViewById(R.id.item_email)
        val telp: TextView = itemView.findViewById(R.id.item_telp)
        val image: CircleImageView = itemView.findViewById(R.id.item_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customers[position]
        holder.name.text = customer?.fullname
        holder.email.text = customer?.email
        holder.telp.text = customer?.phone

        Glide.with(holder.itemView.context).load(customer?.faceImageURL).into(holder.image)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("FULLNAME", customer?.fullname)
                putExtra("EMAIL", customer?.email)
                putExtra("PHONE", customer?.phone)
                putExtra("FACE_IMAGE_URL", customer?.faceImageURL)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newCustomers: List<Customer?>) {
        customers = newCustomers
        notifyDataSetChanged()
    }
}
