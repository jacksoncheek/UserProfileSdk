package com.jacksoncheek.userprofile.core.data.model

import com.squareup.moshi.Json

data class Birthday(

	@Json(name="dmy")
	val dmy: String,

	@Json(name="mdy")
	val mdy: String,

	@Json(name="raw")
	val raw: Int
)