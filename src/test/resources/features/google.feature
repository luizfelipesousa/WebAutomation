Feature: Search 

Scenario: Google Search 
	Given I am on the google page 
	When search for "Youtube" 
	Then show results of "Youtube" 