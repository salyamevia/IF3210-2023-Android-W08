package com.tubespbdandroid.majika.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tubespbdandroid.majika.data.RestaurantMenu
import com.tubespbdandroid.majika.databinding.ListBinding
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor


class MenuAdapter(private val list: ArrayList<RestaurantMenu>): RecyclerView.Adapter<MenuAdapter.Holder>() {
    inner class Holder(val binding: ListBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val displayedPrice = "Rp ${formatPrice(list?.get(position)?.price)}"
        val displayedSold = "${formatSold(list?.get(position)?.sold!!)} Terjual"


        holder.binding.menuName.text = list?.get(position)?.name
        holder.binding.menuDesc.text = list?.get(position)?.description
        holder.binding.menuPrice.text = displayedPrice
        holder.binding.menuSold.text = displayedSold
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun formatPrice(price: Int?): String {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")

        var finalFormattedPrice = (format.format(price)).toString()
        finalFormattedPrice = finalFormattedPrice
            .replace("IDR", "")
            .replace(",", ".")
            .trim()

        return finalFormattedPrice
    }

    private fun formatSold(sold: Int): String {
        val displayedSold = floor(sold/1000.0)

        return if (displayedSold < 1.0) {
            sold.toString()
        } else {
            "${displayedSold.toInt()}RB+"
        }
    }
}
