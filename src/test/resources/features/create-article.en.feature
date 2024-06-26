Feature: Create Article.

  Scenario: As a user, I should be able to create new article.
    Given Create an article with following fields
      | title | Cucumber                                                |
      | body  | Lorem ipsum dolor sit amet, consectetur adipiscing elit |
    Then Should have status of 200
    And "id" should not be null
    And "title" should not be null
    And "body" should not be null
    And "lastUpdatedOn" should not be null
    And "createdOn" should not be null
    And "title" should be equal to "Cucumber"
    And "title" should be same as that in payload


  Scenario: As a user, I should not be allowed to create articles with invalid fields.
  Error message should show which field is the cause of the errors.
    Given Create an article with following fields
      | title |                                                         |
      | body  | Lorem ipsum dolor sit amet, consectetur adipiscing elit |
    Then Should have status of 400
    And "body.detail" should include "Title is empty"