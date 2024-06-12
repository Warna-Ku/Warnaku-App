package com.dicoding.warnaku_app.view.analysis

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.warnaku_app.databinding.ActivityAnalysisBinding

class AnalysisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalysisBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}
