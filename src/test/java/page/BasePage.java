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
 * <p>
 * - Recuperar a instancia atual de WebDriver;
 * <p>
 * - Inicializar objetos de apoio para espera e acoes do Selenium;
 * <p>
 * - Disponibilizar utilitarios comuns para interacao com paginas;
 * <p>
 * - Servir como classe base para especializacoes do pacote page.
 * <p>
 * @author Thiago Santana
 * @version 1.0
 */
public class BasePage {
    protected WebDriver driver;
    protected Helper helper;
    protected WebDriverWait wait;
    protected Actions actions;

    /**
     * Inicializa os recursos base compartilhados pelos page objects.
     * <p>
     * Responsabilidades:
     * <p>
     * - Obter o driver ativo da execucao;
     * <p>
     * - Criar instancias de espera explicita e acoes;
     * <p>
     * - Disponibilizar o helper para uso das classes filhas.
     */
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.actions = new Actions(driver);
        this.helper = new Helper(driver, wait, actions);
    }
}
