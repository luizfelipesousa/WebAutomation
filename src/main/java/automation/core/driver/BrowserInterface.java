package automation.core.driver;

/*
 * @author Luiz Felipe Alves de Sousa
 * @version 1.00
 * @since 10/01/2020
 * 
 * Interface padrão e obrigatória para todas as classes responsáveis por criar driver web.
 * Esta interface é utilizada no gerenciamento para decidir qual tipo de driver deve ser criado.
*/

import org.openqa.selenium.WebDriver;

public interface BrowserInterface {

	/**
	 * Método responsável por obter um driver web capaz de realizar automações
	 * utilizando Selenium.
	 * 
	 * @return WebDriver - Driver web devidamente configurado e pronto para uso.
	 */
	public WebDriver getDriver();
}
