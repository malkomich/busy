Feature: A user will be able to register a new company
	In order to offer services and manage his company
	As a registered user
	I want to register a company with the tax data
	
	Background:
		Given the user has logged in his account
		And the user is on main page
		When the user clicks on "Create company"
		Then the page for register a new company is shown
	
	Scenario Outline: Register a new company successfully
		When the user introduces the trade name <trade_name>
		And the user introduces the business name <business_name>
		And the user introduces the email <email>
		And the user introduces the cif <cif>
		And the user selects the country <country>
		And the user selects the city <city>
		And the user introduces the zip code <zipcode>
		And the user introduces the phone number <phone>
		And the user introduces the address <address>
		And the user selects the category <category>
		And the user press the Create button
		Then the main page is shown
		And a message informing that the company is pending to approve is shown
		When the company is approved manually by an admin
		Then a confirm notification is shown
		And a business section is shown in my main page
		
		Examples:
			|trade_name|business_name|email|cif|country|city|zipcode|phone|address|category|
			|"Boom"|"Boom S.A."|"jefe@boom.com"|"B12345678"|"España"|"Valladolid"|"47007"|"654321987"|"Calle Los Almendros, 2"|"Mobiliario"|
		
	Scenario Outline: Register of a new company with wrong data
		When the user introduces the trade name <trade_name>
		And the user introduces the business name <business_name>
		And the user introduces the email <email>
		And the user introduces the cif <cif>
		And the user selects the country <country>
		And the user selects the city <city>
		And the user introduces the zip code <zipcode>
		And the user introduces the phone number <phone>
		And the user introduces the address <address>
		And the user selects the category <category>
		And the user press the Create button
		Then an error message in the page for register a new company is shown
		
		Examples:
			|trade_name|business_name|email|cif|country|city|zipcode|phone|address|category|
			|""|""|""|""|""|""|""|""|""|""|
			|"Boom"|""|"jefe@boom.com"|"B12345678"|"España"|"Valladolid"|"47007"|"654321987"|"Calle Los Almendros, 2"|"Mobiliario"|
			|"Boom"|"Boom S.A."|""|"B12345678"|"España"|"Valladolid"|"47007"|"654321987"|"Calle Los Almendros, 2"|"Mobiliario"|
			|"Boom"|"Boom S.A."|"jefe@boom.com"|""|"España"|"Valladolid"|"47007"|"654321987"|"Calle Los Almendros, 2"|"Mobiliario"|
			|"Boom"|"Boom S.A."|"jefe@boom.com"|"B12345678"|""|""|"47007"|"654321987"|"Calle Los Almendros, 2"|"Mobiliario"|
			|"Boom"|"Boom S.A."|"wrong_email.com"|"B12345678"|"España"|"Valladolid"|"47007"|"654321987"|"Calle Los Almendros, 2"|"Mobiliario"|
			|"Boom"|"Boom S.A."|"jefe@boom.com"|"123456789A"|"España"|"Valladolid"|"47007"|"654321987"|"Calle Los Almendros, 2"|"Mobiliario"|
			|"Boom"|"Boom S.A."|"jefe@boom.com"|"B12345678"|"España"|"Valladolid"|"47007"|"5"|"Calle Los Almendros, 2"|"Mobiliario"|

	Scenario Outline: Register a new company rejected by an admin
		When the user introduces the trade name <trade_name>
		And the user introduces the business name <business_name>
		And the user introduces the email <email>
		And the user introduces the cif <cif>
		And the user selects the country <country>
		And the user selects the city <city>
		And the user introduces the zip code <zipcode>
		And the user introduces the phone number <phone>
		And the user introduces the address <address>
		And the user selects the category <category>
		And the user press the Create button
		Then the main page is shown
		And a message informing that the company is pending to approve is shown
		When the company is rejected manually by an admin
		Then a notification with the problem is shown in my account
		
		Examples:
			|trade_name|business_name|email|cif|country|city|zipcode|phone|address|category|
			|"X"|"X"|"user@gmail.com"|"B00000000"|"España"|"Valladolid"|"47007"|"00000000"|"Calle Los Almendros, 2"|"Mobiliario"|
