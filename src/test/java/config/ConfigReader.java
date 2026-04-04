package config;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe ConfigReader para centralizar a leitura de parametros de execucao dos testes.
 * <p>
 * Responsabilidades:
 * <p>
 * - Armazenar parametros por identificador de teste;
 * <p>
 * - Inicializar mapas de parametros para cada execucao;
 * <p>
 * - Recuperar valores configurados por chave;
 * <p>
 * - Fornecer valor padrao quando o parametro nao estiver disponivel.
 * <p>
 * @author Thiago Santana
 * @version 1.0
 */
public class ConfigReader {

    private static final Map<String, Map<String, String>> suiteParams = new HashMap<>();

    /**
     * Inicializa os parametros associados a um teste da suite.
     * <p>
     * Responsabilidades:
     * - Criar uma copia isolada do mapa recebido;
     * - Associar os parametros ao identificador do teste;
     * - Disponibilizar os dados para recuperacao posterior.
     *
     * @param testName nome identificador do teste
     * @param params mapa de parametros da execucao
     */
    public static void initTestParams(String testName, Map<String, String> params) {
        suiteParams.put(testName, new HashMap<>(params));
    }

    /**
     * Recupera o valor configurado para uma chave especifica.
     * <p>
     * Responsabilidades:
     * - Localizar os parametros associados ao teste informado;
     * - Retornar o valor configurado para a chave desejada;
     * - Devolver o valor padrao quando a chave nao existir.
     *
     * @param testName nome identificador do teste
     * @param key chave do parametro desejado
     * @param defaultValue valor padrao para retorno em caso de ausencia
     * @return valor configurado para a chave ou o valor padrao informado
     */
    public static String get(String testName, String key, String defaultValue) {
        Map<String, String> params = suiteParams.get(testName);
        if (params != null && params.containsKey(key)) {
            return params.get(key);
        }
        return defaultValue;
    }
}
