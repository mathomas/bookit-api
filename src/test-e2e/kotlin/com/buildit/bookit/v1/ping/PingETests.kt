package com.buildit.bookit.v1.ping

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.json.JSONObject
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder

/**
 * Test /v1/ping like a black box
 */
object PingETests : Spek(
{
    val uri : String = System.getenv("ENDPOINT_URI") ?: "http://localhost:8080"
    val restTemplate = TestRestTemplate(RestTemplateBuilder().rootUri(uri).build())
    describe("/v1/ping")
    {
        on("GET")
        {
            val response = restTemplate.getForEntity("/v1/ping", String::class.java)
            it("should return UP")
            {
                val jsonResponse = JSONObject(response.body)
                val status = jsonResponse.get("status")
                expect(status).to.be.equal("UP")
            }
        }
    }
})
