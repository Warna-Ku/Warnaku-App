package com.dicoding.warnaku_app.wellcome

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.view.login.LoginActivity

class WellcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wellcome)
        supportActionBar?.hide()

//        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
//        viewPager.adapter = ViewPagerAdapter(this)
        //

        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            setContentView(R.layout.activity_wellcome)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = ViewPagerAdapter(this)

            // Set isFirstRun ke false setelah pengguna melewati WellcomeActivity
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstRun", false)
            editor.apply()
        } else {
            // Pengguna sudah pernah membuka aplikasi, langsung ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}