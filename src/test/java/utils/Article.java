package utils;

/**
 * Classe Article para representar um artigo retornado pela busca do blog.
 * <p>
 * Responsabilidades:
 * - Armazenar titulo, link e data de publicacao do artigo;
 * - Disponibilizar acesso aos dados coletados na busca;
 * - Servir como objeto de transporte entre pagina e validacoes;
 * - Fornecer representacao textual amigavel do conteudo.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class Article {
    private String title;
    private String link;
    private String date;

    /**
     * Inicializa uma instancia de artigo com os dados coletados.
     * <p>
     * Responsabilidades:
     * <p>
     * - Armazenar o titulo do artigo;
     * <p>
     * - Armazenar o link do artigo;
     * <p>
     * - Armazenar a data de publicacao.
     * <p>
     * @param title titulo do artigo
     * @param link link do artigo
     * @param date data de publicacao do artigo
     */
    public Article(String title, String link, String date) {
        this.title = title;
        this.link = link;
        this.date = date;
    }

    /**
     * Retorna o titulo do artigo.
     *
     * @return titulo do artigo
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retorna o link do artigo.
     *
     * @return link do artigo
     */
    public String getLink() {
        return link;
    }

    /**
     * Retorna a data de publicacao do artigo.
     *
     * @return data de publicacao do artigo
     */
    public String getDate() {
        return date;
    }

    /**
     * Monta a representacao textual do artigo.
     *
     * @return descricao textual do artigo
     */
    @Override
    public String toString() {
        return "Titulo: " + title + "\nLink: " + link + "\nData: " + date + "\n";
    }
}
