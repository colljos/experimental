package com.java7developer.chapter1.listing_1_3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class TryWithResourcesSimple {

	private void run() throws IOException {
		
		File file = new File("foo");		
		URL url = null;
		try {
			url = new URL("http://www.google.com/");
		} catch (MalformedURLException e) { }
		
		try (FileOutputStream fos = new FileOutputStream(file); 
		     InputStream is = url.openStream() ) { 
		    byte[] buf = new byte[4096];
		    int len;
		    while ((len = is.read(buf)) > 0) {
		      fos.write(buf, 0, len);
		    }
		  }
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		TryWithResourcesSimple instance = new TryWithResourcesSimple();
		instance.run();
	}

	
}
