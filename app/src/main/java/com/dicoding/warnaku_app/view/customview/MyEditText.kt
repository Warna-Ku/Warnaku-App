package com.dicoding.warnaku_app.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.dicoding.warnaku_app.R
import com.google.android.material.textfield.TextInputEditText

class MyEditText: TextInputEditText {
    private var errorBackgroundDrawable: Drawable? = null
    private var defaultBackgroundDrawable: Drawable? = null
    private var hasError: Boolean = false
    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize()
    }

    private fun initialize() {
        errorBackgroundDrawable = ContextCompat.getDrawable(context, R.drawable.stroke_error)
        defaultBackgroundDrawable = ContextCompat.getDrawable(context, R.drawable.stroke_default)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val inputText = charSequence.toString()
                validateInput(inputText)
            }

            override fun afterTextChanged(editable: Editable?) {
                val inputText = editable.toString()
                validateInput(inputText)
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (hasError) {
            errorBackgroundDrawable
        } else {
            defaultBackgroundDrawable
        }
    }

    private fun validateInput(input: String) {
        when(inputType){
            EMAIL_TYPE->{
                if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
                    error = "Masukan valid input"
                    hasError = true
                }else{
                    hasError = false
                }
            }
            PASSWORD_TYPE->{
                hasError = if(input.length < 8){
                    setError("Isi Passaword minimal 8 karakter", null)
                    true
                }else{
                    false
                }
            }
        }
    }

    companion object{
        const val EMAIL_TYPE = 0x00000021
        const val PASSWORD_TYPE = 0x00000081
    }
}