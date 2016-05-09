Feature: An user will be able to create new services in his company schedule
    In order to set the reservation schedule of my own company
    As a company manager
    I want to be able to add new services to the work schedule of my own company
    
    Background:
        Given I am logged as an user
        And I have a registered company with my account
        And I have selected the name of my company in the upper dropdown
    
    Scenario: Add a service without service types previously created
        When I select a day cell in the schedule
        Then I should see a message to create at least one service type

    Scenario: Add a service without roles previously created
        When I select a day cell in the schedule
        Then I should see a message to create at least one role
    
    Scenario Outline: Add a service with invalid data
        When I select a day cell in the schedule
        Then I should see a dialog to enter the data of the new service
        When I write the init hour <start_hour>
        And I select the service type <service_type>
        And I select the role/roles <roles>
        And I select the repetition <repetition>
        And I click on 'Save'
        Then I should see an error message in the form
    
        Examples:
            | start_hour | service_type | roles | repetition |
            | "09:00" | "" | "Juan" | "" |
            | "09:00" | "Tipo 1" | "" | "" |
    
    Scenario Outline: Add a service without repetition successfully
        When I select a day cell in the schedule
        Then I should see a dialog to enter the data of the new service
        When I write the init hour <start_hour>
        And I select the service type <service_type>
        And I select the role/roles <roles>
        And I select the repetition <repetition>
        And I click on 'Save'
        Then I should see the service created on the selected day
    
        Examples:
            | start_hour | service_type | roles | repetition |
            | "09:00" | "" | "Juan" | "" |
            | "09:00" | "Tipo 1" | "" | "" |
    
    Scenario Outline: Add a service with repetition successfully
        When I select a day cell in the schedule
        Then I should see a dialog to enter the data of the new service
        When I write the init hour <start_hour>
        And I select the service type <service_type>
        And I select the role/roles <roles>
        And I select the repetition <repetition>
        And I click on 'Save'
        Then I should see the service created on the selected day and subsequent days according to the repeat type <repetition>
    
        Examples:
            | start_hour | service_type | roles | repetition |
            | "9:00" | "Tipo 1" | "Juan" | "Diaria" |
            | "9:00" | "Tipo 1" | "Juan" | "Semanal" |
            | "9:00" | "Tipo 1" | "Juan" | "Mensual" |
            | "9:00" | "Tipo 1" | "Juan", "Carlos" | "Diaria" |
            | "9:00" | "Tipo 1" | "Juan", "Carlos"  | "Semanal" |
            | "9:00" | "Tipo 1" | "Juan", "Carlos"  | "Mensual" |
