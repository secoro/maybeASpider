import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.Logger;

public class WebSpiderLauncher {

    private static final Logger logger = Logger.getLogger(WebSpiderLauncher.class.getName());


    public static void main(String[] args) {
        System.out.println("Hello World");

        try {
            Document doc = Jsoup.connect("https://en.wikipedia.org").get();
            System.out.println(doc.title());
            Elements newsHeadLines = doc.select("*[href*=php]");
            for (Element headline: newsHeadLines) {
                System.out.println(headline);
            }

        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
