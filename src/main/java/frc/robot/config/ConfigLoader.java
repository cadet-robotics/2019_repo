package frc.robot.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Loads the config file into a JSON object for parsing
 * 
 * @author Alex Pickering
 */
public class ConfigLoader {
	
	/**
	 * Loads the JSON config file
	 * The first .json in this implementation
	 * 
	 * @return The JsonObject of the config file
	 * @throws IOException
	 */
	public static JsonObject loadConfigFile() throws IOException {
		FileReader fr = new FileReader(getConfigFile());
		
		return new JsonParser().parse(fr).getAsJsonObject();
	}
	
	/**
	 * Loads the specified JSON config file
	 * 
	 * @param fileName The name of the file to load
	 * @return The JsonObject of the config file
	 * @throws IOException
	 */
	public static JsonObject loadConfigFile(String fileName) throws IOException {
		FileReader fr = new FileReader(getConfigFile(fileName));
		
		return new JsonParser().parse(fr).getAsJsonObject();
	}
	
	/**
	 * Gets the config file to read from
	 * This will be the 'first' .cfg file found in this implementation
	 * 
	 * @return The first .cfg file in the deploy directory
	 * @throws FileNotFoundException
	 */
	static File getConfigFile() throws FileNotFoundException {
		FilenameFilter fnf = new FilenameFilter() {
			@Override
			public boolean accept(File d, String n) {
				return n.endsWith(".json");
			}
		};
		
		File[] dir = Filesystem.getDeployDirectory().listFiles(fnf);
		
		if(dir.length == 0) throw new FileNotFoundException("No .json files found in deploy directory");
		
		return dir[0];
	}
	
	/**
	 * Gets the config file to read from
	 * The file is specified in this implementation
	 * 
	 * @param fileName The config file to read from
	 * @return The config file
	 * @throws FileNotFoundException
	 */
	static File getConfigFile(String fileName) throws FileNotFoundException {
		File[] dir = Filesystem.getDeployDirectory().listFiles();
		
		if(dir.length == 0) throw new FileNotFoundException("No files in deploy directory");
		
		for(File f : dir) {
			System.out.println(f.getName());
			if(f.getName().equals(fileName)) return f;
		}
		
		throw new FileNotFoundException("Secified config file not found");
	}
}
