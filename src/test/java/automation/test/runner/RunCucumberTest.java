package automation.test.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(strict = true, plugin = {
		"pretty" }, monochrome = true, features = "src/test/resources/features", glue = "automation.test.steps")
public class RunCucumberTest {
}