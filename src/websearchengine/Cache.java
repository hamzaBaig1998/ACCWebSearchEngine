package websearchengine;

import java.io.File;
import java.io.FileOutputStream;

public class Cache {
	
	public static Boolean createCacheFile() {
		try {
			File fileStream=new File("cache.txt");
			if(fileStream.createNewFile()) {
//				File created
				Utility.log("File created!");
				return true;
			}else {
//				File already exists
				Utility.log("File already exists!");
				return false;
			}
		}catch(Exception e) {
			Utility.log(e.getMessage());
			return false;
		}
	}
	
	public static Boolean fileExists(String path) {
		File f = new File(path);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}else {
			return false;
		}
	}
	
	public static void deleteCache() {
		try {
			new FileOutputStream("cache.txt").close();
			Utility.log("Cache has been cleared");
		}catch(Exception e) {
			Utility.log(e.getMessage());
		}
	}
}
