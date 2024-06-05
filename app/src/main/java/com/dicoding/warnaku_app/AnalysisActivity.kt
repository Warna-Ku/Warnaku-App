package com.dicoding.warnaku_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AnalysisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_analysis)

        val buttonAnalyze: Button = findViewById(R.id.btn_analyze)
        buttonAnalyze.setOnClickListener {
            val intent = Intent(this, AnalysisUserActivity::class.java)
            startActivity(intent)
        }
    }
}