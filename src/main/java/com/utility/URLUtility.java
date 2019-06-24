package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.pages.AWSPage;

public class URLUtility {

	public static SoftAssert as = new SoftAssert();

	/*
	 * public static void main(String[] args) {
	 * 
	 * System.out.println(getDatafromCustomisedLinkSheet()); }
	 */

	public static void verifyLinkActive(String linkUrl) {
		try {
			URL url = new URL(linkUrl);

			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

			httpURLConnect.setConnectTimeout(3000);

			httpURLConnect.connect();

			int ExpectedResponseSuccess = 200;

			int ExpectedFileNotFound = 404;

			if (httpURLConnect.getResponseCode() == 200) {
				as.assertEquals(200, ExpectedResponseSuccess);

				Reporter.log("200 ---" + linkUrl, true);
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				as.assertFalse(404 == ExpectedFileNotFound);

				Reporter.log("404 ---" + linkUrl, true);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static String getDatafromCustomisedLinkSheet() {
		String value = null;
/*
		Workbook wb;
		try {
			wb = WorkbookFactory.create(new File(".//WEBPLEX_CUSTOMISEDLINK.xlsx"));
			value = wb.getSheet("Staging URL").getRow(1).getCell(URLUtility.getStagingURL()).getStringCellValue();

		} catch (Exception e) {
		}
		*/
		Map<String, String> entityFieldsDetailMap = null;
		
		Map<String, List<String>> dataTreeMap = FileHandlingOperationUtility.readFromCSVFile(".//WEBPLEX_CUSTOMISEDLINK.csv", "");
		 Set<String> entityKeyList = dataTreeMap.keySet();
		 for (int key = 1; key < entityKeyList.size(); key++) {
			 entityFieldsDetailMap =
		    		  FileHandlingOperationUtility.getEntityDetailsFromMap(Integer.toString(key), dataTreeMap);
		 }
		 String WebsiteName = null;
		try {
			WebsiteName = URLUtility.PropertyFile("WebsiteName");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 value = entityFieldsDetailMap.get(WebsiteName);
		return value;
	}

	public static int getStagingURL() throws IOException {
		String WebsiteName = URLUtility.PropertyFile("WebsiteName");
		String toFind = WebsiteName;
		String CountryPosition = null;

		try {
			File myFile = new File(".//WEBPLEX_CUSTOMISEDLINK.xlsx");
			FileInputStream fis = new FileInputStream(myFile);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			DataFormatter formatter = new DataFormatter();
			Sheet sheet1 = wb.getSheet("Staging URL");
			for (Row row : sheet1) {
				for (Cell cell : row) {
					CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

					// get the text that appears in the cell by getting the cell
					// value and applying any data formats (Date, 0.00, 1.23e9,
					// $1.23, etc)
					String text = formatter.formatCellValue(cell);

					// is it an exact match?
					if (toFind.equals(text)) {
						String Position = cellRef.formatAsString();
						String sourceString = Position;
						String[] varArr = sourceString.split("");
						CountryPosition = varArr[0];
						;
					}
				}
			}

		}

		catch (Exception e) {
			System.out.println(e);
		}
		return URLUtility.titleToNumber(CountryPosition);
	}

	public static String PropertyFile(String Value) throws IOException {
		Properties prop = null;

		try {

			prop = new Properties();

			FileInputStream file = new FileInputStream("./config/ConfigInputFile.properties");

			prop.load(file);

		} catch (Exception e) {
			System.out.println(e);
		}
		return prop.getProperty(Value);
	}

	public static int titleToNumber(String s) {
		int result = 0;
		for (int i = 0; i < s.length(); i++) {
			result *= 26;
			result += s.charAt(i) - 'A';
		}
		return result;
	}

	public static String findURLAWS() throws InterruptedException {
		String StageURL = getDatafromCustomisedLinkSheet();
		int end = StageURL.length();
		String CustomisedURL = StageURL.substring(8, end);
		return CustomisedURL;
	}

	public static void compareLinkTextValue() throws InterruptedException {
		AWSPage aws = new AWSPage();
		String newCustomisedURL = findURLAWS() + ".edgekey.net.";
		System.out.println(newCustomisedURL);
		SoftAssert as = new SoftAssert();
		as.assertEquals(aws.getTextAreaValue(), newCustomisedURL, "Both the settings are not matching");
		as.assertAll();
	}
}
