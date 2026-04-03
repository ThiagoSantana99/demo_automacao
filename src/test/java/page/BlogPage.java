package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ScreenshotUtil;

import java.util.List;

/**
 * Classe BlogPage para representar a página principal do blog.
 * <p>
 * Responsabilidades:
 * - Mapear elementos de busca disponíveis na página inicial;
 * - Realizar interações com campo, botão e ícone de pesquisa;
 * - Abrir a home do blog e registrar evidências da navegação;
 * - Validar resultados e mensagens retornadas pela busca.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class BlogPage extends BasePage {

    private final By results = By.cssSelector("article");
    @FindBy(css = "#search-field")
    private WebElement searchInput;
    @FindBy(name = "submit")
    private WebElement submitBtn;
    @FindBy(css = ".newsletter-signup")
    private WebElement newsletterSignup;
    @FindBy(css = "uagb-post__title uagb-post__text")
    private WebElement carroceu;
    @FindBy(xpath = "//*[@class=\"ast-search-menu-icon slide-search\"]")
    private WebElement searchIcon;
    @FindBy(css = ".no-results")
    private WebElement emptyMessage;

    public BlogPage() {
        PageFactory.initElements(driver, this);
    }

    public void clickSearchIcon() {
        helper.click(searchIcon);
    }

    public void doubleclickSearchIcon(String nomeEvid) {
        helper.doubleclick(searchIcon, nomeEvid);
    }

    public boolean getcarroceu() {
        return wait.until(ExpectedConditions.visibilityOf(carroceu)).isEnabled();
    }

    public void openHomePage() {
        driver.get("https://blog.agibank.com.br/");
        helper.moveScreen();
        helper.waitForFullLoad();
        helper.moveScreen();
        ScreenshotUtil.Esperar(300);
        ScreenshotUtil.attachScreenshot("Pagina Carregada com Sucesso");
    }

    public void submitWithEnter() {
        helper.pressEnter(searchInput);
        ScreenshotUtil.attachScreenshot("Pesquisando");
    }

    public boolean hasResults() {
        return results.hashCode() > 0;
    }

    public boolean resultsContainTerm(String term) {
        List<WebElement> elements = driver.findElements(results);
        ScreenshotUtil.attachScreenshot("Resultado da Busca");
        for (WebElement el : elements) {
            if (el.getText().toLowerCase().contains(term.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean compareResultsWith(String term) {
        return resultsContainTerm(term);
    }

    public String getEmptyMessage() {
        helper.waitForFullLoad();
        helper.moveScreen();
        ScreenshotUtil.attachScreenshot("Evidencia Sem Resultado na Busca");
        return emptyMessage.getText();
    }

    public void enterSearch(String searchText) {
        helper.click(searchIcon);
        helper.isVisible(searchInput);
        helper.type(searchInput, searchText);
        ScreenshotUtil.attachScreenshot("Realizando Busca do termo: " + searchText);
    }
}
