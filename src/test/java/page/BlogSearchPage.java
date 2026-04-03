package page;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Article;
import utils.ScreenshotUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe BlogSearchPage para representar a página de resultados de busca do blog.
 * <p>
 * Responsabilidades:
 * - Mapear elementos exibidos após a pesquisa;
 * - Obter o título da página de resultado;
 * - Percorrer os artigos encontrados na busca;
 * - Registrar evidências e anexos no relatório Allure.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class BlogSearchPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"primary\"]/section/h1")
    private WebElement searchTitle;

    @FindBy(xpath = "//*[@id=\"main\"]")
    private WebElement searchResult;

    public BlogSearchPage() {

        PageFactory.initElements(driver, this);
    }

    public String getSearchTitle() {
        helper.moveScreenTop();
        helper.scrollToElementPic(searchTitle, "Titulo Pagina Pesquisa");
        return searchTitle.getText();
    }

    public List<Article> findResults(String termoBusca) {
        List<Article> articlesList = new ArrayList<>();

        helper.moveScreen();
        String expectedUrl = "https://blog.agibank.com.br/?s=" + termoBusca;
        helper.moveScreen();

        List<WebElement> articles = driver.findElements(By.xpath("//*[@id='main']//article"));
        int cont = 0;

        ScreenshotUtil.attachScreenshot("Evidencia Resultado Pequisa");
        Allure.step("Artigos Encontrados: Total de Artigos = " + articles.size());

        for (WebElement article : articles) {
            int numArticle = articles.indexOf(article) + 1;
            helper.scrollToElement(articles.get(cont));
            List<WebElement> titleElements = helper.findChildElements(article, By.xpath("//*[@class=\"entry-title ast-blog-single-element\"]//a[@rel=\"bookmark\"]"));
            List<WebElement> dateElements = helper.findChildElements(article, By.xpath("//*[@class=\"published\"]"));
            String title = titleElements.get(cont).getText();
            String link = titleElements.get(cont).getAttribute("href");
            String date = dateElements.get(cont).getText();
            articlesList.add(new Article(title, link, date));
            int finalCont = cont;
            Allure.step("Artigo -> " + numArticle + " -> " + title, () -> {
                Allure.addAttachment("Título", title);
                Allure.addAttachment("Data", date);
                Allure.addAttachment("Link", link);
                helper.scrollToElementPic(articles.get(finalCont), "Artigo " + numArticle + " " + titleElements.get(finalCont).getText());
            });
            cont = cont + 1;
        }
        helper.moveScreenTop();
        return articlesList;
    }

}
