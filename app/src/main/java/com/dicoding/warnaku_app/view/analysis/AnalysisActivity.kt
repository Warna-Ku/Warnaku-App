package com.dicoding.warnaku_app.view.analysis

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.databinding.ActivityAnalysisBinding
import com.dicoding.warnaku_app.view.history.HistoryActivity
import com.dicoding.warnaku_app.view.main.MainActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class AnalysisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalysisBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "Analysis"

        binding.btnAnalyze.setOnClickListener {
            checkImage()
        }

        binding.btnGallery.setOnClickListener {
            openGallery()
        }

        binding.btnCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        // Receive the URI from CameraActivity
        val uriString = intent.getStringExtra("captured_image_uri")
        if (uriString != null) {
            currentImageUri = Uri.parse(uriString)
            binding.imageView.setImageURI(currentImageUri)
        }

        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        val bottomNavigationItems = mutableListOf(
            CurvedBottomNavigation.Model(DASHBOARD, getString(R.string.dashboard), R.drawable.home),
            CurvedBottomNavigation.Model(ANALYSIS, getString(R.string.analysis), R.drawable.scan),
            CurvedBottomNavigation.Model(HISTORY, getString(R.string.history), R.drawable.history),
        )

        binding.bottomNavigation.apply {
            bottomNavigationItems.forEach { add(it) }
            setOnClickMenuListener {
                when (it.id) {
                    DASHBOARD -> startActivityWithAnimation(Intent(this@AnalysisActivity, MainActivity::class.java))
                    ANALYSIS -> startActivityWithAnimation(Intent(this@AnalysisActivity, AnalysisActivity::class.java))
                    HISTORY -> startActivityWithAnimation(Intent(this@AnalysisActivity, HistoryActivity::class.java))
                }
            }
            show(ANALYSIS)
        }
    }

    private fun startActivityWithAnimation(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
    private fun checkImage() {
        if (currentImageUri != null) {
            val intent = Intent(this, AnalysisUserActivity::class.java).apply {
                putExtra("image_uri", currentImageUri.toString())
            }
            startActivity(intent)
        } else {
            showToast("Please select an image before analyzing.")
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            binding.imageView.setImageURI(uri)
            showToast("Image selected: $uri")
        } else {
            showToast("No image selected")
        }
    }

    private fun openGallery() {
        launcherGallery.launch("image/*")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val DASHBOARD = R.id.dashboard_action
        val ANALYSIS = R.id.scan_action
        val HISTORY = R.id.history_action
    }
}
