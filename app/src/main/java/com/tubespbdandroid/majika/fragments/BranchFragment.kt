package com.tubespbdandroid.majika.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tubespbdandroid.majika.MainActivity
import com.tubespbdandroid.majika.R
import com.tubespbdandroid.majika.adapters.BranchAdapter
import com.tubespbdandroid.majika.data.Branch
import com.tubespbdandroid.majika.data.DefaultResponse
import com.tubespbdandroid.majika.databinding.FragmentBranchBinding
import com.tubespbdandroid.majika.databinding.ItemRestoBranchBinding
import com.tubespbdandroid.majika.retrofit.branch.BranchClient
import com.tubespbdandroid.majika.retrofit.branch.BranchService
import kotlinx.android.synthetic.main.fragment_branch.*
import kotlinx.android.synthetic.main.item_resto_branch.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class BranchFragment: Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<BranchAdapter.BranchViewHolder>? = null
    private var _binding: FragmentBranchBinding? = null
    private val binding get() = _binding!!

    private var _itemBinding: ItemRestoBranchBinding? = null
    private var itemBinding get() = _itemBinding!!

    private val branchesCall = BranchClient.service.getBranches()
    private val branches = ArrayList<Branch>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate (fill in with "variables" from the view) the fragment
        _binding = FragmentBranchBinding.inflate(inflater, container, false)

        binding.branchListFragment.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        branchesCall.enqueue(object: Callback<DefaultResponse<Branch>> {
            override fun onResponse(call: Call<DefaultResponse<Branch>>,
                                    response: Response<DefaultResponse<Branch>>) {
                val data = response.body()!!.data

                for(i in 0 until data.size){
                    branches.add(data[i])
                }

                val branchAdapter = BranchAdapter(branches)
                binding.branchListFragment.adapter = branchAdapter
                context?.let { branchAdapter.addContext(it) }
            }

            override fun onFailure(call: Call<DefaultResponse<Branch>>, t: Throwable) {
                Log.d("BRANCH_CALL", "Error bang " + t.message)
            }
        })

        val button: Button = itemBinding.branchListMap
        val latitude: Float = 0.0f
        val longitude: Float = 0.0f

        button.setOnClickListener{
            // Tapi perlu variable lat long
            val gmmIntentUri = Uri.parse("google.streetview:cbll=$latitude,$longitude")

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent) // Ini ga bisa dipke di adapter krn perlu "activity"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Branch"
    }
}
