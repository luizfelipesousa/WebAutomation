package automation.core.driver;

/*
 * @author Luiz Felipe Alves de Sousa
 * @version 1.00
 * @since 10/01/2020
 * 
 * Classe responsável por configurar e gerar instancias de drivers
 * responsáveis por acessar o navegador Safari.
*/

import java.io.File;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import automation.core.utils.Utils;
import automation.logging.log4j.Log4JSetup;

public class DriverSafari implements BrowserInterface {

	private SafariDriver driver;
	private Logger log = Log4JSetup.getLogger(DriverSafari.class);

	// ******************************
	// Construtor
	// ******************************

	/**
	 * Responsavel por criar e configurar corretamente o objeto do tipo
	 * SafariDriver.
	 */
	public DriverSafari() {

		SafariOptions safariOptions = new SafariOptions();
		safariOptions.setCapability("safari.options.dataDir", Utils.binDownloadFolderPath() + File.separator);

		driver = new SafariDriver(safariOptions);

		log.info("Safari iniciado com sucesso.");
	}

	/**
	 * Retorno do objeto Driver devidamente configurado
	 */
	public WebDriver getDriver() {
		return driver;
	}

}
