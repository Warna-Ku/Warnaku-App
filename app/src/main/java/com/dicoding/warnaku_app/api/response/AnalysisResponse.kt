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
