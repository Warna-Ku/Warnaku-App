package com.dicoding.warnaku_app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.warnaku_app.databinding.ActivityMainBinding
import com.dicoding.warnaku_app.view.analysis.AnalysisActivity
import com.dicoding.warnaku_app.view.history.HistoryActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {
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

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "Home"

        setUpBottomNavigation()

    }

    private fun setUpBottomNavigation() {
        val bottomNavigationItems = mutableListOf(
            CurvedBottomNavigation.Model(DASHBOARD, getString(R.string.dashboard), R.drawable.ic_home),
            CurvedBottomNavigation.Model(ANALYSIS, getString(R.string.analysis), R.drawable.ic_analysis),
            CurvedBottomNavigation.Model(HISTORY, getString(R.string.history), R.drawable.ic_history),
        )

        binding.bottomNavigation.apply {
            bottomNavigationItems.forEach { add(it) }
            setOnClickMenuListener {
                when (it.id) {
                    DASHBOARD -> {
                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
                    }
                     ANALYSIS-> {
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