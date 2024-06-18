package com.dicoding.warnaku_app.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.ViewModelFactory
import com.dicoding.warnaku_app.data.FetchResult
import com.dicoding.warnaku_app.databinding.ActivityLoginBinding
import com.dicoding.warnaku_app.view.main.MainActivity
import com.dicoding.warnaku_app.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()

        setupViewModel()
        setupAction()
        playAnimation()

    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    private fun setupAction() {
        binding.apply {
            button.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passEditText.text.toString()
                if (email.isEmpty()) {
                    emailEditText.error = getString(R.string.required_field)
                }
                if (password.isEmpty()) {
                    passEditText.error = getString(R.string.required_field)
                }
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    showLoading()
                    loginViewModel.login(email, password).observe(this@LoginActivity) { result ->
                        when (result) {
                            is FetchResult.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.button.isClickable = false
                            }
                            is FetchResult.Success -> {
                                binding.progressBar.visibility = View.GONE
                                binding.button.isClickable = true
                                val token = result.data.loginResult?.token ?: ""
                                val name = result.data.loginResult?.name ?: ""
                                val uid = result.data.loginResult?.uid ?: ""
                                loginViewModel.updateUserName(name)
                                loginViewModel.saveToken(token)
                                loginViewModel.saveUID(uid)
                                showAlertDialog(true)
                            }
                            is FetchResult.Error -> {
                                binding.progressBar.visibility = View.GONE
                                binding.button.isClickable = true
                                showAlertDialog(false, result.error)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_Y, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.button, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailEditTextLayout,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

    private fun showLoading() {
        loginViewModel.isLoading.observe(this@LoginActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showAlertDialog(isSuccess: Boolean, errorMessage: String? = null) {
        val builder = AlertDialog.Builder(this)
        if (isSuccess) {
            val userName = loginViewModel.userName.value ?: "User"
            builder.setTitle(getString(R.string.login_success_title_alert))
            builder.setMessage(getString(R.string.login_success_desc_alert,userName))
            builder.setPositiveButton("OK") { _, _ ->
                moveToMainActivity()
            }.setOnCancelListener {
                moveToMainActivity()
            }.create().show()
        } else {
            builder.setTitle(getString(R.string.login_failed_title))
            builder.setMessage(errorMessage)
            builder.setPositiveButton("OK") { _, _ -> }.create().show()
        }
    }


    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun toRegister(view: View) {
        startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
    }

}