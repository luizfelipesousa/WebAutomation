package automation.test.pages;

import automation.core.base.WebBasePage;
import automation.test.objects.GoogleObject;

public class GooglePage extends WebBasePage {

	private GoogleObject Google = new GoogleObject();

	public void search(String content) {
		type(Google.field, content);
		click(Google.searchButton);
		waitPresenceOfElement(Google.results);
	}

	public void validateSearch(String content) {
		String BrowserTitle = String.format("%s - Pesquisa Google", content);
		validateBrowserTitle(BrowserTitle);
	}
}
