package com.example.demo

import io.restassured.response.Response

fun <T : Any> withPayload(payload: T, block: () -> Response?) {
    StateHolder.setPayload(payload)
    block()
}

object HttpUtils {

    private fun executeAndSetResponse(block: () -> Response): Response {
        val response = block()
        StateHolder.setResponse(response)
        return StateHolder.getResponse()
    }

    fun executePost(url: String): Response {
        return executeAndSetResponse {
            StateHolder.getRequest().post(url)
        }
    }

    fun executePut(url: String): Response {
        return executeAndSetResponse {
            StateHolder.getRequest().put(url)
        }
    }

    fun executeGet(url: String): Response {
        return executeAndSetResponse {
            StateHolder.getRequest().get(url)
        }
    }

    fun executeDelete(url: String): Response {
        return executeAndSetResponse {
            StateHolder.getRequest().delete(url)
        }
    }

}