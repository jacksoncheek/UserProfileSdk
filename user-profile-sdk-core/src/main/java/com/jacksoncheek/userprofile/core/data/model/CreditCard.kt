package com.jacksoncheek.userprofile.core.data.model

import com.squareup.moshi.Json

data class CreditCard(

	@Json(name="number")
	val number: String,

	@Json(name="security")
	val security: Int,

	@Json(name="pin")
	val pin: Int,

	@Json(name="expiration")
	val expiration: String
)