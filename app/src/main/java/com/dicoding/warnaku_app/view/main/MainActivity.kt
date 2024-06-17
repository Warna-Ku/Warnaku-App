package com.dicoding.warnaku_app.view.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.warnaku_app.GetViewModelFactory
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.databinding.ActivityMainBinding
import com.dicoding.warnaku_app.view.analysis.AnalysisActivity
import com.dicoding.warnaku_app.view.history.HistoryActivity
import com.dicoding.warnaku_app.view.login.LoginActivity
import com.dicoding.warnaku_app.view.wellcome.WellcomeActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import kotlinx.coroutines.launch
import meow.bottomnavigation.MeowBottomNavigation

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    companion object {
        val DASHBOARD = R.id.dashboard_action
        val ANALYSIS = R.id.scan_action
        val HISTORY = R.id.history_action
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        viewModel = GetViewModelFactory.obtain<MainViewModel>(this)
        checkLoginStatus()
        setUpBottomNavigation()

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "Dashboard"

        binding.logoutButton.setOnClickListener {
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setTitle(getString(R.string.logout_title))
            alertBuilder.setMessage(getString(R.string.logout_message))
            alertBuilder.setPositiveButton(getString(R.string.logout_positive)) { _, _ ->
                logout()
            }
            alertBuilder.setNegativeButton(getString(R.string.logout_negative)) { dialog, _ ->
                dialog.dismiss()
            }
            alertBuilder.create().show()
        }

    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            viewModel.getLoginStatus().collect { isLoggedIn ->
                if (isLoggedIn != true) {
                    checkWelcomePageStatus()
                }
            }
        }
    }

    private fun checkWelcomePageStatus() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            // Pengguna belum melewati halaman Welcome, arahkan ke WellcomeActivity
            val intent = Intent(this, WellcomeActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            moveToLoginActivity()
        }
        // Jika sudah melewati WelcomePage, lanjutkan di MainActivity
    }


    private fun moveToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
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
                    DASHBOARD -> startActivityWithAnimation(Intent(this@MainActivity, MainActivity::class.java))
                    ANALYSIS -> startActivityWithAnimation(Intent(this@MainActivity, AnalysisActivity::class.java))
                    HISTORY -> startActivityWithAnimation(Intent(this@MainActivity, HistoryActivity::class.java))
                }
            }
            show(DASHBOARD)
        }
    }

    private fun startActivityWithAnimation(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        sharedPreferences.edit().putBoolean("is_logged_in", false).apply()

        viewModel.logout()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }
}