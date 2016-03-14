Feature: The user will be able search companies by name
	In order to view the services offered by a company
	As a registered user
	I want to search a company by his name
	
	Background:
		Given I am logged as an user
		And I am on the main page
	
	Scenario: Search company by name successfully
		When I click on the search bar
		And I write the name of company 'Boom S.A.'
		Then I should see a list when 'Boom S.A.' is shown
		When I click on the company 'Boom S.A.'
		Then I should see the company information page
		
	Scenario: Search company by name unsuccessfully
		When I click in the search bar
		And I write the name of company 'asdf'
		Then I should not see any list, or an empty list of companies
		