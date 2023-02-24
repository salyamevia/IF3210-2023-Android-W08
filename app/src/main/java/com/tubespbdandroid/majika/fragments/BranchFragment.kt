package com.tubespbdandroid.majika.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tubespbdandroid.majika.databinding.FragmentBranchBinding
import com.tubespbdandroid.majika.utility.BranchListEvent

class BranchFragment: Fragment() {
    private var _binding: FragmentBranchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BranchListEvent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBranchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Branch"
    }
}