package websearchengine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class toText {
	
	public static void convert(String uri) throws IOException
	{
		long time=0;
		String pathName=null;
		String timeString= null;
		Elements element;
		
		// new directory initialization
		File dir = new File("TextFiles");
		
		// check if new directory exists
		if(!dir.exists())
		dir.mkdir();
		//JSoup connection  
		Document doc = Jsoup.connect(uri).get();
		
		
		
		//getting time for logs
		time = System.currentTimeMillis();
        
		// converting time to string
		timeString =String.valueOf(time);
        
		//file name
		pathName ="TextFiles//"+timeString+".txt";
				
		File file = new File(pathName);
        // check if file exists
		if(!file.exists())
		// create new file
		file.createNewFile();
		
		// select all elements
		element = doc.select("*");
		
		// adding to cache
		Cache.addToCache(uri+" "+timeString+".txt");
		
		
		// to write content in file
		BufferedWriter filefinale = new BufferedWriter(new FileWriter(file));
		for (Element e : element) {
			// writing in file
			filefinale.write(e.ownText());
			// going to new line
			filefinale.newLine();
		}

		filefinale.flush();
		filefinale.close();
		
	}

}
