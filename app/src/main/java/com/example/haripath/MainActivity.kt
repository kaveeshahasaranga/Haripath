package com.example.haripath

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var fabAddTransaction: FloatingActionButton
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        // Initialize views
        initializeViews()
        setupBottomNavigation()
        setupAddTransactionButton()
        setupViewPager()
    }

    private fun initializeViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation)
        fabAddTransaction = findViewById(R.id.fabAddTransaction)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Already on home
                    true
                }
                R.id.navigation_analysis -> {
                    startActivity(Intent(this, SpendingAnalysisActivity::class.java))
                    true
                }
                R.id.navigation_search -> {
                    startActivity(Intent(this, TransactionHistoryActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupAddTransactionButton() {
        fabAddTransaction.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java))
        }
    }

    private fun setupViewPager() {
        // Set up ViewPager adapter
        val adapter = MainPagerAdapter(this)
        viewPager.adapter = adapter

        // Connect TabLayout with ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Transactions"
                1 -> "Budget"
                else -> null
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_help -> {
                // TODO: Show help dialog or activity
                true
            }
            R.id.action_about -> {
                // TODO: Show about dialog or activity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}