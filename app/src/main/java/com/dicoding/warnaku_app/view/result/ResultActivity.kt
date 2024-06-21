package com.dicoding.warnaku_app.view.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.data.UserPreference
import com.dicoding.warnaku_app.data.dataStore
import com.dicoding.warnaku_app.databinding.ActivityResultBinding
import com.dicoding.warnaku_app.view.main.MainActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Assuming you have a way to get the UserPreference instance
        userPreference = UserPreference.getInstance(dataStore)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "Result"

        val uriString = intent.getStringExtra("image_uri")
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val address = intent.getStringExtra("address")
        val email = intent.getStringExtra("email")

        if (uriString != null) {
            val imageUri = Uri.parse(uriString)
            binding.userImageResult.setImageURI(imageUri)
        }

        binding.tvCustomerName.text = "Name: $name"
        binding.tvNumberCustomer.text = "Phone: $phone"
        binding.tvEmailCustomer.text = "Email: $email"
        binding.tvAddressCustomer.text = "Address: $address"

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

        // Retrieve and set customerID
        lifecycleScope.launch {
            userPreference.getCustomerID().collect { customerID ->
                binding.tvTitleCustomer.text = "Customer ID: $customerID"
            }
        }
    }
}
