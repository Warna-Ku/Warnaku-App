package com.dicoding.warnaku_app.view.history.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
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
import com.dicoding.warnaku_app.databinding.ActivityDetailBinding
import com.dicoding.warnaku_app.databinding.BottomsheetBinding
import com.dicoding.warnaku_app.view.history.detail.result.ResultDetail
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "Result"

        setContentView(binding.root)
        setView()
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        val analysisReport: AnalysisReportsItem? = intent.getParcelableExtra("REPORT")
        val customer = analysisReport?.customer

        binding.tvCustomerName.text = customer?.fullname
        binding.tvEmailCustomer.text = customer?.email
        binding.tvNumberCustomer.text = customer?.phone
        binding.tvAddressCustomer.text = customer?.address
        binding.tvSeasonResult.text = "Result: ${analysisReport?.season}"


        Glide.with(this).load(customer?.faceImageURL).into(binding.userImageResult)

        binding.btnShowResult.setOnClickListener {
            val intent = Intent(this@DetailActivity, ResultDetail::class.java).apply {
                putExtra("REPORT_DETAIL", analysisReport)
            }
            startActivity(intent)
        }
    }

    private fun showBottomSheet(analysisReport: AnalysisReportsItem?) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bindingg = BottomsheetBinding.inflate(layoutInflater)

        bindingg.tvResultSeason.text = analysisReport?.season

        analysisReport?.let { report ->
            Glide.with(this).load(report.paletteImg).into(bindingg.ivColorPallete)
            bindingg.tvColorPallete.text = report.paletteDescription

            // Set up RecyclerView for recommendations
            bindingg.recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = ColorsAdapter(report.colors ?: emptyList())
            bindingg.recyclerView.adapter = adapter
        }

        bottomSheetDialog.setContentView(bindingg.root) // Correct reference to bindingg.root
        bottomSheetDialog.show()
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