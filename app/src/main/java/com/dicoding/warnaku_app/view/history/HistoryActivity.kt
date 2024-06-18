package com.dicoding.warnaku_app.view.history

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.ViewModelFactory
import com.dicoding.warnaku_app.api.ApiConfig
import com.dicoding.warnaku_app.api.ApiService
import com.dicoding.warnaku_app.data.adapter.CustomersAdapter
import com.dicoding.warnaku_app.databinding.ActivityHistoryBinding
import com.dicoding.warnaku_app.view.analysis.AnalysisActivity
import com.dicoding.warnaku_app.view.main.MainActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private val binding: ActivityHistoryBinding by lazy {
        ActivityHistoryBinding.inflate(layoutInflater)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomersAdapter
    private lateinit var viewModel: HistoryViewModel
    private val workerID = "worker_id_here"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        val titleTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        titleTextView?.text = "History"

        setUpRecyclerView()
        setUpBottomNavigation()
        fetchData()
    }

    private fun setUpRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomersAdapter(emptyList()) // Initialize with empty list
        recyclerView.adapter = adapter
    }

    private fun fetchData() {
        val factory = ViewModelFactory.getInstance(applicationContext)
        viewModel = ViewModelProvider(this, factory).get(HistoryViewModel::class.java)


        viewModel.customers.observe(this, Observer { customers ->
            customers?.let {
                adapter.setData(it)
            }
        })

        viewModel.error.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.fetchCustomers()
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