package com.tubespbdandroid.majika.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tubespbdandroid.majika.data.Branch
import com.tubespbdandroid.majika.databinding.ItemRestoBranchBinding

class BranchAdapter(private val list:List<Branch>) : RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {
    // Holds the view "components" from the card view
    // Using data binding rather than findViewById
    class BranchViewHolder(private val binding: ItemRestoBranchBinding): RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.branchItemName
        var menu: TextView = binding.branchItemMenuRec
        var address: TextView = binding.branchItemAddress
        var phone: TextView = binding.branchItemPhone
    }

    // Create new view to display branches
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):BranchViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return BranchViewHolder(
            ItemRestoBranchBinding.inflate(inflater, parent, false)
        )
    }

    // Bind the items into the view
    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val branchListModel = list[position]

        holder.name.text = branchListModel.name
        holder.menu.text = branchListModel.popularFood
        holder.address.text = branchListModel.address
        holder.phone.text = branchListModel.phone
    }

    // Simply returns the list size
    override fun getItemCount(): Int {
        return list.size
    }

}