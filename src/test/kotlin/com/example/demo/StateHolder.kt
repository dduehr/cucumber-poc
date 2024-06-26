package com.example.demo

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification

object StateHolder {

    private class State {
        var response: Response? = null
        var request: RequestSpecification? = null
        var payload: Any? = null

        /**
         * The value that uniquely identifies an entity
         */
        var differentiator: Any? = null
    }

    private val store: ThreadLocal<State> = ThreadLocal.withInitial { State() }

    private val state: State
        get() = store.get()

    fun setDifferentiator(value: Any) {
        state.differentiator = value
    }

    fun getDifferentiator(): Any? {
        return state.differentiator
    }

    fun setRequest(request: RequestSpecification) {
        state.request = request
    }

    fun getRequest(): RequestSpecification {
        var specs = state.request
        if (specs == null) {
            specs = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            setRequest(specs)
            return specs
        }
        return specs
    }

    fun setPayload(payload: Any): RequestSpecification {
        val specs = getRequest()
        state.payload = payload
        return specs.body(payload)
    }

    fun getPayloadOrNull(): Any? {
        return state.payload
    }

    fun getPayload(): Any {
        return getPayloadOrNull()!!
    }

    fun <T> getPayloadAs(klass: Class<T>): T {
        return klass.cast(getPayload())
    }

    fun getPayloadAsMap(): Map<*, *> {
        return getPayloadAs(Map::class.java)
    }

    fun setResponse(value: Response) {
        state.response = value
    }

    fun getValidatableResponse(): ValidatableResponse {
        return getResponse().then()
    }

    fun <T> extractPathValueFromResponse(path: String): T? {
        return extractPathValueFrom(path, getValidatableResponse())
    }

    private fun <T> extractPathValueFrom(path: String, response: ValidatableResponse): T? {
        return response.extract().body().path<T>(path)
    }

    fun getResponse() = getResponseOrNull()!!

    fun getResponseOrNull() = state.response

    fun clear() {
        store.remove()
    }
}