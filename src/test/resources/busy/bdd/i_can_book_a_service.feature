Feature: An user will be able to make a new booking in a service
    In order to book a service in any company
    As a user who wants a service
    I want to be able to book a service time in a specific company
    
    Background:
        Given I am logged as an user
        And I have a registered company with my account
        And I am on the info page of a company
    
    Scenario Outline: Book a service successfully
        When I click on 'Bookings'
        Then I should see a list with the branches of the company
        When I select a branch
        Then I should see a calendar with the available services
        When I select the day <day> in the calendar
        Then I should see a dialog with the available times and workers
        When I select the time <time>
        And I select the worker <worker>
        Then I should see a notification of the booking done
        When I select the day <day> in the calendar
        Then I shouldn't see the time <time> in the time options
        
        Examples:
            | day | time | worker |
            | "15" | "09:00" | "Juan" |
    
    Scenario Outline: Book a service in an already booked time
        When I click on 'Bookings'
        Then I should see a list with the branches of the company
        When I select a branch
        Then I should see a calendar with the available services
        When I select the day <day> in the calendar
        Then I should see a dialog with the available times and workers
        When the time <time> is booked by anyone else
        And I select the time <time>
        And I select the worker <worker>
        Then I should see an error message in the dialog
        And I shouldn't see the time <time> in the time options
    
        Examples:
            | day | time | worker |
            | "15" | "09:00" | "Juan" |
    
    Scenario: Book a service in an inactive company
        When the company is inactive
        And I click on 'Bookings'
        Then I should see a message with the error
