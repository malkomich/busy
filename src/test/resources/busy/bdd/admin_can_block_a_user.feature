Feature: An admin will be able to block useraccounts
    In order to avoid the misuse of the application
    As a registered admin
    I want to deny the use of the application to a user

    Background:
        Given I am logged as an admin
        And I am on the main admin page

    Scenario: Block a user successfully
        When I click on users section
        Then I should see a detailed list of all the users in the system
        And I should see the user "Usuario1" as active
        When I click on Block in the row of user "Usuario1"
        Then I should see a confirmation message
        And I should see the user "Usuario1" as blocked