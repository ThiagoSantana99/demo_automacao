package factory;

import org.openqa.selenium.WebDriver;

/**
 * Classe DriverManager para gerenciar a instancia de WebDriver por thread.
 * <p>
 * Responsabilidades:
 * <p>
 * - Armazenar o WebDriver em contexto isolado por thread;
 * <p>
 * - Disponibilizar o driver ativo para a execucao corrente;
 * <p>
 * - Definir a instancia do driver criada para o teste;
 * <p>
 * - Encerrar e remover o driver ao final da execucao.
 * <p>
 * @author Thiago Santana
 * @version 1.0
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Recupera o driver associado a thread atual.
     * <p>
     * Responsabilidades:
     * - Consultar o armazenamento ThreadLocal;
     * - Retornar a instancia ativa para a execucao corrente.
     *
     * @return instancia atual de WebDriver da thread
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Define a instancia de driver para a thread atual.
     * <p>
     * Responsabilidades:
     * - Associar o WebDriver criado ao contexto corrente;
     * - Isolar o driver por thread para execucao paralela.
     *
     * @param driverInstance instancia do WebDriver a ser associada
     */
    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    /**
     * Encerra e remove o driver associado a thread atual.
     * <p>
     * Responsabilidades:
     * - Finalizar a sessao ativa do navegador;
     * - Limpar o contexto ThreadLocal apos o encerramento.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
