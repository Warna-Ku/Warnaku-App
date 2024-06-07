package com.dicoding.warnaku_app.api.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RegisterResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
