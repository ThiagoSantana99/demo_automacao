package utils;

import factory.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.ByteArrayInputStream;

/**
 * Classe ScreenshotUtil para centralizar capturas de tela da automação.
 * <p>
 * Responsabilidades:
 * - Capturar screenshots da execução corrente;
 * - Anexar imagens ao relatório Allure;
 * - Ajustar a visualização antes da captura;
 * - Disponibilizar espera simples para apoio às evidências.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class ScreenshotUtil {

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

    public static void Esperar(Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
