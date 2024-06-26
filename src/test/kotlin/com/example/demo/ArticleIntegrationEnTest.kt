package com.example.demo

import io.cucumber.java.BeforeStep
import io.cucumber.java.Scenario
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.restassured.RestAssured
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.hamcrest.Matchers.lessThan
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.boot.test.web.server.LocalServerPort

@RunWith(Cucumber::class)
@CucumberOptions(features = ["classpath:features"], plugin = ["pretty", "html:build/reports/cucumber.html"])
class ArticleIntegrationEnTest {
    @LocalServerPort
    private var port: Int? = 0

    @Before
    fun setup(scenario: Scenario) {
        println("## Scenario: ${scenario.name} Running on: ${Thread.currentThread().name}")
        // Required when running in non-parallel mode
        StateHolder.clear()
        RestAssured.baseURI = "http://localhost:$port"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @BeforeStep
    fun step(scenario: Scenario) {
        println("## Scenario: ${scenario.name} Steps running on: ${Thread.currentThread().name}")
    }

    @Given("Create an article with following fields")
    fun createAnArticleWithFollowingFields(payload: Map<String, Any>) {
        withPayload(payload) {
            HttpUtils.executePost("/articles")
        }
        if (StateHolder.getResponse().statusCode == 200) {
            StateHolder.setDifferentiator(StateHolder.extractPathValueFromResponse<String>("id")!!)
        }
    }

    @Then("{string} should not be null")
    fun shouldNotBeNull(path: String) {
        StateHolder.getValidatableResponse().body(path, notNullValue())
    }

    @Then("{string} should be equal to {string}")
    fun shouldBeEqual(path: String, right: String) {
        StateHolder.getValidatableResponse().body(path, equalTo(right))
    }

    @Then("{string} should include {string}")
    fun shouldInclude(path: String, content: String) {
        StateHolder.getValidatableResponse().body(path, containsString(content))
    }

    @Then("{string} should be equal to differentiator")
    fun shouldBeEqualToDifferentiator(path: String) {
        StateHolder.getValidatableResponse().body(path, equalTo(StateHolder.getDifferentiator()))
    }

    @Then("{string} should be same as that in payload")
    fun pathValueShouldBeSameAsPayload(path: String) {
        val valueFromResponse =
            StateHolder.getValidatableResponse().extract().body().path<Comparable<Any>>(path)
        val valueFromPayload = StateHolder.getPayloadAsMap()[path]
        assert(valueFromResponse.equals(valueFromPayload))
    }

    @When("Fetch article by id")
    fun fetchArticleById() {
        val id = StateHolder.getDifferentiator()
        requireNotNull(id)
        HttpUtils.executeGet("/article")
    }

    @Then("Should succeed")
    fun requestShouldSucceed() {
        assertThat(
            StateHolder.getResponse().statusCode,
            allOf(
                greaterThanOrEqualTo(200),
                lessThan(300)
            )
        )
    }

    @Then("Should have status of {int}")
    fun requestShouldHaveStatusCodeOf(statusCode: Int) {
        assertThat(
            StateHolder.getResponse().statusCode,
            equalTo(statusCode)
        )
    }
}