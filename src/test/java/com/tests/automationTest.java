package com.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.core.BaseClass;
import com.pages.AWSPage;
import com.pages.WebPlex;
import com.utility.URLUtility;

public class automationTest extends BaseClass {

	static HashMap<String, String> jsonData;
	Map<String, LinkedHashMap<String, String>> spreadSheetData;
	ArrayList<String> optionsData = new ArrayList<>();

	/**
	 * This method used to compare spreadsheet data with Stash files
	 * 
	 * @param webplexFieldNames
	 * @return
	 */
	@Test
	public void verifySpreadSheetData() throws IOException {
		WebPlex webPlex = new WebPlex();
		webPlex.getDataFromCSV();

		driver.get("https://stash.mtvi.com/projects/WEBPLEX/repos/webplex/browse");
		webPlex.getSpreadsheetData();

		webPlex.login();
		for (int branch = 0; branch < webPlex.branches.length; branch++) {
			if (webPlex.branches[branch].equals("na")) {
				break;
			}
			webPlex.enterBranchName(webPlex.branches[branch]);
			webPlex.selectBranch(webPlex.branches[branch]);
			webPlex.navigateTillConfigFolder();

			for (int site = 0; site < webPlex.sites.length; site++) {
				String[] folder = webPlex.folders[site].split(";");
				for(int folderCnt=0; folderCnt < folder.length; folderCnt++) {
					if(folder[folderCnt].contains("/")) {
						webPlex.selectSitesConfigFolder(folder[folderCnt]);
						//webPlex.verifyHostsJSFile(site);
						//webPlex.verifyOptionsDefaultJSFile(site);
						webPlex.verifyReportingAdsDefaultsFile(site);
						webPlex.clickOnConfigBreadcrumbLink();
					} else {
						webPlex.selectSitesConfigFolder(folder[folderCnt]);
						webPlex.clickOnConfigBreadcrumbLink();
					}
				}
			}
			webPlex.clickOnWebPlexFolder();
		}

		if (webPlex.status.equals(false)) {
			Assert.fail("");
		}
	}

	/**
	 * This method used to validate broken links
	 * 
	 * @param
	 * @return
	 */
	// @Test(priority=1)
	public void verifyBrokenLinks() {
		driver.get(URLUtility.getDatafromCustomisedLinkSheet());
		List<WebElement> AllLinks = driver.findElements(By.xpath("//a"));

		int count = AllLinks.size();

		// System.out.println(count);

		for (int i = 0; i < count; i++) {
			WebElement Link = AllLinks.get(i);

			String abc = Link.getAttribute("href");

			// System.out.println(abc);

			URLUtility.verifyLinkActive(abc);

		}
		URLUtility.as.assertAll();
	}

	// @Test(priority=2)
	public void verifyAWSSettings() throws InterruptedException {

		driver.get(
				"https://fs.viacomcloud.com/adfs/ls/idpinitiatedsignon.aspx?logintoRP=urn:amazon:webservices&IsPassive=false&forceauthentication=false");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		AWSPage awsPage = new AWSPage();

		awsPage.enterUsername();

		awsPage.enterPassword();

		awsPage.clickLoginButton();

		awsPage.clickRoute53();

		awsPage.clickHostedZones();

		awsPage.clickWebplexIo();

		awsPage.enterURLSearchTab();

		awsPage.clickSearchedLink();

		awsPage.selectCname();

		awsPage.getTextAreaValue();

		URLUtility.compareLinkTextValue();
	}

	public static void printJSON(JSONObject jsonObj) {
		String mainKey = null;
		try {
			for (Object keyObj : jsonObj.keySet()) {

				String key = (String) keyObj;
				Object valObj = jsonObj.get(key);

				if (valObj instanceof JSONObject) {
					// call printJSON on nested object
					printJSON((JSONObject) valObj);
					mainKey = key;
				} else {
					// print key-value pair
					// System.out.println("key : " + mainKey);
					// System.out.println("value : " + valObj.toString());
					jsonData.put(mainKey, valObj.toString());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void test() {
		String subject1 = "text2(text3, text4), text5(text6, text7)";
		String subject2 = "text2, text3(text4, text5), text6, text7";
		String sub = "manikanta, Santhosh, ramakrishna(mani, text(santhosh,dd)), tester";
		String sub1 = "{options: {brand: 'KCA SE',apiEndPoint: {region: 'se'},nickApiEndPoint: {namespace: 'kca.nickelodeon.se'},poeditorEndPoint: {language: ['sv', 'en-us']},newRelicBrowser: {dev: {appId: '203044131'},stage: {appId: '203044018'},prod: {appId: '203043873'}},locale: 'se',timezone: 'Europe/Stockholm'}}";
		// String p = "\s*(\"[^\"]*\"|\([^)]*\)|[^,]+)";
		String[] res = sub1.split(",(?![^{]*\\})");
		System.out.println(Arrays.toString(res));
	}
}
