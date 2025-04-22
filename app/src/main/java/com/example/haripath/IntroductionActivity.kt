package com.example.haripath

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class IntroductionActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        setupToolbar()
        initializeViews()
        setupViewPager()
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeViews() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.indicator)
        btnNext = findViewById(R.id.btnNext)
    }

    private fun setupViewPager() {
        val introPages = listOf(
            IntroPage(
                R.drawable.ic_launcher_foreground,
                "Welcome to Haripath",
                "Discover the best way to navigate your journey"
            ),
            IntroPage(
                R.drawable.ic_launcher_foreground,
                "Easy Navigation",
                "Find your way with our intuitive interface"
            ),
            IntroPage(
                R.drawable.ic_launcher_foreground,
                "Get Started",
                "Begin your journey with Haripath today"
            )
        )

        viewPager.adapter = IntroPagerAdapter(introPages)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        btnNext.setOnClickListener {
            if (viewPager.currentItem < introPages.size - 1) {
                viewPager.currentItem += 1
            } else {
                // Navigate to main app
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                btnNext.text = if (position == introPages.size - 1) "Get Started" else "Next"
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

data class IntroPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

class IntroPagerAdapter(private val pages: List<IntroPage>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<IntroPagerAdapter.IntroViewHolder>() {

    class IntroViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.introImage)
        val title: TextView = view.findViewById(R.id.introTitle)
        val description: TextView = view.findViewById(R.id.introDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_intro_page, parent, false)
        return IntroViewHolder(view)
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        val page = pages[position]
        holder.image.setImageResource(page.imageRes)
        holder.title.text = page.title
        holder.description.text = page.description
    }

    override fun getItemCount() = pages.size
} 