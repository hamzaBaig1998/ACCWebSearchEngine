package websearchengine;

import java.util.ArrayList;
import java.util.Scanner;

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
				add("3. Rank pages");
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
					Utility.log("Selected option: " + options.get(option-1));
//					Utility.log("The file exists?:"+Cache.fileExists("cache.txt"));
//					Cache.createCacheFile();
					break;
				}
				case 2:{
					Utility.log("Selected option: " + options.get(option-1));
					Cache.deleteCache();
					break;
				}
				case 3:{
					Utility.log("Selected option: " + options.get(option-1));
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
