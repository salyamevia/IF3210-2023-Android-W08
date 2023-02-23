package com.tubespbdandroid.majika.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.tubespbdandroid.majika.R
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tubespbdandroid.majika.utility.BranchDiffUtilCallback
import com.tubespbdandroid.majika.utility.BranchListEvent
import com.tubespbdandroid.majika.data.Branch
import com.tubespbdandroid.majika.databinding.ItemRestoBranchBinding

class BranchListAdapter(val event:MutableLiveData<BranchListEvent> = MutableLiveData()) : ListAdapter<Branch, BranchListAdapter.BranchViewHolder>(BranchDiffUtilCallback()) {
    // Item viewer property
    // "Bridge" between the Data Model and the Layout
    // Mutable Live data because it will/can change the value whenever we want and will record the event when it happens
    class BranchViewHolder(private val binding: ItemRestoBranchBinding): RecyclerView.ViewHolder(binding.root) {
        var name: TextView = binding.branchItemName
        var menu: TextView = binding.branchItemMenuRec
        var address: TextView = binding.branchItemAddress
        var phone: TextView = binding.branchItemPhone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return BranchViewHolder(
            // Inflate the layout of the item
            ItemRestoBranchBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        // Get the variables
        getItem(position).let{ branch ->
            holder.name.text = branch.name
            holder.menu.text = branch.popularFood
            holder.address.text = branch.address
            holder.phone.text = branch.phone

            holder.itemView.setOnClickListener{
                event.value = BranchListEvent.OnBranchItemClick(position)
            }
        }
    }

}