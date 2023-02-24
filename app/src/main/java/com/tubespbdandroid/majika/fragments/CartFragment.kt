package com.tubespbdandroid.majika.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tubespbdandroid.majika.PaymentActivity
import com.tubespbdandroid.majika.R
import com.tubespbdandroid.majika.databinding.FragmentCartBinding
import com.tubespbdandroid.majika.databinding.FragmentMenuBinding
import kotlinx.android.synthetic.main.fragment_cart.*


class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Keranjang"
        val rollButton: Button = button
        rollButton.setOnClickListener {
            startActivity(Intent(activity, PaymentActivity::class.java))
        }
    }
}