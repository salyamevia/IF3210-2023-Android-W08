package com.tubespbdandroid.majika

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.remove
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tubespbdandroid.majika.fragments.BranchFragment
import com.tubespbdandroid.majika.fragments.CartFragment
import com.tubespbdandroid.majika.fragments.MenuFragment
import com.tubespbdandroid.majika.fragments.SearchBarFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var selectedBottomNavBarItemId = R.id.menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutViewBasedOnOrientation(resources.configuration.orientation)
        handleSearchIntent(intent)

//        DELETE TO ENABLE NIGHT MODE
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSearchIntent(intent)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        setLayoutViewBasedOnOrientation(newConfig.orientation)
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

    private fun initBottomNavBarListener() {
        bottomNavigationView.setOnItemSelectedListener {
            selectedBottomNavBarItemId = it.itemId
            val searchBarFragment = supportFragmentManager.findFragmentById(R.id.search_bar_container) as SearchBarFragment?
            supportFragmentManager.commit{
                if (searchBarFragment != null) {
                    remove(searchBarFragment)
                }
            }
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
                    supportFragmentManager.commit{
                        setReorderingAllowed(true)
                        replace<CartFragment>(R.id.container)
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
                else -> false
            }
        }
    }

    private fun setLayoutViewBasedOnOrientation(orientation: Int) {
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                setContentView(R.layout.activity_main)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                setContentView(R.layout.activity_main_landscape)
            }
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        initBottomNavBarListener()
        bottomNavigationView.selectedItemId = selectedBottomNavBarItemId
    }

}