package websearchengine;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import utility.TST;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static websearchengine.AutoCorrect.*;

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
				add("6. Show history");
				add("7. Delete history");
				add("8. Exit");
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
					// default depth for wrong input
					if(depth <1)
					{
						depth =1;
					}
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
					System.out.println("\nAUTOCORRECT SIMULATION\n");
					loadWords();
					sort(words);
					startSimulation();
					break;
				}
				case 5:{
					Utility.log("Selected option: " + options.get(option-1));
					
					input.nextLine();
					Utility.log("Enter word for suggestions (We use history for this feature)");
					String word=input.nextLine();
					int top=0;

					ArrayList<String> keys = new ArrayList<String>();

				 	// contains the history of searches by the user (for suggestions)
				    TST<Integer> history = new TST<Integer>();
					try {
					      File myObj = new File("history.txt");
					      Scanner myReader = new Scanner(myObj);
					      int i=0;
					      while (myReader.hasNextLine()) {
					        String data = myReader.nextLine();
					        keys.add(data);
					        history.put(data, top);
					        top++;
					      }
					      myReader.close();
					    } catch (Exception e) {
					      Utility.log("Error: "+ e.getMessage());
					    }
					history.printSimilarWords(word);
					break;
				}
				case 6:{
					Utility.log("Selected option: " + options.get(option-1));
					Utility.log("History:");
					try {
					      File myObj = new File("history.txt");
					      Scanner myReader = new Scanner(myObj);
					      while (myReader.hasNextLine()) {
					        String data = myReader.nextLine();
					        Utility.log(data);
					      }
					      myReader.close();
					    } catch (Exception e) {
					      Utility.log("Error: "+ e.getMessage());
					    }
					break;
				}
				case 7:{
					Utility.log("Selected option: " + options.get(option-1));
					try {
						new FileOutputStream("history.txt").close();
						Utility.log("History has been cleared!");
					}catch(Exception e) {
						Utility.log("Error:"+e.getMessage());
					}
					break;
				}
				case 8:{
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
