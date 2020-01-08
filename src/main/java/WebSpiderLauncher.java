import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import model.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WebSpiderLauncher {

    private static final Logger logger = Logger.getLogger(WebSpiderLauncher.class.getName());
    public static final String baseUrl = "https://www.bol.com/nl/l/tablets-accessoires/N/18147/?promo=electronics_360__A_42602-42621-tablets-&-accessoires_8_";
    public static final String baseSecondUrl = "https://www.bol.com";

    public static void main(String[] args) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<DomAttr> titles = (List<DomAttr>) page.getByXPath("//*/a[contains(@class, 'product-image product-image--list px_list_page_product_click')]/img/@alt");
            List<DomAttr> prices = (List<DomAttr>) page.getByXPath("//*[@id='js_items_content']/li/div/wsp-buy-block/div/section/div/div/meta/@content");
            List<DomAttr> urls = (List<DomAttr>) page.getByXPath("//*[@id='js_items_content']/li/div/div/a[contains(@class, 'hit-area__link medium--is-hidden')]/@href");


            if (titles.size() == prices.size() && prices.size() == urls.size()) {
                List<Item> items = new ArrayList<Item>(titles.size());
                for (int i = 0; i < titles.size(); i++) {
                    Item item = new Item();
                    item.setTitle(titles.get(i).getTextContent());
                    item.setPrice(Double.parseDouble(prices.get(i).getValue()));
                    item.setUrl(baseSecondUrl + urls.get(i).getValue());
                    items.add(item);
                }

                for (Item item: items) {
                    System.out.println(item.toString());
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
