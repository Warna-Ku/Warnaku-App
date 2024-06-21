package com.dicoding.warnaku_app.api.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CustomersResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: Customer? = null,

	@field:SerializedName("analysisReports")
	val analysisReports: List<AnalysisReportsItem?>? = null
) : Parcelable

@Parcelize
data class ColorsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null
) : Parcelable

@Parcelize
data class AnalysisReportsItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("season")
	val season: String? = null,

	@field:SerializedName("worker")
	val worker: Worker? = null,

	@field:SerializedName("colors")
	val colors: List<ColorsItem?>? = null,

	@field:SerializedName("paletteDescription")
	val paletteDescription: String? = null,

	@field:SerializedName("paletteImg")
	val paletteImg: String? = null,

	@field:SerializedName("customer")
	val customer: Customer? = null
) : Parcelable

@Parcelize
data class Worker(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable

@Parcelize
data class Customer(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("customerID")
	val customerID: Int? = null,

	@field:SerializedName("faceImageURL")
	val faceImageURL: String? = null
) : Parcelable
