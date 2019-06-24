package com.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.core.BaseClass;
import com.dataobject.WebplexDataObject;
import com.dataobject.WebplexFieldNames;
import com.pages.AWSPage;
import com.pages.WebPlex;
import com.utility.FileHandlingOperationUtility;
import com.utility.URLUtility;

public class automationTest2 extends BaseClass {

	static HashMap<String, String> jsonData;
	Map<String, LinkedHashMap<String, String>> spreadSheetData;
	ArrayList<String> optionsData = new ArrayList<>();
	/**
	 * This method used to compare spreadsheet data with Stash files
	 * 
	 * @param webplexFieldNames
	 * @return
	 */
	@Test(dataProvider = "getInputData", dataProviderClass = WebplexDataObject.class, priority = 0)
	public void verifySpreadSheetData(WebplexFieldNames webplexFieldNames) throws IOException {
		
		//test();
		
	   //,(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)
		ArrayList<String> hostsHeaderNames = FileHandlingOperationUtility.getHeaderNames("./src/main/resources/hosts.csv");
		ArrayList<String> inputDataHeaderNames = FileHandlingOperationUtility.getHeaderNames("./src/main/resources/InputData.csv");
		ArrayList<String> optionsDefaultHeaderNames = FileHandlingOperationUtility.getHeaderNames("./src/main/resources/optionsDefaults.csv");
		
		String[] contexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/hosts.csv", hostsHeaderNames.get(0));
		String[] subContexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/hosts.csv", hostsHeaderNames.get(1));
		String[] webplexKey = FileHandlingOperationUtility.getKeyValues("./src/main/resources/hosts.csv", hostsHeaderNames.get(2));
		
		String[] branches = FileHandlingOperationUtility.getKeyValues("./src/main/resources/InputData.csv", inputDataHeaderNames.get(0));
		String[] folders = FileHandlingOperationUtility.getKeyValues("./src/main/resources/InputData.csv", inputDataHeaderNames.get(1));
		String[] sites = FileHandlingOperationUtility.getKeyValues("./src/main/resources/InputData.csv", inputDataHeaderNames.get(2));
		
		
		String[] optDefContexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv", optionsDefaultHeaderNames.get(0));
		String[] optDefSubContexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv", optionsDefaultHeaderNames.get(1));
		String[] optDefWebplexkey = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv", optionsDefaultHeaderNames.get(2));
		String[] optDefFiles = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv", optionsDefaultHeaderNames.get(4));
		String[] optDefIsObj = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv", optionsDefaultHeaderNames.get(3));
		
		WebPlex webPlex = new WebPlex();
		webPlex.getDataFromCSV();
		
		
		
		driver.get("https://stash.mtvi.com/projects/WEBPLEX/repos/webplex/browse");
		spreadSheetData = new TreeMap<String, LinkedHashMap<String, String>>();
		HashMap<String, String> entityFieldsDetailMap1 = new HashMap<String, String>();
		spreadSheetData = sheetMap.get("UserInfo");
		Set<String> entityKeyList = spreadSheetData.keySet();
		int incr = 0;
		String folderName = null;
		Map<String, String> tveWebPlexKey = new LinkedHashMap<String, String>();
		jsonData = new HashMap<String, String>();
		
		webPlex.login();
		
		String[] filesArray = webplexFieldNames.getFile().split(",");
		
		
		for(int branch=0; branch<branches.length; branch++) {
			if(branches[branch].equals("na")) {
				break;
			}
			webPlex.enterBranchName(branches[branch]);
			webPlex.selectBranch(branches[branch]);
			webPlex.navigateTillConfigFolder();
		
		for(int site=0; site < sites.length; site++) {
			webPlex.selectSitesConfigFolder(folders[site]);
		
			
			////host files validation
		/*for(int cnt=0; cnt < contexts.length; cnt++) {
			
			String txtContext =  contexts[cnt];
			String txtSubContext =  subContexts[cnt];
			String txtWebplexKey = webplexKey[cnt];
			
		for (int key2 = 1; key2 <= entityKeyList.size(); key2++) {
			entityFieldsDetailMap1 = spreadSheetData.get(Integer.toString(key2));
			 ArrayList<String> data = new ArrayList<>();
			
				if (entityFieldsDetailMap1.get(hostsHeaderNames.get(0)).equals(txtContext)
						&& entityFieldsDetailMap1.get(hostsHeaderNames.get(1)).equals(txtSubContext)) {
						String siteName = entityFieldsDetailMap1.get(sites[site]);
						String jsData = "{hosts: ";//+txtWebplexKey+": '"+siteName+"'}}";
						data.add(jsData);
						data.add("{"+txtWebplexKey+": '"+siteName+"'}}");
						webPlex.clickOnConfigSettingFile(files[cnt]);
						webPlex.clickOnRawViewLink();
						//webPlex.validateHostsData(siteName);
						webPlex.validateJSData(data);
						webPlex.returnToMainFolder();
						break;
					 
				}
				
			}
		}*/
			boolean flag = false, isSubContext = false;
			String dup = "xx";
			StringBuilder builder = null;
					
			for(int options=0; options<1; options++) {
				
			
		for(int cnt=0; cnt < optDefContexts.length; cnt++) {
			String txtContext =  optDefContexts[cnt];
			String txtSubContext =  optDefSubContexts[cnt];
			String txtWebplexKey =  optDefWebplexkey[cnt];
			String isObj = optDefIsObj[cnt];
			int totalKey;
			
			if(cnt==0) {
				optionsData.add("{ options: {");
			}
			
			if(!(dup.equals(txtContext))) {
				try {
				if(builder.length()!=0) {
					if(isSubContext) {
						builder.append(" }");
					}
					optionsData.add(builder.toString());
				}
				} catch(Exception e) {
					System.out.println(e);
				}
				flag = true;
				builder = new StringBuilder();
			}

			/*String[] webplexArray = txtWebplexKey.split(";");
			if(!txtWebplexKey.equals("")) {
				
				totalKey = webplexArray.length;
			} else {
				totalKey = 1;
			}*/
			
			//outerloop:
			//	for(int webplex=0; webplex<totalKey; webplex++) {
					
			for (int key2 = 1; key2 <= entityKeyList.size(); key2++) {
				entityFieldsDetailMap1 = spreadSheetData.get(Integer.toString(key2));
				dup = txtContext;
				
					if (entityFieldsDetailMap1.get(optionsDefaultHeaderNames.get(0)).equals(txtContext)
							&& entityFieldsDetailMap1.get(optionsDefaultHeaderNames.get(1)).equals(txtSubContext)
							&& entityFieldsDetailMap1.get(optionsDefaultHeaderNames.get(2)).equals(txtWebplexKey)) {
						String siteName = entityFieldsDetailMap1.get(sites[site]);
						if(!siteName.equals("")) {
							
						
						
						if(txtWebplexKey.equals("")) {
							if(siteName.contains("[")) {
								siteName = siteName.replace("[", "").replace("]", "");
								String[] values = siteName.split(",");
								StringBuilder build = new StringBuilder();
								build.append("[");
								for (int cnt1=0; cnt1<values.length; cnt1++) {
									if(cnt1==values.length-1) {
										build.append("'"+values[cnt1]+"'");
									} else {
										build.append("'"+values[cnt1]+"'"+", ");
									}
									
								}
								build.append("]");
								optionsData.add(txtContext+": "+build);
							} else {
								optionsData.add(txtContext+": '"+siteName+"'");
							}

						} else {
							if(isObj.equals("n")) {
								if(siteName.contains("[")) {
									siteName = siteName.replace("[", "").replace("]", "");
									String[] values = siteName.split(",");
									StringBuilder build = new StringBuilder();
									build.append("[");
									for (int cnt1=0; cnt1<values.length; cnt1++) {
										if(cnt1==values.length-1) {
											build.append("'"+values[cnt1]+"'");
										} else {
											build.append("'"+values[cnt1]+"'"+", ");
										}
										
									}
									build.append("]");
									optionsData.add(txtWebplexKey+": "+build);
								} else {
									optionsData.add(txtWebplexKey+": '"+siteName+"'");
								}
								
							} else {
								if(flag) {
									if(!txtSubContext.equals("")) {
										//optionsData.add(txtContext +": {" +txtSubContext+": {" +txtWebplexKey+": '"+siteName+"'}");
										flag = false;
										isSubContext = true;
										
										if(siteName.contains("[")) {
											siteName = siteName.replace("[", "").replace("]", "");
											String[] values = siteName.split(",");
											StringBuilder build = new StringBuilder();
											build.append("[");
											for (int cnt1=0; cnt1<values.length; cnt1++) {
												if(cnt1==values.length-1) {
													build.append("'"+values[cnt1]+"'");
												} else {
													build.append("'"+values[cnt1]+"'"+", ");
												}
												
											}
											build.append("]");
											
											builder.append(txtContext +": { " +txtSubContext+": { " +txtWebplexKey+": "+build+" }");
										} else {
											builder.append(txtContext +": { " +txtSubContext+": { " +txtWebplexKey+": '"+siteName+"' }");
										}
										
										
									} else {
										//optionsData.add(txtContext +": {" +txtWebplexKey+": '"+siteName+"'}");
										flag = false;
										isSubContext = true;
										
										if(siteName.contains("[")) {
											siteName = siteName.replace("[", "").replace("]", "");
											String[] values = siteName.split(",");
											StringBuilder build = new StringBuilder();
											build.append("[");
											for (int cnt1=0; cnt1<values.length; cnt1++) {
												if(cnt1==values.length-1) {
													build.append("'"+values[cnt1]+"'");
												} else {
													build.append("'"+values[cnt1]+"'"+", ");
												}
												
											}
											build.append("]");
											builder.append(txtContext +": { " +txtWebplexKey+": "+build);
										} else {
											
											builder.append(txtContext +": { " +txtWebplexKey+": '"+siteName+"'");
										}
										
										
									}
									
								} else {
									if(!txtSubContext.equals("")) {
										//optionsData.add(","+txtSubContext +": {" +txtWebplexKey+": '"+siteName+"'}" );
										builder.append(", "+txtSubContext +": { " +txtWebplexKey+": '"+siteName+"' }");
									} else {
										//optionsData.add(","+txtContext +": {" +txtWebplexKey+": '"+siteName+"'}");
										if(siteName.contains("[")) {
											siteName = siteName.replace("[", "").replace("]", "");
											String[] values = siteName.split(",");
											StringBuilder build = new StringBuilder();
											build.append("[");
											for (int cnt1=0; cnt1<values.length; cnt1++) {
												if(cnt1==values.length-1) {
													build.append("'"+values[cnt1]+"'");
												} else {
													build.append("'"+values[cnt1]+"'"+",");
												}
												
											}
											build.append("]");
											builder.append(", "+txtWebplexKey+": "+build);
										} else {
											
											builder.append(", "+txtWebplexKey+": '"+siteName+"'");
										}
										
										
									}
									
								}
								
							}

						}
						}
						System.out.println("break....");
							 break;
					}
				}
				//}
			
			if(cnt== optDefContexts.length-1) {
				if(isSubContext && builder.length()!=0) {
					builder.append(" }");
					optionsData.add(builder.toString());
				}
				
				
				optionsData.add("} }");
			}
			
		}
		webPlex.clickOnConfigSettingFile(optDefFiles[options]);
		webPlex.clickOnRawViewLink();
		//webPlex.validateHostsData(siteName);
		webPlex.validateJSData(optionsData,"");
		webPlex.returnToMainFolder();
		
			}
		webPlex.clickOnConfigBreadcrumbLink();
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
	    //String p = "\s*(\"[^\"]*\"|\([^)]*\)|[^,]+)";
	    String[] res = sub1.split(",(?![^{]*\\})");
	    System.out.println(Arrays.toString(res));
	}
}
