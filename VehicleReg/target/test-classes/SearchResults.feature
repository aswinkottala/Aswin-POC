Feature: Peform Free Car Check

  Scenario: Validate Search Results and compare with the output file
    Given I can navigate to cartaxcheck homepage
    When I look for the vehicle registrations from the input file and search on the cartaxcheck website
    Then check for expected vehicle details


