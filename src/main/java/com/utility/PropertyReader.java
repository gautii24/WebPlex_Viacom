package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class PropertyReader {
	static Properties prop = new Properties();
	static {
		File file = new File("./config/prop.properties");

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getPropertyObject() {
		return prop;
	}
	
//	public static void main(String[] args) {
//		for (Enumeration<?> e = prop.; e.hasMoreElements(); ) {
//		    String name = (String)e.nextElement();
//		    String value = prop.getProperty(name);
//		    System.out.println(name);
//		    System.out.println(value);
//		    // now you have name and value
//		    if (name.startsWith("Appname")) {
//		        // this is the app name. Write yor code here
//		    }
//	}
	}
	


