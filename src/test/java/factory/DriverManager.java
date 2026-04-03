package factory;

import org.openqa.selenium.WebDriver;

/**
 * Classe DriverManager para gerenciar a instância de WebDriver por thread.
 * <p>
 * Responsabilidades:
 * - Armazenar o WebDriver em contexto isolado por thread;
 * - Disponibilizar o driver ativo para a execução corrente;
 * - Definir a instância do driver criada para o teste;
 * - Encerrar e remover o driver ao final da execução.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
