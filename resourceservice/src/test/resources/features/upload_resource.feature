Feature: Upload resource
  As a user
  I want save audio file and all information about it

  Scenario: Upload audio file
    Given new audio file
    When user uploads audio file
    And resource id is returned
    And user can get audio file by id
    And user can get audio metadata by resource id
