package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Classe DriverFactory para criar instâncias de WebDriver conforme o navegador informado.
 * <p>
 * Responsabilidades:
 * - Configurar automaticamente os drivers dos navegadores suportados;
 * - Instanciar WebDriver para Chrome, Firefox e Edge;
 * - Aplicar opções de inicialização e limpeza de cache;
 * - Retornar o navegador pronto para execução dos testes.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class DriverFactory {

    public enum Browser {
        CHROME, EDGE
    }

    private static boolean isHeadlessEnabled() {
        String headlessProperty = System.getProperty("headless", "false");
        String ciEnvironment = System.getenv("CI");
        return Boolean.parseBoolean(headlessProperty) || "true".equalsIgnoreCase(ciEnvironment);
    }

    public static WebDriver createDriver(Browser browser) {

        WebDriver driver;
        boolean headless = isHeadlessEnabled();
        switch (browser) {
              case EDGE:
                WebDriverManager.edgedriver().setup();

                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--inprivate");
                edgeOptions.addArguments("--disable-application-cache");
                edgeOptions.addArguments("--disable-cache");
                edgeOptions.addArguments("--window-size=1920,1080");
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }

                driver = new EdgeDriver(edgeOptions);
                driver.manage().window().maximize();
                return driver;

            case CHROME:
            default:
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("--disable-application-cache");
                chromeOptions.addArguments("--disable-cache");
                chromeOptions.addArguments("--window-size=1920,1080");
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }

                driver = new ChromeDriver(chromeOptions);
                driver.manage().deleteAllCookies();
                driver.manage().window().maximize();
                return driver;
        }

    }
}
