package com.dicoding.warnaku_app.view.analysis

import android.widget.Toast
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
            onBtnNextClick()
        }
    }

    private fun onBtnNextClick() {
        val name = binding.nameEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()
        val address = binding.addressEditText.text.toString()
        val email = binding.emailEditText.text.toString()

        if (areFieldsEmpty(name, phone, address, email)) {
            showAlertDialog("Failed to analyze User", "Please fill in all fields")
        } else if (!isValidEmail(email)) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
        } else {
            showConfirmationDialog(name, phone, address, email)
        }
    }

    private fun areFieldsEmpty(name: String, phone: String, address: String, email: String): Boolean {
        return name.isEmpty() || phone.isEmpty() || address.isEmpty() || email.isEmpty()
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
                navigateToResultActivity(name, phone, address, email)
                dialog.dismiss()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun navigateToResultActivity(name: String, phone: String, address: String, email: String) {
        val intent = Intent(this@AnalysisUserActivity, ResultActivity::class.java).apply {
            putExtra("image_uri", imageUri.toString())
            putExtra("name", name)
            putExtra("phone", phone)
            putExtra("address", address)
            putExtra("email", email)
        }
        startActivity(intent)
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