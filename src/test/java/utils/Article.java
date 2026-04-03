package utils;

/**
 * Classe Article para representar um artigo retornado pela busca do blog.
 * <p>
 * Responsabilidades:
 * - Armazenar título, link e data de publicação do artigo;
 * - Disponibilizar acesso aos dados coletados na busca;
 * - Servir como objeto de transporte entre página e validações;
 * - Fornecer representação textual amigável do conteúdo.
 *
 * @author Thiago Santana
 * @version 1.0
 */
public class Article {
    private String title;
    private String link;
    private String date;

    public Article(String title, String link, String date) {
        this.title = title;
        this.link = link;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Título: " + title + "\nLink: " + link + "\nData: " + date + "\n";
    }
}
