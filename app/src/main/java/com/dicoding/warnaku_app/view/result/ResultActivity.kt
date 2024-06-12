package com.dicoding.warnaku_app.view.result

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.warnaku_app.databinding.ActivityResultBinding
import com.google.android.material.tabs.TabLayoutMediator

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                0 -> tab.text = "Colour"
                1 -> tab.text = "Recommendation"
            }
        }.attach()
    }
}