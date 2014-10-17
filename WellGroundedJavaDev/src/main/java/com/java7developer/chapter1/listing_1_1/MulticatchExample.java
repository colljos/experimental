package com.java7developer.chapter1.listing_1_1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class MulticatchExample {

public static class Configuration {
		
	}

	public static class ConfigurationException extends Exception {
		
	}

	public Configuration getConfig(String fileName_) {
		
		Configuration cfg = null;
		try {
			String fileText = getFile(fileName_);
			cfg = verifyConfig(parseConfig(fileText));
		} catch (FileNotFoundException fnfx) {
			System.err.println("Config file "+ fileName_ +" is missing");
		} catch (IOException e) {
			System.err.println("Error while processing file "+ fileName_);
		} catch (ConfigurationException e) {
			System.err.println("Config file "+ fileName_ +" is not consistent");
		} catch (ParseException e) {
			System.err.println("Config file "+ fileName_ +" is malformed");
		}
			
		return cfg;
	}
	
	private Configuration verifyConfig(Configuration parseConfig) throws ConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	// throws ParseException if file is malformed
	private Configuration parseConfig(String fileText) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	// Can throw a FileNotFoundException if file doesn't exist, or IOException if something bad happens
	// while trying to read from it
	private String getFile(String fileName_) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
