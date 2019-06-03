package com.jacksoncheek.userprofile.core.data.model

import com.squareup.moshi.Json

data class User(

	@Json(name="birthday")
	val birthday: Birthday,

	@Json(name="password")
	val password: String,

	@Json(name="credit_card")
	val creditCard: CreditCard,

	@Json(name="gender")
	val gender: String,

	@Json(name="phone")
	val phone: String,

	@Json(name="surname")
	val surname: String,

	@Json(name="name")
	val name: String,

	@Json(name="photo")
	val photo: String,

	@Json(name="region")
	val region: String,

	@Json(name="title")
	val title: String,

	@Json(name="age")
	val age: Int,

	@Json(name="email")
	val email: String
)