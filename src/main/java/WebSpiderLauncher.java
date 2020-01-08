import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import model.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WebSpiderLauncher {

    private static final Logger logger = Logger.getLogger(WebSpiderLauncher.class.getName());
    public static final String baseUrl = "https://www.bol.com/nl/l/tablets-accessoires/N/18147/?promo=electronics_360__A_42602-42621-tablets-&-accessoires_8_";
    public static final String baseSwapUrl = "https://www.ticketswap.nl";
    public static final String baseSecondUrl = "https://www.bol.com";
    public static final String baseThirdUrl = "https://www.bol.com/nl/l/boeken/N/8299/?page=";
    public static final List<String> urls = new ArrayList<String>();
    public static final List<Item> books = new ArrayList<Item>();
    public static final String url = "https://www.ticketswap.nl/event/mystic-winter-garden-2020/b49eb76f-ed12-49d3-a80e-48b91100f0c0";

    public static void main(String[] args) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        for (int i = 1; i < 500; i++) {
            urls.add(baseThirdUrl + i);
        }

        try {
                HtmlPage page = client.getPage(url);
                List<DomAttr> tickets = (List<DomAttr>) page.getByXPath("//div[preceding-sibling::h2[contains(., 'Aangeboden')]and following-sibling::h2[contains(., 'Verkocht')]]/ul/div/a/@href");

                // //*[@id="tickets"]/h2[contains(@class, 'available-h2')]/div/ul/div/a/@href

            System.out.println(tickets.size());

                int index = 1;
                for (DomAttr item: tickets) {
                    System.out.println(baseSwapUrl + item.getValue());
                    index++;
                }

//                List<DomAttr> titles = (List<DomAttr>) page.getByXPath("//*/a[contains(@class, 'product-image product-image--list px_list_page_product_click')]/img/@alt");
//                List<DomAttr> prices = (List<DomAttr>) page.getByXPath("//*[@id='js_items_content']/li/div/wsp-buy-block/div/section/div/div/meta/@content");
//                List<DomAttr> urls = (List<DomAttr>) page.getByXPath("//*[@id='js_items_content']/li/div/div/a[contains(@class, 'hit-area__link medium--is-hidden')]/@href");


//                if (titles.size() == prices.size() && prices.size() == urls.size()) {
//                    List<Item> items = new ArrayList<Item>(titles.size());
//                    for (int i = 0; i < titles.size(); i++) {
//                        Item item = new Item();
//                        item.setTitle(titles.get(i).getTextContent());
//                        item.setPrice(Double.parseDouble(prices.get(i).getValue()));
//                        item.setUrl(baseSecondUrl + urls.get(i).getValue());
//                        items.add(item);
//                    }
//
//                    for (Item item: items) {
//                        System.out.println(item.toString());
//                    }
//                }


        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
