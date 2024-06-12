package com.dicoding.warnaku_app.view.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.warnaku_app.GetViewModelFactory
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.databinding.ActivityMainBinding
import com.dicoding.warnaku_app.view.analysis.AnalysisActivity
import com.dicoding.warnaku_app.view.history.HistoryActivity
import com.dicoding.warnaku_app.view.login.LoginActivity
import com.dicoding.warnaku_app.wellcome.WellcomeActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    companion object {
        const val DASHBOARD = 1
        const val ANALYSIS = 2
        const val HISTORY = 3
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        viewModel = GetViewModelFactory.obtain<MainViewModel>(this)
        checkLoginStatus()
        setUpBottomNavigation()

    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            viewModel.getLoginStatus().collect { isLoggedIn ->
                if (isLoggedIn != true) {
                    moveToLoginActivity()
                } else {
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
            CurvedBottomNavigation.Model(
                DASHBOARD, getString(R.string.dashboard),
                R.drawable.ic_home
            ),
            CurvedBottomNavigation.Model(
                ANALYSIS, getString(R.string.analysis),
                R.drawable.ic_analysis
            ),
            CurvedBottomNavigation.Model(
                HISTORY, getString(R.string.history),
                R.drawable.ic_history
            ),
        )

        binding.bottomNavigation.apply {
            bottomNavigationItems.forEach { add(it) }
            setOnClickMenuListener {
                when (it.id) {
                    DASHBOARD -> {
                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
                    }
                     ANALYSIS -> {
                         startActivity(Intent(this@MainActivity, AnalysisActivity::class.java))
                    }
                    HISTORY -> {
                        startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
                    }
                }
            }
            show(DASHBOARD)
        }
    }
}