package websearchengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;

public class Cache {
	
	public static Boolean createCacheFile() {
		if(!fileExists("cache.txt")) {
			File fileStream=new File("cache.txt");
			try {
				if(fileStream.createNewFile()) {
//					File created
//					Utility.log("File created!");
					return true;
				}
			}catch(Exception e) {
				Utility.log(e.getMessage());
				return false;
			}
		}else {
//			Utility.log("File already exists!");
			return false;
		}
		
		return false;
	}
	
	public static Boolean fileExists(String path) {
		File file = new File(path);
		if(file.isFile()) { 
		    return true;
		}else {
			return false;
		}
	}
	
	public static void addToCache(String filepath) {
		File fileStream=new File("cache.txt");
//		create new file if not existing
		createCacheFile();
		try {
//			Append to file
			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(fileStream, true));
			bufferWriter.append(filepath);
			bufferWriter.newLine();
			bufferWriter.flush();
			bufferWriter.close();
		}catch(Exception e){
			Utility.log(e.getMessage());
		}
		
	}
	
	public static Boolean existsInCache(String path) {
		BufferedReader reader;
		try {
			reader=new BufferedReader(new FileReader("cache.txt"));
			String line = reader.readLine();
			while(line != null) {
//				Utility.log(line);
				
//				Split the name from cache and take out the uri which will be first sequence before space as per our naming convention
				String uri= line.split(" ")[0];
				if(uri.equals(path)) {
//					Utility.log(path+" exists");
					reader.close();
					return true;
				}
				line=reader.readLine();
			}
			reader.close();
		}catch(Exception e) {
			Utility.log("Error Message: " + e.getMessage());
		}	
		return false;
	}
	
	public static void deleteCache() {
		try {
//			REFERENCE: https://docs.oracle.com/javase/7/docs/api/java/io/FileOutputStream.html
			new FileOutputStream("cache.txt").close();
			Utility.log("Cache has been cleared");
//			REFERECNE: https://commons.apache.org/proper/commons-io/javadocs/api-2.5/org/apache/commons/io/FileUtils.html#cleanDirectory(java.io.File)
			FileUtils.cleanDirectory(new File("TextFiles")); 
		}catch(Exception e) {
			Utility.log(e.getMessage());
		}
	}
	
}
