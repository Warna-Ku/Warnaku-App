package com.dicoding.warnaku_app.view.analysis

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.databinding.ActivityAnalysisUserBinding
import com.dicoding.warnaku_app.view.result.ResultActivity

class AnalysisUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalysisUserBinding
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalysisUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "Analysis User"

        // Retrieve the URI from the intent
        val uriString = intent.getStringExtra("image_uri")
        if (uriString != null) {
            imageUri = Uri.parse(uriString)
            binding.profileImage.setImageURI(imageUri)
        }

        binding.btnNext.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val address = binding.addressEditText.text.toString()

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                AlertDialog.Builder(this).apply {
                    setTitle("Failed to analyze User")
                    setMessage("Please fill in all fields")
                    setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
            } else {
                // Show confirmation dialog
                AlertDialog.Builder(this).apply {
                    setTitle("Confirm Data")
                    setMessage("Name: $name\nPhone: $phone\nAddress: $address\n\nIs the information correct?")
                    setPositiveButton("Yes") { dialog, _ ->
                        val intent = Intent(this@AnalysisUserActivity, ResultActivity::class.java).apply {
                            putExtra("image_uri", imageUri.toString())
                            putExtra("name", name)
                            putExtra("phone", phone)
                            putExtra("address", address)
                        }
                        startActivity(intent)
                        dialog.dismiss()
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
            }
        }
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
