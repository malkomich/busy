Feature: The user or admin will be able to log in the system
	In order to use the functionality of Busy
	As a registered user
	I want to log in the system with my credentials
	
	Scenario Outline: User log in succesfully 
		Given the user is on login page
		When the user introduces email <email>
		And the user introduces password <password>
		And the user press Log In button
		Then the Main page is shown
		
		Examples:
			|email|password|
			|"user@domain.com"|"pass"|
		
	Scenario Outline: User log in wrong
		Given the user is on login page
		When the user introduces email <email>
		And the user introduces password <password>
		And the user press Log In button
		Then an error message in the Login page is shown
		
		Examples:
			|email|password|
			|""|""|
			|"user@domain.com"|""|
			|""|"pass"|
			|"wrong_user@domain.com"|"pass"|
			|"user@domain.com"|"wrong_pass"|