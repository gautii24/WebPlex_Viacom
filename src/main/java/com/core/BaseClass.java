package com.core;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.utility.FileHandlingOperationUtility;
import com.utility.GoogleSheetAPI;
import com.utility.PropertyReader;

public class BaseClass {
	protected static WebDriver driver;
	protected Sheets service;
	private String spreadsheetId;// =
									// "1RrFEnCddIAJFkOZELVdnXWdGmNDLTttSmVIzrQWLkzc";
	private String range; // = "UserInfo!A1:DL";
	protected Map<String, LinkedHashMap<String, String>> dataTree;
	public static Map<String, Map<String, LinkedHashMap<String, String>>> sheetMap;

	@BeforeSuite
	public void bedoreSuite() throws IOException {
		service = GoogleSheetAPI.getSheetsService();
	}

	@BeforeTest
	public void getSpreadSheetData() {
		sheetMap = new HashMap<String, Map<String, LinkedHashMap<String, String>>>();
		System.out.println("In Before Test.......");
		Properties prop = PropertyReader.getPropertyObject();
		for (Enumeration<?> e = prop.propertyNames(); e.hasMoreElements();) {
			String name = (String) e.nextElement();
			String value = prop.getProperty(name);
			System.out.println(name);
			System.out.println(value);

			if (name.contains("spreadsheetId")) {
				spreadsheetId = value;
			}

			if (name.startsWith("sheet")) {
				range = value;
				ValueRange response = null;
				try {
					response = service.spreadsheets().values().get(spreadsheetId, range).execute();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				List<List<Object>> values = response.getValues();

				dataTree = FileHandlingOperationUtility.getEntityDetailsFromMap(values);
				sheetMap.put(range.substring(0, range.lastIndexOf("!")), dataTree);
			}

		}
	}

	@BeforeMethod
	public void navigateToStash() {
		System.out.println("In Before BeforeMethod.......");
		System.setProperty("webdriver.chrome.driver", "./browserdrivers/chromedriver.exe");
		driver = new ChromeDriver();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
