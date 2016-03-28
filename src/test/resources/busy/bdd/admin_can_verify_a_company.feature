Feature: An admin will be able to verify companies
    In order to verify wether the data of a new company is right
    As a registered admin
    I want to be able to confirm business registers
    
    Background:
        Given I am logged as an admin
        And I am on the main admin page
    
    Scenario: Approve a company successfully
        When I click on "Verify new companies"
        And I select the pending company 'Boom S.A.'
        Then I should see the company 'Boom S.A.' as blocked or not active
        When I click on "Approve company"
        Then a verify notification is sent to the manager of the company
        And an email is sent to the manager of the company
        When I click on "Verify new companies"
        Then the company 'Boom S.A.' is shown as active
        