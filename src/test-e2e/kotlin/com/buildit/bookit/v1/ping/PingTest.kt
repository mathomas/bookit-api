package com.buildit.bookit.v1.ping

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.json.JSONObject
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder

object PingControllerTests : Spek(
    {
        val restTemplate = TestRestTemplate(RestTemplateBuilder().rootUri("https://integration-bookit-api.buildit.tools").build())
        describe("/v1/ping")
        {
            on("GET")
            {
                val response = restTemplate.getForEntity("/v1/ping", String::class.java)
                it("should return UP")
                {
                    val expected =
                        """
                        {
                            "status": "UP"
                        }
                        """
                    JSONAssert.assertEquals(expected, JSONObject(response.body), JSONCompareMode.STRICT)
                }
            }
        }
    })
