package com.dicoding.warnaku_app.api.response

import com.google.gson.annotations.SerializedName

data class AnalysisResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("Colors")
	val colors: List<ColorsItem?>? = null,

	@field:SerializedName("PalImg")
	val palImg: String? = null,

	@field:SerializedName("PalDesc")
	val palDesc: String? = null,

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("user_palette")
	val userPalette: String? = null
)

data class ColorsItem(

	@field:SerializedName("Desc")
	val desc: String? = null,

	@field:SerializedName("Img")
	val img: String? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("Name")
	val name: String? = null
)
