package com.dicoding.warnaku_app.view.register

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
import com.dicoding.warnaku_app.databinding.ActivityRegisterBinding
import com.dicoding.warnaku_app.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy{
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        supportActionBar?.apply {
            title = getString(R.string.title_signup)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
    }

    private fun setupAction() {
        binding.apply {
            button.setOnClickListener {
                val name = unameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passEditText.text.toString()
                val confPassword = confPassEditText.text.toString()

                if (name.isEmpty()) {
                    unameEditText.error = getString(R.string.required_field)
                }
                if (email.isEmpty()) {
                    emailEditText.error = getString(R.string.required_field)
                }
                if (password.isEmpty()) {
                    passEditText.error = getString(R.string.required_field)
                }
                if (confPassword.isEmpty()) {
                    confPassEditText.error = getString(R.string.required_field)
                }

                if (password.isNotEmpty() && confPassword.isNotEmpty()) {
                    if (password != confPassword) {
                        confPassEditText.error = getString(R.string.passwords_do_not_match)
                    }
                }

                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    registerViewModel.register(name, email, password).observe(this@RegisterActivity) { result ->
                        when (result) {
                            is FetchResult.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.button.isClickable = false
                            }
                            is FetchResult.Success -> {
                                binding.progressBar.visibility = View.GONE
                                binding.button.isClickable = true
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
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(100)
//        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.unameEditTextLayout, View.ALPHA, 1f).setDuration(100)
//        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
//        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val register = ObjectAnimator.ofFloat(binding.button, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
//                nameTextView,
                nameEditTextLayout,
//                emailTextView,
                emailEditTextLayout,
//                passwordTextView,
                passwordEditTextLayout,
                register
            )
            startDelay = 100
        }.start()
    }

    private fun showAlertDialog(isSuccess: Boolean, errorMessage: String? = null) {
        val builder = AlertDialog.Builder(this)
        if (isSuccess) {
            builder.setTitle(getString(R.string.title_signup))
            builder.setMessage(getString(R.string.email))
            builder.setPositiveButton(getString(R.string.title_login)) { _, _ ->
                moveToLoginActivity()
            }.setOnCancelListener {
                moveToLoginActivity()
            }.create().show()
        } else {
            builder.setTitle(getString(R.string.title_signup))
            builder.setMessage(errorMessage)
            builder.setPositiveButton("OK") { _, _ -> }.create().show()
        }
    }

    private fun moveToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun toLogin(view: View) {
        startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
    }
}