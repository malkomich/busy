Feature: The user or admin will be able to log in the system
In order to use the functionality of Busy
As a registered user
I want to log in the system with my credentials

Scenario Outline: User log in succesfully 
Given user is on login page
When user introduces email <email>
And user introduces password <password>
And user press Log In button
Then the Main page is shown

Examples:
|email|password|
|"admin@busy.com"|"123456"|

Scenario Outline: User log in wrong
Given user is on login page
When user introduces email <email>
And user introduces password <password>
And user press Log In button
Then an error message is shown

Examples:
|email|password|
|"admin@busy.com"|""|
|""|"123456"|
|"wrong_user@gmail.com"|"123456"|
|"admin@busy.com"|"wrong_pass"|