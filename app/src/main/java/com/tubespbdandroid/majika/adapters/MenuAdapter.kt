package com.tubespbdandroid.majika.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tubespbdandroid.majika.data.Menus
import com.tubespbdandroid.majika.databinding.ListBinding


class MenuAdapter(private val list: ArrayList<Menus>): RecyclerView.Adapter<MenuAdapter.Holder>() {
    inner class Holder(val binding: ListBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.dummyRvContainer.text = list?.get(position)?.name
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
