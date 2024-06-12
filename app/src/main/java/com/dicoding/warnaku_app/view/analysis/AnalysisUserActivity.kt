package com.dicoding.warnaku_app.view.analysis

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.warnaku_app.databinding.ActivityAnalysisUserBinding
import com.dicoding.warnaku_app.view.result.ResultActivity

class AnalysisUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalysisUserBinding
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalysisUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}
