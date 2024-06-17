package com.dicoding.warnaku_app.view.history

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.databinding.ActivityHistoryBinding
import com.dicoding.warnaku_app.view.analysis.AnalysisActivity
import com.dicoding.warnaku_app.view.main.MainActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class HistoryActivity : AppCompatActivity() {
    private val binding: ActivityHistoryBinding by lazy{
        ActivityHistoryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "History"

        setUpBottomNavigation()
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
                    DASHBOARD -> startActivityWithAnimation(Intent(this@HistoryActivity, MainActivity::class.java))
                    ANALYSIS -> startActivityWithAnimation(Intent(this@HistoryActivity, AnalysisActivity::class.java))
                    HISTORY -> startActivityWithAnimation(Intent(this@HistoryActivity, HistoryActivity::class.java))
                }
            }
            show(HISTORY)
        }
    }

    private fun startActivityWithAnimation(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
    companion object {
        val DASHBOARD = R.id.dashboard_action
        val ANALYSIS = R.id.scan_action
        val HISTORY = R.id.history_action
    }
}