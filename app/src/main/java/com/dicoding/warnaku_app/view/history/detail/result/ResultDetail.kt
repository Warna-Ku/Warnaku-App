package com.dicoding.warnaku_app.view.history.detail.result

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.api.response.AnalysisReportsItem
import com.dicoding.warnaku_app.data.adapter.ColorsAdapter
import com.dicoding.warnaku_app.databinding.ActivityResultDetailBinding

class ResultDetail : AppCompatActivity() {
    private val binding: ActivityResultDetailBinding by lazy{
        ActivityResultDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "Result"

        val analysisReport: AnalysisReportsItem? = intent.getParcelableExtra("REPORT_DETAIL")

        binding.tvResultSeason.text = analysisReport?.season
        Glide.with(this).load(analysisReport?.paletteImg).into(binding.ivColorPallete)
        binding.tvColorPallete.text = analysisReport?.paletteDescription

        // Set up RecyclerView for recommendations
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ColorsAdapter(analysisReport?.colors ?: emptyList())
        binding.recyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}