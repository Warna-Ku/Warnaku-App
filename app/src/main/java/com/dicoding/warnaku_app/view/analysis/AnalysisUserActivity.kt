package com.dicoding.warnaku_app.view.analysis

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.ViewModelFactory
import com.dicoding.warnaku_app.databinding.ActivityAnalysisUserBinding
import com.dicoding.warnaku_app.view.result.ResultActivity

class AnalysisUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalysisUserBinding
    private lateinit var analysisUserViewModel: AnalysisUserViewModel
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

        initializeViewModel()
        setImage()
        setupAnalyzeButton()
    }

    private fun initializeViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        analysisUserViewModel = ViewModelProvider(this, factory)[AnalysisUserViewModel::class.java]

        analysisUserViewModel.customerResponse.observe(this, Observer { response ->
            if (response != null && !response.error!!) {
                Toast.makeText(this, "Customer created successfully", Toast.LENGTH_SHORT).show()
                navigateToResultActivity()
            } else {
                Log.e("AnalysisUserActivity", "Error in response: ${response?.error}")
            }
        })

        analysisUserViewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, "Failed to create customer: $it", Toast.LENGTH_SHORT).show()
                Log.e("AnalysisUserActivity", "Error message: $it")
            }
        })
    }

    private fun setImage() {
        val uriString = intent.getStringExtra("image_uri")
        if (uriString != null) {
            imageUri = Uri.parse(uriString)
            binding.profileImage.setImageURI(imageUri)
        }
    }

    private fun setupAnalyzeButton() {
        binding.btnAnalyze.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val address = binding.addressEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            if (validateInput(name, phone, address, email)) {
                showConfirmationDialog(name, phone, address, email)
            }
        }
    }

    private fun validateInput(name: String, phone: String, address: String, email: String): Boolean {
        return when {
            name.isEmpty() || phone.isEmpty() || address.isEmpty() || email.isEmpty() -> {
                showAlertDialog("Failed to analyze User", "Please fill in all fields")
                false
            }
            !isValidEmail(email) -> {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun showConfirmationDialog(name: String, phone: String, address: String, email: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirm Data")
            setMessage("Name: $name\nPhone: $phone\nAddress: $address\nEmail: $email\n\nIs the information correct?")
            setPositiveButton("Yes") { dialog, _ ->
                createCustomer(name, phone, address, email)
                dialog.dismiss()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun createCustomer(name: String, phone: String, address: String, email: String) {
        analysisUserViewModel.createCustomer(name, phone, address, email)
    }

    private fun navigateToResultActivity() {
        val intent = Intent(this@AnalysisUserActivity, ResultActivity::class.java).apply {
            putExtra("image_uri", imageUri.toString())
        }
        startActivity(intent)
    }
}