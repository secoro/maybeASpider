import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WebSpiderLauncher {

    private static final Logger logger = Logger.getLogger(WebSpiderLauncher.class.getName());
    public static final String baseUrl = "https://www.ticketswap.nl/event/karnaval-festival-2020/sunday-tickets/6c2e638c-0f9e-4b7d-a678-4e158c15bafb/1463463";
    public static final String baseSecondUrl = "https://www.ticketswap.nl";

    public static int numOfTickets = 0;
    public static boolean isAlive = true;


    public static void main(String[] args) {
        WebClient client = new WebClient(BrowserVersion.FIREFOX_17);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        List<String> urls = new ArrayList<String>();
        int index = 1;

        try {
            List<DomAttr> tickets;

            while (isAlive) {
                HtmlPage page = client.getPage(baseUrl);
                page.executeJavaScript("location.reload();");
                tickets = (List<DomAttr>) page.getByXPath("//div[preceding-sibling::h2[contains(., 'Aangeboden')]and following-sibling::h2[contains(., 'Verkocht')]]/ul/div/a/@href");

                System.out.println("Seconds: " + index);
                index++;

                for (DomAttr item : tickets) {
                    urls.add(baseSecondUrl + item.getValue());
                }

                if (!tickets.isEmpty()) {
                    numOfTickets = tickets.size();
                    System.out.println("Available tickets: " + numOfTickets);
                    isAlive = false;
                }

            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        System.out.println();

        try {
            HtmlPage page = client.getPage(urls.get(0));
            System.out.println("PAGE");

            //make a button 'Koop een ticket'
            HtmlButton purchaseButton = page.getFirstByXPath("//*[@id=\"__next\"]/div[2]/div[1]/div/form/button");
            DomAttr cartAnchor = page.getFirstByXPath("//*[@id=\"__next\"]/div[1]/div[1]/div[1]/div/nav/ul/li[4]/a/@href");

            System.out.println("JS RESULT");
//            ScriptResult scriptResult = page.executeJavaScript("document.getElementsByClassName('css-t2gdrp e1suhhn80')[0].click()\n");
            ScriptResult scriptResult = page.executeJavaScript("document.getElementsByTagName('button')[5].click()\n");
            scriptResult.getNewPage();
            HtmlPage reserverdTicketsPage = client.getPage(baseSecondUrl + cartAnchor.getValue());
            System.out.println(reserverdTicketsPage.asXml());

            //click button to generate next page
//            page.executeJavaScript("window.onload=function(){\n" +
//                    "  document.getElementsByTagName(\"BUTTON\").click();\n" +
//                    "};");
//            System.out.println("\nJS Result");
//            System.out.println(page.executeJavaScript("window.onload=function(){\n" +
//                    "  document.getElementsByTagName(\"BUTTON\").click();\n" +
//                    "};").toString());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}