package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import page.BlogPage;
import page.BlogSearchPage;

/**
 * Classe blogSearchSteps para implementar os passos de busca no blog.
 * <p>
 * Responsabilidades:
 * - Abrir a página inicial do blog para os cenários de busca;
 * - Preencher e submeter o termo pesquisado;
 * - Interagir com o ícone de pesquisa;
 * - Validar resultados, mensagens e conteúdo retornado.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class blogSearchSteps {

    BlogPage blogPage = new BlogPage();
    BlogSearchPage BlogSearchPage = new BlogSearchPage();
    String termoBusca;

    @Severity(SeverityLevel.CRITICAL)
    @Description("Teste de Blog Agi - Funcionalidade de Busca")
    @Given("que o usuário acessa a página inicial do blog")
    public void acessarPaginaInicial() {

        blogPage.openHomePage();
    }

    @When("o usuário digita {string} no campo de busca")
    public void digitarNoCampoBusca(String termo) {
        termoBusca = termo;
        blogPage.enterSearch(termo);
    }

    @When("pressiona Enter")
    public void pressionaEnter() {
        blogPage.submitWithEnter();
    }

    @When("clica no ícone de busca")
    public void clicarNaLupa() {
        blogPage.doubleclickSearchIcon("Evidencia Click na Lupa");
    }

    @Then("deve visualizar uma lista de artigos")
    public void validarListaResultados() {
        BlogSearchPage.findResults(termoBusca);
    }

    @Then("os resultados devem conter a palavra {string} no título ou conteúdo")
    public void validarConteudoResultados(String termo) {
        Assert.assertEquals(BlogSearchPage.getSearchTitle(), "Resultados encontrados para: " + termoBusca.trim());
    }

    @Then("deve visualizar mensagem de {string}")
    public void validarMensagem(String mensagem) {
        Assert.assertTrue(
                blogPage.getEmptyMessage().contains(mensagem),
                "Mensagem esperada não exibida"
        );
    }

    @Then("os resultados devem incluir conteúdos de {string}")
    public void validarAcentuacao(String termo) {
        Assert.assertTrue(
                blogPage.resultsContainTerm(termo),
                "Problema com acentuação"
        );
    }

    @Then("deve retornar artigos relacionados a {string}")
    public void validarBuscaParcial(String termo) {
        Assert.assertTrue(
                blogPage.resultsContainTerm(termo),
                "Busca parcial não retornou resultados esperados"
        );
    }
}
