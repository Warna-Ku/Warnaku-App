package com.dicoding.warnaku_app.view.profile

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.ViewModelFactory
import com.dicoding.warnaku_app.api.ApiConfig
import com.dicoding.warnaku_app.api.ApiService
import com.dicoding.warnaku_app.data.UserPreference
import com.dicoding.warnaku_app.data.dataStore
import com.dicoding.warnaku_app.data.repository.UserRepository
import com.dicoding.warnaku_app.databinding.ActivityProfileBinding
import com.dicoding.warnaku_app.view.login.LoginViewModel

class ProfileActivity : AppCompatActivity() {
    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "Profile"

        setupViewModel()

        profileViewModel.loadLoginResult()

        profileViewModel.loginResult.observe(this, Observer { loginResult ->
            loginResult?.let {
                binding.textViewName.text = it.name
                binding.textViewIdUser.text = "ID: ${it.uid}"
            }
        })

    }
    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }
}