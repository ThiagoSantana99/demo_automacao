package page;

import factory.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Helper;

import java.time.Duration;

/**
 * Classe BasePage para fornecer recursos compartilhados entre os page objects.
 * <p>
 * Responsabilidades:
 * - Recuperar a instância atual de WebDriver;
 * - Inicializar objetos de apoio para espera e ações do Selenium;
 * - Disponibilizar utilitários comuns para interação com páginas;
 * - Servir como classe base para especializações do pacote page.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class BasePage {
    protected WebDriver driver;
    protected Helper helper;
    protected WebDriverWait wait;
    protected Actions actions;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.actions = new Actions(driver);
        this.helper = new Helper(driver, wait, actions);
    }
}
