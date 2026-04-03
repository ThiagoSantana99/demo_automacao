package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Classe TestRunner para executar a suíte Cucumber integrada ao TestNG.
 * <p>
 * Responsabilidades:
 * - Definir a localização das features da automação;
 * - Informar os pacotes de steps e hooks utilizados na execução;
 * - Configurar plugins de saída e integração com Allure;
 * - Disponibilizar cenários para execução paralela.
 *
 * @author Thiago Santana
 * @version 1.0
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
