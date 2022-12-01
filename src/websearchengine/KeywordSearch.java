package websearchengine;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;
import utility.TST;


public class KeywordSearch {

	private static final String path_to_file = "TextFiles/";

	/**this method is created to read the files in the specified directory
	 * @throws IOException
	 */
	
	public static void read_files() throws IOException {
		// creating the instance of specified directory
		File directory = new File(path_to_file);
		Scanner scan = new Scanner(System.in);
		String operation_restart;

		// making the string array for the list of the files
		String[] name_of_file = directory.list();
		
		// map is created to store the the text file names for mapping the word occurrence
		Map<String, Integer> hash_mapping = new HashMap<String, Integer>();

		do {
			System.out.println("Please enter the Keyword to be searched: ");
			String word_searching = scan.nextLine(); // Read user input
			
//			Write to history
			File file = new File("history.txt");
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
//				Driver.keys[Driver.keys.length]=word_searching;
//				Driver.history.put(word_searching, Driver.keys.length-1);
			    writer.append(word_searching);
			    writer.newLine();
			    writer.flush();
			    writer.close();
			}catch(Exception e) {
				Utility.log("Error: " + e.getMessage());
			}
//		    Write to history ends.
			
			// using the loop for reading the file contents
			for (String file_name : Objects.requireNonNull(name_of_file)) {

				String str_file = path_to_file + file_name;
				Utility.log(str_file);
				File current_file = new File(str_file);
				if (current_file.exists() && current_file.isFile() && current_file.canRead()) {
					Path path = Paths.get(str_file);
					hash_mapping.put(path.getFileName().toString(), new Integer(string_occurence_number(path, word_searching)));

				}
			}

			Map<String, Integer> map_sorting = sortByValue(hash_mapping);

			//method created for ranking the files in the directory from max from the to the least of the occurrence of the word
			Ranking(map_sorting);						
					
			System.out.println("Want to search another keyword? Yes/No");
			operation_restart = scan.nextLine();
		} 
		while (operation_restart.equals("yes") || operation_restart.equals("Yes") || operation_restart.equals("YES"));

		if (operation_restart.equals("no") || operation_restart.equals("No") || operation_restart.equals("NO"))
			System.out.println("Thank you for using our program, I hope we get 100 marks!");

	}
	
	 // applying the sorting method to the file on the basis of number of occurrence
	  private static Map<String, Integer> sortByValue(Map<String, Integer> map_unsorted) {

	        List<Map.Entry<String, Integer>> linked_list = new LinkedList<Map.Entry<String, Integer>>(map_unsorted.entrySet());

	        
	        Collections.sort(linked_list, new Comparator<Map.Entry<String, Integer>>() {
	            public int compare(Map.Entry<String, Integer> version1, Map.Entry<String, Integer> version2) {
	                return (version1.getValue()).compareTo(version2.getValue());
	            	}
	        	}
	        );

	        // looping and sorting into the new linkedhashmap
	        Map<String, Integer> map_sorted = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> entry : linked_list) {
	            map_sorted.put(entry.getKey(), entry.getValue());
	        }

	        return map_sorted;
	    }
	
	
	  public static <K, V> void Ranking(Map<K, V> map) throws IOException {
	  
		ArrayList keyword_list = new ArrayList(map.keySet());
		BufferedReader buffer_reader = new BufferedReader(new FileReader("Cache.txt"));
		ArrayList<String> list_of_lines = new ArrayList<>();
		Map<String, String> hash_mapping_1 = new HashMap<String, String>();
		String line = buffer_reader.readLine(); 
	  System.out.println("Page Ranking");
	  int page_rank=1;
		for (int i = keyword_list.size() - 1; i >= 0; i--) {
			while (line != null) { 
				list_of_lines.add(line); line = buffer_reader.readLine(); 
				} 
			
			for(String st: list_of_lines){
				String[] tmp=st.split(" ");
				hash_mapping_1.put(tmp[1],tmp[0]);	
			}
			
			// File
			String keyword = (String) keyword_list.get(i);
			if(!map.get(keyword).toString().equals("0")){
				System.out.println(page_rank+". "+" ||| number of word occurrence: "+map.get(keyword) +" ||| Uniform Resource Locator (URL) "+hash_mapping_1.get(keyword));
				
			}
			
			
			//Occurrence
			page_rank++;
		}
		buffer_reader.close();
	  }
	  
	/**
	 * created a method to find the number of keyword occurrence in file
	 *
	 * @param path_to_file
	 * @param word_Searching
	 */
	private static int string_occurence_number(Path path, String word_searching) {

		int total_number;
		TST<Integer> integer_1 = new TST<Integer>();
		List<String> line = null;
		
		// using try catch if file get null sos it doesn't throw error
		try {
			line = Files.readAllLines(path, StandardCharsets.ISO_8859_1); 
		} 
		catch (IOException e) {
			Utility.log("Error message: "+e.getMessage());
		}

		for (String str_line : Objects.requireNonNull(line)) {
			StringTokenizer stringTokenizer = new StringTokenizer(str_line);
			while (stringTokenizer.hasMoreTokens()) {
				String str_token = stringTokenizer.nextToken();
				if (integer_1.get(str_token) == null) {
					integer_1.put(str_token, 1);
				} 
				else {
					integer_1.put(str_token, integer_1.get(str_token) + 1);
				}
			}
		}

		if (integer_1.get(word_searching) != null)
			total_number = integer_1.get(word_searching);
		else
			total_number = 0;

		//printing the total number of the occurrence of the keyword
		return total_number;
	}
}

