package automation.core.driver;

/*
 * @author Luiz Felipe Alves de Sousa
 * @version 1.00
 * @since 10/01/2020
 * 
 * Classe responsável por configurar e gerar instancias de drivers
 * responsáveis por acessar o navegador Google Chrome.
*/

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import automation.core.utils.Utils;
import automation.logging.log4j.Log4JSetup;

public class DriverChrome implements BrowserInterface {

	private ChromeDriver driver;
	private Logger log = Log4JSetup.getLogger(DriverChrome.class);

	// ******************************
	// Construtor
	// ******************************

	/**
	 * Responsavel por criar e configurar corretamente o objeto do tipo
	 * ChromeDriver.
	 */
	public DriverChrome() {

		System.setProperty("webdriver.chrome.driver", Utils.binDriverPath("chrome"));
		System.setProperty("webdriver.chrome.args", "--disable-logging");
		System.setProperty("webdriver.chrome.silentOutput", "true");

		ChromeOptions options = setupChromeBrowser();
		driver = new ChromeDriver(options);
		log.info("Chrome iniciado com sucesso.");
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
	private ChromeOptions setupChromeBrowser() {

		String downloadFolderPath = Utils.binDownloadFolderPath();
		boolean headless = !Utils.getProp("browser.mode.headless").isEmpty();

		// Configurando Preferencias de Download
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFolderPath);
		chromePrefs.put("download.prompt_for_download", false);
		chromePrefs.put("download.directory_upgrade", true);

		// Configurando Opcoes de Navegador
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--lang=pt-BR");
		options.addArguments("--incognito");

		if (headless) {
			options.addArguments("--no-sandbox"); // Bypass OS security model, MUST BE THE VERY FIRST OPTION
			options.addArguments("--headless");
			options.addArguments("--window-size=1366,768");
			options.setExperimentalOption("useAutomationExtension", false);
			options.addArguments("start-maximized"); // open Browser in maximized mode
			options.addArguments("disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-gpu"); // applicable to windows os only
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
			options.addArguments("--ignore-certificate-errors");
		}

		return options;
	}

	/**
	 * Retorno do objeto Driver devidamente configurado
	 */
	public ChromeDriver getDriver() {
		return driver;
	}

}
