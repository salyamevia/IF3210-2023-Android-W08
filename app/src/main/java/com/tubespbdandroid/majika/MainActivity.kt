package com.tubespbdandroid.majika

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tubespbdandroid.majika.databinding.ItemRestoBranchBinding
import com.tubespbdandroid.majika.fragments.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleSearchIntent(intent)

        //        DELETE TO ENABLE NIGHT MODE
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)) as Button

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        supportFragmentManager.commit {
            replace<TwibbonFragment>(R.id.container)
        }

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<SearchBarFragment>(R.id.search_bar_container)
                        replace<MenuFragment>(R.id.container)
                        addToBackStack("menu")
                    }
                    true
                }
                R.id.cart -> {
                    val searchBarFragment = supportFragmentManager.findFragmentById(R.id.search_bar_container) as SearchBarFragment?
                    supportFragmentManager.commit{
                        setReorderingAllowed(true)
                        replace<CartFragment>(R.id.container)
                        if (searchBarFragment != null) {
                            remove(searchBarFragment)
                        }
                        addToBackStack("cart")
                    }
                    true
                }
                R.id.branch -> {
                    supportFragmentManager.commit{
                        replace<BranchFragment>(R.id.container)
                    }
                    true
                }

                R.id.twibbon -> {
                    supportFragmentManager.commit {
                        replace<TwibbonFragment>(R.id.container)
                    }
                    true
                }
                else -> false
            }
        }
    }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSearchIntent(intent)
    }

    private fun handleSearchIntent(intent: Intent) {
        try {
            if (Intent.ACTION_SEARCH == intent.action) {
                val query = intent.getStringExtra(SearchManager.QUERY)!!
                val bundle = Bundle()
                bundle.putString("query", query)

                val menuFragment = MenuFragment()
                menuFragment.arguments = bundle
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.container, menuFragment)
                    addToBackStack(null)
                }
            }
        } catch (error: Error) {
            println(error.message)
        }

    }

}