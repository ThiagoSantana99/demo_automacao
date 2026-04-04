package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Classe TestRunner para executar a suite Cucumber integrada ao TestNG.
 * <p>
 * Responsabilidades:
 * <p>
 * - Definir a localizacao das features da automacao;
 * <p>
 * - Informar os pacotes de steps e hooks utilizados na execucao;
 * <p>
 * - Configurar plugins de saida e integracao com Allure;
 * <p>
 * - Disponibilizar cenarios para execucao paralela.
 *
 * @author Thiago Santana
 * @version 1.0
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Disponibiliza os cenarios do Cucumber para execucao pelo TestNG.
     * <p>
     * Responsabilidades:
     * - Obter os cenarios mapeados pelo runner;
     * <p>
     * - Expor os dados ao DataProvider configurado em paralelo.
     * <p>
     * @return matriz de cenarios a serem executados pelo TestNG
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
