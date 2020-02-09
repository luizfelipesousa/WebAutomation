package automation.test.steps;

import automation.test.pages.GooglePage;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GoogleSteps {

	GooglePage google = new GooglePage();

	@After
	public void tearDown(Scenario scenario) {
		google.takeScreenshot(scenario.getName());
		scenario.embed(google.takeScreenshot(), "image/png");
		google.exitBrowser();
	}

	@Given("I am on the google page")
	public void i_am_on_the_google_page() {
		google.validateBrowserTitle("Google");
	}

	@When("search for {string}")
	public void search_for(String string) {
		google.search(string);
	}

	@Then("show results of {string}")
	public void show_results_of(String string) {
		google.validateSearch(string);
	}
}
