package com.dicoding.warnaku_app.view.result

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.databinding.ActivityResultBinding
import com.google.android.material.tabs.TabLayoutMediator

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "Result"

        // Retrieve the URI from the intent
        val uriString = intent.getStringExtra("image_uri")
        if (uriString != null) {
            val imageUri = Uri.parse(uriString)
            binding.userImageResult.setImageURI(imageUri)
        }

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
//                    tab.view.background = ContextCompat.getDrawable(this, R.drawable.tab_selector)
                }
                1 -> {
//                    tab.view.background = ContextCompat.getDrawable(this, R.drawable.tab_selector)
                }
            }
        }.attach()
    }
}