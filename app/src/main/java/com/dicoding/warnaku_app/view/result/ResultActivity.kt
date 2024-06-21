package com.dicoding.warnaku_app.view.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.databinding.ActivityResultBinding
import com.dicoding.warnaku_app.view.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

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

        val uriString = intent.getStringExtra("image_uri")
        if (uriString != null) {
            val imageUri = Uri.parse(uriString)
            binding.userImageResult.setImageURI(imageUri)
        }

        binding.btnShowResult.setOnClickListener {
            val intent = Intent(this, ShowResultActivity::class.java)
            startActivity(intent)
        }

        binding.btnBackDashboard.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}