package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Classe Helper para reunir utilitários de interação e espera com Selenium.
 * <p>
 * Responsabilidades:
 * - Localizar elementos e coleções na página;
 * - Executar ações de clique, digitação e teclado;
 * - Realizar esperas explícitas de carregamento e visibilidade;
 * - Apoiar rolagem, evidências e validações auxiliares.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class Helper {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public Helper(WebDriver driver, WebDriverWait wait, Actions actions) {
        this.driver = driver;
        this.wait = wait;
        this.actions = actions;
    }

    public WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> findAll(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }

    public List<WebElement> findChildElements(WebElement parent, By childLocator) {
        return parent.findElements(childLocator);
    }

    public boolean isVisible(By locator) {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return el.isDisplayed();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (TimeoutException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean exists(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public boolean isClickable(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void doubleclick(WebElement element, String nomeEvid) {
        waitForFullLoad();
        WebElement body = driver.findElement(By.tagName("body"));
        body.click();
        ScreenshotUtil.attachScreenshot("Evidencia-> " + nomeEvid);
        actions.moveToElement(element)
                .pause(Duration.ofMillis(200))
                .doubleClick()
                .perform();
        waitForFullLoad();
        body = driver.findElement(By.tagName("body"));
        body.click();
    }

    public void clickJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    public void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    public void pressEnter(WebElement element) {
        element.sendKeys(Keys.ENTER);
    }

    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public String getAttribute(By locator, String attribute) {
        return find(locator).getAttribute(attribute);
    }

    public boolean waitForPageLoaded() {
        try {
            ExpectedCondition<Boolean> pageLoadCondition = driver -> {
                assert driver != null;
                return ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete");
            };

            return wait.until(pageLoadCondition);
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean waitForAjaxComplete() {
        try {
            ExpectedCondition<Boolean> ajaxCondition = driver -> {
                assert driver != null;
                return (Boolean) ((JavascriptExecutor) driver)
                        .executeScript("return window.jQuery != undefined && jQuery.active === 0");
            };

            return wait.until(ajaxCondition);
        } catch (Exception e) {
            return true;
        }
    }

    public void waitForFullLoad() {
        waitForPageLoaded();
        waitForAjaxComplete();
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToElementPic(WebElement element, String nomeEvid) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
        ScreenshotUtil.Esperar(100);
        ScreenshotUtil.attachScreenshot("Evidencia-> " + nomeEvid);
    }

    public void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForText(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public int count(By locator) {
        return driver.findElements(locator).size();
    }

    public void moveScreen() {
        actions.scrollByAmount(0, 1000).perform();
        actions.scrollByAmount(0, -1000).perform();
    }

    public void moveScreenPic() {
        actions.scrollByAmount(0, 1000).perform();
        ScreenshotUtil.Esperar(200);
        ScreenshotUtil.attachScreenshot("Pagina Carregada com Sucesso");
        actions.scrollByAmount(0, -1000).perform();
    }

    public void moveScreenTop() {
        actions.scrollByAmount(0, 0).perform();
    }

}
