package utils;

import factory.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Classe ScreenshotUtil para centralizar capturas de tela da automacao.
 * <p>
 * Responsabilidades:
 * <p>
 * - Capturar screenshots da execucao corrente;
 * <p>
 * - Anexar imagens ao relatorio Allure;
 * <p>
 * - Ajustar a visualizacao antes da captura;
 * <p>
 * - Disponibilizar espera simples para apoio as evidencias.
 * <p>
 * @author Thiago Santana
 * @version 1.0
 */
public class ScreenshotUtil {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);

    /**
     * Captura um screenshot da execucao atual e anexa o resultado no Allure.
     * <p>
     * Responsabilidades:
     * <p>
     * - Obter o driver associado a execucao corrente;
     * <p>
     * - Ajustar a visualizacao antes da captura;
     * <p>
     * - Gerar a imagem e anexar ao relatorio.
     * <p>
     * @param name nome do anexo a ser exibido no relatorio
     */
    public static void attachScreenshot(String name) {
        WebDriver driver = DriverManager.getDriver();
        Actions actions = new Actions(driver);
        actions.scrollByAmount(0, 0).perform();
        Esperar(500);

        if (driver != null) {
            actions.scrollByAmount(0, 0).perform();
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        }
    }

    /**
     * Realiza uma espera simples para apoiar a captura de evidencias.
     * <p>
     * Responsabilidades:
     * <p>
     * - Pausar a execucao pelo tempo informado;
     * <p>
     * - Preservar o status de interrupcao em caso de falha.
     * <p>
     * @param time tempo de espera em milissegundos
     */
    public static void Esperar(Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error("Execucao interrompida durante espera para captura de evidencia.", e);
            Thread.currentThread().interrupt();
        }
    }
}
