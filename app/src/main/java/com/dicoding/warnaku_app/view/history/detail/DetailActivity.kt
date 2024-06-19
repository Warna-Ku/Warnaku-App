package com.dicoding.warnaku_app.view.history.detail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.databinding.ActivityDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

    }
    fun showBottomSheet(view: View) {
        val showResult = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet, null)
        showResult.setContentView(view)
        showResult.show()
    }
}