package automation.core.driver;

/*
 * @author Luiz Felipe Alves de Sousa
 * @version 1.00
 * @since 10/01/2020
 * 
 * Classe responsável por configurar e gerar instancias de drivers
 * responsáveis por acessar de forma remota um navegador do Google Chrome.
*/

import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import automation.core.utils.Utils;
import automation.logging.log4j.Log4JSetup;

public class DriverRemote implements BrowserInterface {

	private RemoteWebDriver driver;
	private Logger log = Log4JSetup.getLogger(DriverRemote.class);

	// ******************************
	// Construtor
	// ******************************

	/**
	 * Responsavel por criar e configurar corretamente o objeto remoto do tipo
	 * ChromeDriver.
	 */
	public DriverRemote() {

		try {
			ChromeOptions options = setupBrowser();
			driver = new RemoteWebDriver(new URL(Utils.getProp("browser.remote.url")), options);
			driver.manage().window().maximize();
		} catch (Exception e) {
			log.error("Não foi possível conectar o selenium hub chrome");
			e.printStackTrace();
		}
	}

	// ******************************
	// Configuração
	// ******************************

	/**
	 * Método privado responsável pelo retorno das configurações do browser para
	 * determinar comportamentos, diretórios de download, modos de execução e etc.
	 * 
	 * @return ChromeOptions - Conjunto de configurações do browser.
	 */
	private ChromeOptions setupBrowser() {

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setPlatform(Platform.LINUX);

		ChromeOptions options = new ChromeOptions();
		options.merge(cap);
		options.setHeadless(true);
		options.addArguments("window-size=1366, 768");

		// Configurando Preferencias de Download
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", Utils.binDownloadFolderPath());
		chromePrefs.put("download.prompt_for_download", false);
		chromePrefs.put("download.directory_upgrade", true);

		return options;
	}

	/**
	 * Retorno do objeto Driver devidamente configurado
	 */
	public WebDriver getDriver() {
		return driver;
	}

}
