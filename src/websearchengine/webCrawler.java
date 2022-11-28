//class package websearchengine
package websearchengine;

//import I/O java packages
import java.io.IOException;

//import Util java packages
import java.util.ArrayList;
import java.util.regex.Pattern;

//import JSoup java packages
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class webCrawler{
	
	// URL Regex
	public static final String urlPattern = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$";
	
	public static void checkCrawl(int depth, String uri, ArrayList<String> urisVisited, int depthMax) throws IOException
	{
		//limiting crawling by depth
		if(depth <= depthMax && uri != null)
		{
			//call JSoup crawler
			Document doc = crawl(uri, urisVisited);
			//Element to traverse and manipulate the HTML. 
			//Select to choose hyperlink
			Elements allEle = doc.select("a[href]");
			// for each element in allEle
			for (Element ele : allEle) {
				//abstract Uri
				String link = ele.absUrl("href");
				// check if Uri already visited
				if (!urisVisited.contains(link)) {
					// crawling the Uri if not visited
					checkCrawl(depth++, link, urisVisited,depthMax);
				}
			}
		}
	}
	
	public static Document crawl(String uri, ArrayList<String> urisVisited)
	{
		//JSoup connection
		Connection conn = Jsoup.connect(uri);
		
		try {
			//ignore invalid content type
			Document doc = conn.ignoreContentType(true).get();
			// if success
			if (conn.response().statusCode() == 200) {
				//storing to text file
				toHTML.testfiles(uri);
				// adding Uri to visited list
				urisVisited.add(uri);
				return doc;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}
}
