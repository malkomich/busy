Feature: The user will be able to see the schedule status of his company
    In order to control the schedule of my company
    As a company manager
    I want to see the bookings made in my company in a calendar view

    Background:
        Given I am logged as an user
        And I have a registered company with my account
        And I am on the main page

    Scenario: Organizational display of the company calendar successfully
        When I select my company name 'Busy' in the dropdown
        Then I should see a calendar with the bookings already made
        When I select a day
        Then I should see the bookings of this day in a more detailed way
