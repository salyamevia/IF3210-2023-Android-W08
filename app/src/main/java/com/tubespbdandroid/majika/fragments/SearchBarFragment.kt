package com.tubespbdandroid.majika.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.tubespbdandroid.majika.R


class SearchBarFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        (requireActivity().findViewById<SearchView>(R.id.search_bar)).apply{
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        }
    }
}