Feature: A user will be able to log out of the application
	In order to log out of the initiated session
	As a user logged into the application
	I want to log out of my Busy account
	
	Background:
		Given the user has logged in his account
		And the user is on main page
		
	
	Scenario: Log out successfully 
		When the user clicks on "Log out" in the top bar
		Then the user should see the login page
