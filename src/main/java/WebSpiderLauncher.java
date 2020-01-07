import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Logger;

public class WebSpiderLauncher {

    private static final Logger logger = Logger.getLogger(WebSpiderLauncher.class.getName());


    public static void main(String[] args) {
        System.out.println("Hello World");

        try {
            Document doc = Jsoup.connect("https://www.ticketswap.nl/event/zwarte-cross-2020/vrijdag/5bebf0e9-728e-4bee-9359-0d414a2c9e35/1468082").get();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
