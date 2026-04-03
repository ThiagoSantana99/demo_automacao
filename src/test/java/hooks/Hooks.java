package hooks;

import factory.DriverFactory;
import factory.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScreenshotUtil;

import java.io.File;
import java.util.Objects;

import static io.qameta.allure.Allure.addAttachment;

/**
 * Classe Hooks para gerenciar o ciclo de vida dos testes Cucumber.
 * <p>
 * Responsabilidades:
 * - Inicializar o WebDriver antes de cada cenário (@Before);
 * - Finalizar o WebDriver e anexar artefatos após cada cenário (@After);
 * - Capturar screenshots e logs em caso de falha;
 * - Validar e converter configurações de browser;
 * - Registrar eventos de teste com SLF4J para rastreabilidade.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    private static final String LOG_DIR = "target/logs";

    @Before
    public void setup(Scenario scenario) {
        logger.info("=== INICIANDO CENARIO: {} ===", scenario.getName());

        String browser = System.getProperty("browser", "chrome");
        addAttachment("Browser:", browser);

        logger.info("Browser configurado: {}", browser);

        DriverFactory.Browser browserEnum;
        try {
            browserEnum = DriverFactory.Browser.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Browser inválido: {}. Usando Chrome como padrão.", browser, e);
            browserEnum = DriverFactory.Browser.CHROME;
        }

        try {
            DriverManager.setDriver(DriverFactory.createDriver(browserEnum));
            logger.info("Driver criado com sucesso para o browser: {}", browserEnum);
        } catch (Exception e) {
            logger.error("Falha ao criar o driver para o browser: {}", browserEnum, e);
            throw new RuntimeException("Erro na configuração do driver", e);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("=== CENARIO FALHOU: {} ===", scenario.getName());
            ScreenshotUtil.attachScreenshot("Erro " + scenario.getName());
        } else {
            logger.info("=== CENARIO EXECUTADO COM SUCESSO: {} ===", scenario.getName());
            ScreenshotUtil.attachScreenshot("Sucesso " + scenario.getName());
        }

        try {
            DriverManager.quitDriver();
            logger.info("Driver fechado com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao fechar o driver", e);
        }

        logger.info("=== FIM DO CENARIO ===\n");
    }

    private String getLatestLogFile() {
        File logDirectory = new File(LOG_DIR);
        if (!logDirectory.exists() || !logDirectory.isDirectory()) {
            return null;
        }

        File[] logFiles = logDirectory.listFiles((dir, name) -> name.startsWith("test_") && name.endsWith(".log"));
        if (logFiles == null || logFiles.length == 0) {
            return null;
        }

        File latestFile = logFiles[0];
        for (File file : logFiles) {
            if (file.lastModified() > latestFile.lastModified()) {
                latestFile = file;
            }
        }

        return latestFile.getAbsolutePath();
    }
}
