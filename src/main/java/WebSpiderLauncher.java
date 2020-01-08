import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WebSpiderLauncher {

    private static final Logger logger = Logger.getLogger(WebSpiderLauncher.class.getName());
    public static final String baseUrl = "https://www.ticketswap.nl/event/karnaval-festival-2020/sunday-tickets/6c2e638c-0f9e-4b7d-a678-4e158c15bafb/1463463";
    public static final String baseSecondUrl = "https://www.ticketswap.nl";

    public static final int numOfTickets = 0;
    public static boolean isAlive = true;


    public static void main(String[] args) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        List<String> urls = new ArrayList<String>();

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<DomAttr> tickets = new ArrayList<DomAttr>();

            while (tickets.isEmpty()) {
                page.refresh();
                tickets = (List<DomAttr>) page.getByXPath("//div[preceding-sibling::h2[contains(., 'Aangeboden')]and following-sibling::h2[contains(., 'Verkocht')]]/ul/div/a/@href");

                for (DomAttr item : tickets) {
                    urls.add(baseSecondUrl + item.getValue());
                }
                break;

            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        System.out.println("***** URLS *****");
        for (String item : urls) {
            System.out.println(item);
        }

    }
}