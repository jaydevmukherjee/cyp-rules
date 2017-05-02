package org.copart.cyp.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FetchProperties {
	
	public static void main(String[] args) {
		FetchProperties fetchProperties = new FetchProperties();
		fetchProperties.getProperty();
		System.out.println(System.getProperty("wikiLink"));
		System.out.println(System.getProperty("wikiUserName"));
		System.out.println(System.getProperty("wikiPassword"));
	}

	public void getProperty(){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(System.getProperty("user.dir")+"/config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			/*System.out.println(prop.getProperty("wikiLink"));
			System.out.println(prop.getProperty("wikiUserName"));
			System.out.println(prop.getProperty("wikiPassword"));*/
			
			System.setProperty("wikiLink", prop.getProperty("wikiLink"));
			System.setProperty("wikiUserName", prop.getProperty("wikiUserName"));
			System.setProperty("wikiPassword", prop.getProperty("wikiPassword"));
			System.setProperty("deRulesXML", prop.getProperty("deRulesXML"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
