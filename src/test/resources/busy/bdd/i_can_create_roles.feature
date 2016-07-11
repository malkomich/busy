Feature: The manager of a company will be able to register new employees in his company
    In order to control the workload of my employees
    As a company manager
    I want to add an employee to the company
    
    Background:
        Given I am logged as an user
        And I have a registered company with my account
        And I am on the main page
    
    @ignore
    Scenario Outline: Add a new employee successfully
        When I select my company name "Busy" in the dropdown
        And I click on the roles dropdown
        Then I should see the list of employees with just my name "Juan Carlos"
        When I click on Add
        Then I should see a window with a form to input the data
        When I enter the firstname <firstname>
        And I enter the lastname <lastname>
        And I enter the email <email>
        And I enter the phone number <phone>
        And I click on Save
        Then I should see a confirmation message
        And I should see <firstname> in the list of employees
        
        Examples:
            | firstname | lastname | email | phone |
            | "Pepito" | "González" | "pepito@domain.x" | "666789012" |

    @ignore
    Scenario Outline: Add a new employee unsuccessfully
        When I select my company name "Busy" in the dropdown
        And I click on the roles dropdown
        Then I should see the list of employees with just my name "Juan Carlos"
        When I click on Add
        Then I should see a window with a form to input the data
        When I enter the firstname <firstname>
        And I enter the lastname <lastname>
        And I enter the email <email>
        And I enter the phone number <phone>
        And I click on Save
        Then I should see an error message
        
        Examples:
            | firstname | lastname | email | phone |
            | "" | "González" | "pepito@domain.x" | "666789012" |
            | "Pepito" | "" | "pepito@domain.x" | "666789012" |
            | "Pepito" | "González" | "" | "666789012" |
            | "Pepito" | "González" | "pepito@domain.x" | "" |
            | "Pepito" | "González" | "wrong_email.x" | "666789012" |
            | "Pepito" | "González" | "pepito@domain.x" | "5" |
