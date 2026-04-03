package config;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe ConfigReader para centralizar a leitura de parâmetros de execução dos testes.
 * <p>
 * Responsabilidades:
 * - Armazenar parâmetros por identificador de teste;
 * - Inicializar mapas de parâmetros para cada execução;
 * - Recuperar valores configurados por chave;
 * - Fornecer valor padrão quando o parâmetro não estiver disponível.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class ConfigReader {

    private static final Map<String, Map<String, String>> suiteParams = new HashMap<>();

    public static void initTestParams(String testName, Map<String, String> params) {
        suiteParams.put(testName, new HashMap<>(params));
    }

    public static String get(String testName, String key, String defaultValue) {
        Map<String, String> params = suiteParams.get(testName);
        if (params != null && params.containsKey(key)) {
            return params.get(key);
        }
        return defaultValue;
    }
}
