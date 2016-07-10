Feature: The manager of a company will be able to register new employees in his company
    In order to control the workload of my employees
    As a company manager
    I want to add an employee to the company
    
    Background:
        Given I am logged as an user
        And I have a registered company with my account
        And I am on the main page
    
    Scenario Outline: Add a new employee successfully
        When I select my company name "Busy" in the dropdown
        Then I should see an empty list of employees
        When I click on Add
        Then I should see a window with a form to input the data
        When I enter the name <name>
        And I enter the email <email>
        And I enter the phone number <phone>
        And I click on Save
        Then I should see a confirmation message
        And I should see <name> in the list of employees
        
        Examples:
            |name|email|phone|
            |"Pepito"|"pepito@domain.x"|"666789012"|

    Scenario Outline: Add a new employee unsuccessfully
        When I select my company name "Busy" in the dropdown
        Then I should see an empty list of employees
        When I click on Add
        Then I should see a window with a form to input the data
        When I enter the name <name>
        And I enter the email <email>
        And I enter the phone number <phone>
        And I click on Save
        Then I should see an error message
        
        Examples:
            |name|email|phone|
            |""|"pepito@domain.x"|"666789012"|
            |"Pepito"|""|"666789012"|
            |"Pepito"|"pepito@domain.x"|""|
            |"Pepito"|"wrong_email.x"|"666789012"|
            |"Pepito"|"pepito@domain.x"|"5"|
