import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class WebSpiderLauncher {

    private static final Logger logger = Logger.getLogger(WebSpiderLauncher.class.getName());
    public static final String baseUrl = "https://www.bol.com/nl/l/boeken/N/8299/?bltgh=s-iDlYBGQPgvgTt07k5PWA.2_3_4.5.CategoryImage";
    public static final String baseSecondUrl = "https://www.bol.com";

    public static void main(String[] args) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        StringBuilder stringBuilder = new StringBuilder();

        try {
            HtmlPage page = client.getPage(baseUrl);
//            List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//li[@class='product-item--row js_item_root ']");
            List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//div[@class='product-title--inline']/a");
            if (items.isEmpty()) {
                System.out.println("No items found");
            } else {
                for (HtmlElement htmlItem: items) {
//                    System.out.println("htmlItem: " + htmlItem.toString());
                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath("//div[@class='product-title--inline']/a"));
                    String path = itemAnchor.getHrefAttribute();
                    stringBuilder.append(baseSecondUrl);
                    stringBuilder.append(path);

                    System.out.println(stringBuilder.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
