package websearchengine;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.IOException;

public class Driver {

//	 TODO: Hamza Baig. A basic driver for the web search engine. Will update late
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		boolean state=true;
		ArrayList<String> options = new ArrayList<String>() {
			{
				add("1. Search URL");
				add("2. Remove Cache");
				add("3. Search word");
				add("4. Auto-correct word");
				add("5. Auto-fill word");
				add("6. Exit");
			}
		};
		
		do {
			for (String option : options) 
			{ 
			    Utility.log(option);
			}
			
			Utility.log("Please select one option:");
			int option=input.nextInt();
			
			switch(option) {
				case 1:{
					
					int depth =1;
					String uri="";
					Utility.log("Selected option: " + options.get(option-1));
					
					input.nextLine();
					// user input
					Utility.log("Enter Uri");
					uri = input.nextLine();
					
					Utility.log("Enter Depth");
					depth = input.nextInt();
					
					// check if valid Uri
					if(Pattern.matches(webCrawler.urlPattern, uri))
					{
						// check if present in cache
						if (!Cache.existsInCache(uri)) 
						{
							// calling crawler
							try {
								webCrawler.checkCrawl(1, uri, new ArrayList<String>(),depth);
							}catch(IOException e) {
								Utility.log(e.getMessage());
							}
						} else 
						{
							Utility.log("This URL has already been crawled.");
						}
					}
					else 
					{
						Utility.log("Invalid uri");
					}
					
					break;
				}
				case 2:{
					Utility.log("Selected option: " + options.get(option-1));
					Cache.deleteCache();
					break;
				}
				case 3:{
					Utility.log("Selected option: " + options.get(option-1));
					try {
						KeywordSearch.read_files();
					}catch(Exception e) {
						Utility.log(e.getMessage());
					}
					break;
				}
				case 4:{
					Utility.log("Selected option: " + options.get(option-1));
					break;
				}
				case 5:{
					Utility.log("Selected option: " + options.get(option-1));
					break;
				}
				case 6:{
					Utility.log("Selected option: " + options.get(option-1));
					state=false;
					break;
				}
				default:{
					Utility.log("Selected option: " + options.get(option-1));
					break;
				}
			}
		}
		while(state);
		
		input.close();
	}

}
