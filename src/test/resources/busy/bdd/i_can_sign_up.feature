Feature: A new user will be able to sign up in the system
	In order to be allowed to use the functionality of Busy
	As a not registered user
	I want to sign up in the system with my data
	
	Background:
		Given the user is on login page
		When user clicks on "Sign Up"
		Then the register page is shown
	
	Scenario Outline: Sign up succesfully 
		When user introduces the first name <firstname>
		And user introduces the last name <lastname>
		And user introduces the email <email>
		And user introduces the nif <nif>
		And user selects the country <country>
		And user selects the city <city>
		And user introduces the zip code <zipcode>
		And user introduces the phone number <phone>
		And user introduces the password <password>
		And user introduces the password confirmation <passwordconfirm>
		And user press Sign Up button
		Then a success message is shown
		And an email to confirm account is sent
		When user click on "Validate" in the email
		Then a confirm page is shown
		And login page is shown automatically
		
		Examples:
			|firstname|lastname|email|nif|country|city|zipcode|phone|password|passwordconfirm|
			|"Nombre"|"Apellidos"|"user@domain.com"|"72256481D"|"España"|"Valladolid"|"47007"|"654321987"|"pass"|"pass"|
			|"Nombre"|"Apellidos"|"user@domain.com"|""|""|""|""|""|"pass"|"pass"|
		
	Scenario Outline: Sign up error
		When user introduces the firstname <firstname>
		And user introduces the lastname <lastname>
		And user introduces the email <email>
		And user introduces the nif <nif>
		And user selects the country <country>
		And user selects the city <city>
		And user introduces the zip code <zipcode>
		And user introduces the phone <phone>
		And user introduces the password <password>
		And user introduces the password confirmation <passwordconfirm>
		And user press Sign Up button
		Then an error message is shown
		
		Examples:
			|firstname|lastname|email|nif|country|city|zipcode|phone|password|passwordconfirm|
			|""|""|""|""|""|""|""|""|""|""|
			|""|"Apellidos"|"user@domain.com"|"72256481D"|"España"|"Valladolid"|"47007"|"654321987"|"pass"|"pass"|
			|"Nombre"|""|"user@domain.com"|"72256481D"|"España"|"Valladolid"|"47007"|"654321987"|"pass"|"pass"|
			|"Nombre"|"Apellidos"|""|"72256481D"|"España"|"Valladolid"|"47007"|"654321987"|"pass"|"pass"|
			|"Nombre"|"Apellidos"|"user@domain.com"|"72256481D"|"España"|"Valladolid"|"47007"|"654321987"|""|"pass"|
			|"Nombre"|"Apellidos"|"user@domain.com"|"72256481D"|"España"|"Valladolid"|"47007"|"654321987"|"pass"|""|
			|"Nombre"|"Apellidos"|"user@domain.com"|"72256481D"|"España"|"Valladolid"|"47007"|"654321987"|"pass"|"wrong_pass"|
			|"Nombre"|"Apellidos"|"wrong_email.com"|"72256481D"|"España"|"Valladolid"|"47007"|"654321987"|"pass"|"pass"|
			|"Nombre"|"Apellidos"|"user@domain.com"|"72256481D"|"España"|"Valladolid"|"47007"|"5"|"pass"|"pass"|
			|"Nombre"|"Apellidos"|"user@domain.com"|"ABCDEFGHI"|"España"|"Valladolid"|"47007"|"654321987"|"pass"|"pass"|
			|"Nombre"|"Apellidos"|"user@domain.com"|"72256481D"|"España"|"Valladolid"|"123456789AB"|"654321987"|"pass"|"pass"|