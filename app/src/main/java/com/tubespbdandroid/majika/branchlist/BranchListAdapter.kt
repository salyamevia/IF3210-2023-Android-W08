package com.tubespbdandroid.majika.branchlist

import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BranchListAdapter(val event:MutableLiveData<BranchListEvent> = MutableLiveData()) : ListAdapter<Branch, BranchListAdapter.BranchViewHolder>() {
    // Item viewer property
    // "Bridge" between the Data Model and the Layout
    class BranchViewHolder(root: View): RecyclerView.ViewHolder(root) {
        var name: TextView = root.branch_item_phone
        var menu: TextView = root.branch_item_menu_rec
        var address: TextView = root.branch_item_address
        var phone: TextView = root.branch_item_phone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}