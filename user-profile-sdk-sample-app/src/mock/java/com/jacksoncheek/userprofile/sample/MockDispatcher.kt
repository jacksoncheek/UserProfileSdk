package com.jacksoncheek.userprofile.sample

import okhttp3.Headers
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer

class MockDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {

        val headers = Headers.of(
            mapOf(
                "date" to "Fri, 07 Jun 2019 02:38:49 GMT",
                "server" to "Apache",
                "x-powered-by" to "PHP/5.4.45",
                "access-control-allow-origin" to "*",
                "access-control-allow-methods" to "GET",
                "content-type" to "application/json; charset=utf-8"
            )
        ).toMultimap()

        val body = """
            {
              "name": "Jackson",
              "surname": "Cheek",
              "gender": "male",
              "region": "United States",
              "age": 29,
              "title": "mr",
              "phone": "(123) 456 7890",
              "birthday": {
                "dmy": "01/01/0101",
                "mdy": "01/01/0101",
                "raw": 101010101
              },
              "email": "jackson.cheek@gmail.com",
              "password": "password",
              "credit_card": {
                "expiration": "01/01",
                "number": "1234-1234-1234-1234",
                "pin": 1234,
                "security": 123
              },
              "photo": "https://i.imgur.com/otoB2tp.jpg"
            }
        """.trimIndent().toByteArray()

        return MockResponse().apply {
            setResponseCode(200)

            headers.forEach {
                val headerKey = it.key

                it.value.forEach { headerValue ->
                    addHeader(headerKey, headerValue)
                }
            }

            setBody(Buffer().readFrom(body.inputStream()))
        }
    }
}