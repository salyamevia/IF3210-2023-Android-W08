package com.tubespbdandroid.majika.utility

import androidx.recyclerview.widget.DiffUtil
import com.tubespbdandroid.majika.data.Branch

class BranchDiffUtilCallback : DiffUtil.ItemCallback<Branch>() {
    // Check fi there are duplicate in the data
    override fun areContentsTheSame(oldItem: Branch, newItem: Branch): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areItemsTheSame(oldItem: Branch, newItem: Branch): Boolean {
        return oldItem.name == newItem.name
    }
}