package com.tubespbdandroid.majika.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tubespbdandroid.majika.MainActivity
import com.tubespbdandroid.majika.R
import com.tubespbdandroid.majika.adapters.BranchAdapter
import com.tubespbdandroid.majika.data.Branch
import com.tubespbdandroid.majika.databinding.FragmentBranchBinding
import kotlinx.android.synthetic.main.fragment_branch.*
import kotlinx.android.synthetic.main.item_resto_branch.*


class BranchFragment: Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<BranchAdapter.BranchViewHolder>? = null
    private var _binding: FragmentBranchBinding? = null
    private val binding get() = _binding!!
    private var branches = listOf(
        Branch("McD", "Burger", "Udin", "A", "1",2.1, 3.2),
        Branch("McD", "Burger", "Udin", "A", "1",2.1, 3.2)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate (fill in with "variables" from the view) the fragment
        _binding = FragmentBranchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        branch_list_fragment.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = BranchAdapter(branches)
        }
    }


}