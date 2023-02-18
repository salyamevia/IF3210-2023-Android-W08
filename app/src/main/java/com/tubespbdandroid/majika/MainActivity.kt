package com.tubespbdandroid.majika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.add
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.tubespbdandroid.majika.fragments.CartFragment
import com.tubespbdandroid.majika.fragments.MenuFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        DELETE TO ENABLE NIGHT MODE
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        supportFragmentManager.commit {
            replace<MenuFragment>(R.id.container)
        }

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> {
                    supportFragmentManager.commit {
                        replace<MenuFragment>(R.id.container)
                    }
                    true
                }
                R.id.cart -> {
                    supportFragmentManager.commit{
                        replace<CartFragment>(R.id.container)
                    }
                    true
                }
                else -> false
            }
        }
    }
}