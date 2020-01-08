import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WebSpiderLauncher {

    private static final Logger logger = Logger.getLogger(WebSpiderLauncher.class.getName());
    public static final String baseUrl = "https://www.ticketswap.nl/event/zwarte-cross-2020/vrijdag/5bebf0e9-728e-4bee-9359-0d414a2c9e35/1468082";
    public static final String baseSecondUrl = "https://www.ticketswap.nl";

    public static void main(String[] args) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        List<String> urls = new ArrayList<String>();

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<DomAttr> items = (List<DomAttr>) page.getByXPath("//*[@id='tickets']/div/ul/div/a/@href");
            ////*[@id='js_items_content'/li/div/div/div/a/@href]
            ////*[@id="__next"]
            if (items.isEmpty()) {
                System.out.println("No items found");
            } else {
                for (DomAttr item: items) {
                    urls.add(baseSecondUrl + item.getValue());
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        System.out.println("***** URLS *****");
        for (String item: urls) {
            System.out.println(item);
        }

    }
}
