package com.tubespbdandroid.majika.adapters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.tubespbdandroid.majika.MainActivity
import com.tubespbdandroid.majika.data.Branch
import com.tubespbdandroid.majika.databinding.ItemRestoBranchBinding
import com.tubespbdandroid.majika.fragments.BranchFragment
import kotlinx.android.synthetic.main.item_resto_branch.*

class BranchAdapter(private val list:List<Branch>) : RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {
    // Holds the view "components" from the card view
    // Using data binding rather than findViewById
    class BranchViewHolder(private val binding: ItemRestoBranchBinding): RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.branchItemName
        var menu: TextView = binding.branchItemMenuRec
        var address: TextView = binding.branchItemAddress
        var phone: TextView = binding.branchItemPhone
        var button: Button = binding.branchListMap
        var latitude: Float = 0.0f
        var longitude: Float = 0.0f

        init {
            button.setOnClickListener{
                val gmmIntentUri = Uri.parse("geo:$latitude,$longitude")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                //startActivity(mapIntent)
                Log.d("A", "Hello...")
            }
        }
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
        holder.latitude = branchListModel.latitude.toFloat()
        holder.longitude = branchListModel.longitude.toFloat()
    }

    // Simply returns the list size
    override fun getItemCount(): Int {
        return list.size
    }

}