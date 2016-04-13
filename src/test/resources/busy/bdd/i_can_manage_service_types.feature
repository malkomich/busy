Feature: The manager of a company will be able to manage the service types of his company
    In order to offer different services to the users
    As a company manager
    I want to add, modify and delete service types
    
    Background:
        Given I am logged as an user
        And I have a registered company with my account
        And I am in my main page
    
    Scenario Outline: Add a new service type successfully
        When I select my company name "Busy" in the dropdown
        Then I should see an empty list of services
        When I click on Add
        Then I should see a window with a form to input the data
        When I enter the name <name>
        And I enter the description <description>
        And I enter the number of maximum bookings per role <max_bookings>
        And I enter the duration of the service <duration>
        And I click on Accept
        Then I should see <name> in the list of services with the data <description>, <max_bookings>, <duration>
        
        Examples:
            |name|description|max_bookings|duration|
            |"Tipo 1"|"Un tipo de servicio"|"2"|"60"|
        
    Scenario Outline: Add a new service type which is duplicated
        When I add a new type of service <name>
        And I select my company name "Busy" in the dropdown
        Then I should see <name> in the list of services
        When I click on Add
        Then I should see a window with a form to input the data
        When I enter the name <name>
        And I enter the description <description>
        And I enter the number of maximum bookings per role <max_bookings>
        And I enter the duration of the service <duration>
        And I click on Accept
        Then I should see an error in the name field
        
        Examples:
            |name|description|max_bookings|duration|
            |"Tipo 1"|"Un tipo de servicio"|"2"|"60"|
            
    Scenario Outline: Modify an existing service type
        When I add a new type of service <name>
        And I select my company name "Busy" in the dropdown
        Then I should see <name> in the list of services
        When I click on Modify
        Then I should see a window with a form to input the data
        When I enter the name <name>
        And I enter the description <description>
        And I enter the number of maximum bookings per role <max_bookings>
        And I enter the duration of the service <duration>
        And I click on Accept
        Then I should see <name> in the list of services with the data <description>, <max_bookings>, <duration>
        
        Examples:
            |name|description|max_bookings|duration|
            |"Tipo 1"|"Descripción modificada"|"3"|"120"|
            
    Scenario: Delete successfully of a service type
        When I add a new type of service "Tipo 1"
        And I select my company name 'Busy' in the dropdown
        Then I should see "Tipo 1" in the list of services
        When I click on Delete
        Then I should see an empty list of services
        
    Scenario: Delete failed of a service type
        When I add a new type of service "Tipo 1"
        And I add a service with a booking in the service type "Tipo 1"
        And I select my company name 'Busy' in the dropdown
        Then I should see "Tipo 1" in the list of services
        When I click on Delete
        Then I should see "Tipo 1" in the list of services
        