Feature: An user will be able to create new services in his company schedule
    In order to set the reservation schedule of my own company
    As a company manager
    I want to be able to add new services to the work schedule of my own company
    
    Background:
        Given I am logged as an user
        And I have a registered company with my account
        And I am on the main page
        And I select my company name "Busy" in the dropdown
    
    Scenario: Add a service without service types previously created
        When I double click the cell of day "15" in the schedule
        Then I should see a message to create at least one service type
    
    Scenario Outline: Add a service with invalid data
        When I add at least one service type
        And I add the roles <roles>
        And I double click the cell of day "15" in the schedule
        Then I should see a dialog to enter the data of the new service
        When I introduce the start time <start_time>
        And I select the service type <service_type>
        And I select the role/roles <roles>
        And I click on 'Save'
        Then I should see an error message in the form
    
        Examples:
            | start_time | service_type | roles |
            | "09:00" | "" | "Juan" |
            | "" | "Tipo 1" | "Juan" |
            | "09:00" | "Tipo 1" | "" |
    
    Scenario Outline: Add a service without repetition successfully
        When I add at least one service type
        And I add the roles <roles>
        And I double click the cell of day "15" in the schedule
        Then I should see a dialog to enter the data of the new service
        When I introduce the start time <start_time>
        And I select the service type <service_type>
        And I select the role/roles <roles>
        And I click on 'Save'
        Then I should see the service created on the day "15"
    
        Examples:
            | start_time | service_type | roles |
            | "09:00" | "" | "Juan" |
            | "09:00" | "Tipo 1" | "" |
    
    Scenario Outline: Add a service with repetition successfully
        When I add at least one service type
        And I add the roles <roles>
        And I double click the cell of day "15" in the schedule
        Then I should see a dialog to enter the data of the new service
        When I introduce the start time <start_time>
        And I select the service type <service_type>
        And I select the role/roles <roles>
        And I select the repetition type <rep_type>
        And I introduce the repetition date until <repetition_days> days after
        And I click on 'Save'
        Then I should see the service created on the day "15"
        And I should see the service until <repetition_days> days after the day "15" with repetition type <rep_type>
    
        Examples:
            | start_time | service_type | roles | rep_type | repetition_days |
            | "9:00" | "Tipo 1" | "Juan" | "Daily" | "3" |
            | "9:00" | "Tipo 1" | "Juan" | "Weekly" | "21" |
            | "9:00" | "Tipo 1" | "Juan, Carlos" | "Daily" | "3" |
            | "9:00" | "Tipo 1" | "Juan, Carlos"  | "Weekly" | "21" |
