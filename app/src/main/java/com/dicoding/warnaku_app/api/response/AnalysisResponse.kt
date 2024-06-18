package com.dicoding.warnaku_app.api.response

import com.google.gson.annotations.SerializedName

data class AnalysisResponse(

	@field:SerializedName("resultAnalysis")
	val resultAnalysis: ResultAnalysis? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ColorsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null
)

data class Customer(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class Worker(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class ResultAnalysis(

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
)
