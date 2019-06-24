package com.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import com.core.BaseClass;
import com.utility.FileHandlingOperationUtility;
import com.utility.PropertyReader;

public class WebPlex extends BaseClass {

	WebPlexPageObject webPlexPageObject;
	String lastFolder;

	public WebPlex() {
		webPlexPageObject = new WebPlexPageObject(driver);
	}

	public void login() {
		Properties prop = PropertyReader.getPropertyObject();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webPlexPageObject.userName.sendKeys(prop.getProperty("stashUserName"));
		webPlexPageObject.password.sendKeys(prop.getProperty("stashPassword"));
		webPlexPageObject.submitBtn.click();
	}

	public void enterBranchName(String branch) {
		try {
			Thread.sleep(3000);
			webPlexPageObject.branchDropdown.click();
			Thread.sleep(3000);
			webPlexPageObject.branchInputBox.sendKeys(branch);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void selectBranch(String branch) {
		try {
			Thread.sleep(3000);
			webPlexPageObject.selectBranchFromDropdown(branch).click();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void clickOnPackagesFolder() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webPlexPageObject.packagesFolder.click();
	}

	public void clickOnAppsFolder() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webPlexPageObject.appsFolder.click();
	}

	public void clickOnWebPlexAppsFolder() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webPlexPageObject.webplexAppsFolder.click();
	}

	public void clickOnConfigFolder() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webPlexPageObject.configFolder.click();
	}

	public void clickOnConfigBreadcrumbLink() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webPlexPageObject.configBreadcrumbLink.click();
	}

	public void clickOnWebPlexFolder() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webPlexPageObject.webPlexFolder.click();
	}

	public void navigateTillConfigFolder() {
		try {
			clickOnPackagesFolder();
			clickOnAppsFolder();
			clickOnWebPlexAppsFolder();
			clickOnConfigFolder();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void selectSitesConfigFolder(String folder) {
		String[] sites = folder.split("/");
		lastFolder = sites[sites.length - 1];
		try {
			for (int cnt = 0; cnt < sites.length; cnt++) {
				Thread.sleep(3000);
				webPlexPageObject.selectSiteFolder(sites[cnt]).click();
				;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void clickOnRawViewLink() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webPlexPageObject.rawViewLink.click();
	}

	public void clickOnConfigSettingFile(String fileName) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			List<WebElement> files = webPlexPageObject.configFiles;
			for (int cnt = 0; cnt < files.size(); cnt++) {
				// System.out.println(files.get(cnt).getAttribute("data-item-name"));
				if (files.get(cnt).getAttribute("data-item-name").equals(fileName)) {
					webPlexPageObject.selectconfigFile(Integer.toString(cnt + 1)).click();
					break;
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public List<String> getData() {
		try {
			List<String> jsonList = new ArrayList<String>();
			for (int cnt = 0; cnt < webPlexPageObject.json.size(); cnt++) {
				String strArray = webPlexPageObject.json.get(cnt).getText();
				if (strArray.equals("module.exports = {")) {
					strArray = strArray.substring(17, strArray.length());
				}
				if (!strArray.contains("const") && !strArray.equals("")) {
					jsonList.add(strArray.trim());
				}

			}
			return jsonList;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public String getJsData() {
		String strJson = webPlexPageObject.jsData.getText();
		try {
			String[] firstStr1;
			String firstStr, secondStr;
			if(strJson.contains("const LIBRARY_VERSIONS")) {
				strJson = strJson.substring(strJson.indexOf(")")+1, strJson.length()).trim();
			}
			strJson = strJson.substring(17, strJson.length());
			// strJson = strJson.substring(31, strJson.length());
			// String[] array = strJson.split("\n");
			firstStr1 = strJson.split("\n");
			strJson = strJson.replace("\n ", "");
			firstStr = firstStr1[0];
			secondStr = firstStr1[1];
			// strJson = strJson.replace("{ ", "{");
			// strJson = strJson.replaceAll("^ +| +$|{}+", "$1");
			strJson = StringUtils.normalizeSpace(strJson);
			return strJson;
		} catch (Exception e) {
			System.out.println(e);
		}
		return strJson;
	}

	public void validateJSData(ArrayList<String> data, String fileName) {

		boolean flag = false;
		StringBuilder strbuild = new StringBuilder();
		ArrayList<String> arrayList = new ArrayList<>();
		String strJsData = getJsData();
		// data.clear();
		// String jsData = " {name: 'local1.paramountnetwork.com'}";
		// data.add(jsData);

		String[] strArr = strJsData.split(" ");
		String firstTxt = strArr[0] + " " + strArr[1] + " " + strArr[2];
		arrayList.add(firstTxt);
		strJsData = strJsData.replace(firstTxt, "");

		String last = strJsData.substring(strJsData.length() - 3, strJsData.length());
		
		try {
			Assert.assertEquals(firstTxt, data.get(0),"Missing something on First line under module.exports ");
		} catch (AssertionError e) {
			status = false;
			Reporter.log(e+ "<br>");
		}
		
		try {
			Assert.assertEquals(last, data.get(data.size()-1), "Missing last } bracket");
		} catch (AssertionError e) {
			status = false;
			Reporter.log(e+ "<br>");
		}
		
		if (strJsData.contains("apiEndPoint: { brand: 'paramountnetwork', region: 'it' }")) {
			System.out.println("yes");
		}
		// , (?![^{]*\\})
		String[] tokens = strJsData.split(",(?![^\\{]*\\})|,(?!(.*)\\{(.*?)\\})");

		for (int a = 0; a < tokens.length; a++) {
			System.out.println(tokens[a]);

			String str = tokens[a];
			int count = 0, count1 = 0;

			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == '{')
					count++;
				if (str.charAt(i) == '}')
					count1++;
			}
			System.out.println(count);
			System.out.println(count1);
			if (count > count1) {
				strbuild = new StringBuilder();
				flag = true;
				strbuild.append(str.trim());
			} else if (count < count1 && flag && count1<=2) {
				flag = false;
				strbuild.append(", ").append(str.trim());
				arrayList.add(strbuild.toString());
			} else if (count == count1 && flag) {
				strbuild.append(", ").append(str.trim());
			} else if (count < count1 && flag==false && count1<=2) {
				String[] st = str.split("\\}");
				System.out.println(st[0]);
				arrayList.add(st[0].trim());
				arrayList.add("} }");
			} else if (count < count1 && count1 > 2) {
				//strbuild = new StringBuilder();
				String[] st = str.split("\\}");
				System.out.println(st[0]+ " } }");
				if (strbuild.length() != 0)
				{
					strbuild.append(", "+st[0].trim()+ " } }");
				} else {
					strbuild.append(st[0].trim()+ " } }");
				}
				
				arrayList.add(strbuild.toString());
				arrayList.add("} }");
			} else {
				strbuild = new StringBuilder();
				strbuild.append(str.trim());
				arrayList.add(strbuild.toString());
				strbuild = new StringBuilder();
			}

		}

		ArrayList<String> listOne = data;

		ArrayList<String> listTwo = arrayList;
		int incr=0;
		if ((data.size()) == arrayList.size()) {
			System.out.println("Both are matched");
			for (int i = 0; i < listTwo.size(); i++) {
				if (listOne.contains(listTwo.get(i).trim())) {
					System.out.println("Exist : " + listTwo.get(i));
				} else {
					System.out.println("Not Exist : " + listTwo.get(i));
					if(incr==0) {
						Reporter.log("No match found in file: - " + "<b>"+fileName+"</b>" + "<br>");
						incr++;
					}
					
					try {
						Assert.assertTrue(false);
					} catch (AssertionError e) {
						status = false;
						Reporter.log(listTwo.get(i) + "<br>");
					}
				}
			}
		} else if (data.size() < arrayList.size()) {
			System.out.println("Something extra in the file");
			for (int i = 0; i < listTwo.size(); i++) {
				if (listOne.contains(listTwo.get(i).trim())) {
					System.out.println("Exist : " + listTwo.get(i));
				} else {
					System.out.println("Not Exist : " + listTwo.get(i));
					if(incr==0) {
						Reporter.log("Mismatch or Additional in file - " + "<b>"+fileName+"</b>" + "<br>");
						incr++;
					} 
					
					try {
						Assert.assertTrue(false);
					} catch (AssertionError e) {
						status = false;
						Reporter.log("-" + listTwo.get(i) + "<br>");
					}
				}
			}

			// listOne.removeAll(listTwo);
			// System.out.println(listOne);
		} else {
			System.out.println("Something missing in the file");
			try {
			for (int i = 0; i < listOne.size(); i++) {
				if (listTwo.contains(listOne.get(i).trim())) {
					System.out.println("Exist : " + listOne.get(i));
				} else {
					System.out.println("Not Exist : " + listOne.get(i));
					if(incr==0) {
						Reporter.log("Mismatch or Missing in file: - " + "<b>"+fileName+"</b>" + "<br>");
						incr++;
					}
					
					try {
						Assert.assertTrue(false);
					} catch (AssertionError e) {
						status = false;
						Reporter.log(listTwo.get(i) + "<br>");
					}
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		}
		/*if (strJsData.contains(firstTxt)) {
			System.out.println("Found..");
		}
*/
		/*for (int cnt = 1; cnt < data.size(); cnt++) {
			System.out.println(data.get(cnt));
			if (strJsData.contains(data.get(cnt))) {
				System.out.println("Found..");

			} else {
				
				 * String[] keyVal = data.get(cnt).split(":"); for(int cntt=0;
				 * cntt<keyVal.length; cntt++) {
				 * if(strJsData.contains(keyVal[cntt])){
				 * System.out.println("Kay Value doesn't find..." +
				 * data.get(cnt)); } else {
				 * System.out.println("Value doesn't match..." + data.get(cnt));
				 * } }
				 
				try {
					Assert.assertTrue(false);
				} catch (AssertionError e) {
					status = false;
					Reporter.log("Missing: " + data.get(cnt) + "<br>" + e.getMessage() + "<br>");
					Reporter.log("-----------------------------");
				}

				System.out.println("Not found..." + data.get(cnt));
			}
		}*/

	}

	public void returnToMainFolder() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.navigate().back();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// webPlexPageObject.selectLastFolder(lastFolder).click();
	}

	public Boolean status;

	public void validateHostsData(String site) {
		status = true;
		List<String> jsonFileData = getData();
		List<String> jsonSpreadSheeteData = getHostsSpreadsheetData(site);
		System.out.println(jsonSpreadSheeteData.containsAll(jsonFileData));
		try {
			Assert.assertEquals(jsonFileData.size(), jsonSpreadSheeteData.size());
		} catch (AssertionError e) {
			status = false;
			Reporter.log("Something extra in .js file" + "<br>" + e.getMessage() + "<br>");
			Reporter.log("-----------------------------");
		}

		if (jsonSpreadSheeteData.containsAll(jsonFileData)) {
			System.out.println("Matched..");
		} else {

		}
		for (int data = 0; data < jsonSpreadSheeteData.size(); data++) {
			try {
				Assert.assertEquals(jsonFileData.get(data), jsonSpreadSheeteData.get(data));
			} catch (AssertionError e) {
				status = false;
				Reporter.log("Issue is in hosts.default.js file" + "<br>" + e.getMessage() + "<br>");
				Reporter.log("-----------------------------");
			}
		}
	}

	public List<String> getHostsSpreadsheetData(String hostName) {
		List<String> hostsData = new ArrayList<String>();
		hostsData.add("{");
		hostsData.add("hosts: {");
		hostsData.add("name: " + "'" + hostName + "'");
		hostsData.add("}");
		// hostsData.add("}");
		return hostsData;
	}

	public void validateTVEData(Map<String, String> tveWebPlexKey) {
		status = true;
		List<String> jsonFileData = getData();
		List<String> jsonSpreadSheeteData = getTVESpreadsheetData(tveWebPlexKey);

		try {
			Assert.assertEquals(jsonFileData.size(), jsonSpreadSheeteData.size());
		} catch (AssertionError e) {
			status = false;
			Reporter.log("Issue is in hosts.default.js file" + "<br>" + e.getMessage() + "<br>");
			Reporter.log("-----------------------------");
		}

		for (int data = 0; data < jsonSpreadSheeteData.size(); data++) {
			try {
				Assert.assertEquals(jsonFileData.get(data), jsonSpreadSheeteData.get(data));
			} catch (AssertionError e) {
				status = false;
				Reporter.log("Issue is in hosts.default.js file" + "<br>" + e.getMessage() + "<br>");
				Reporter.log("-----------------------------");
			}
		}
	}

	public List<String> getTVESpreadsheetData(Map<String, String> tveWebPlexKey) {
		List<String> TVEData = new ArrayList<String>();
		int cnt = 0;
		TVEData.add("{");
		TVEData.add("options: {");
		TVEData.add("tve: {");
		for (@SuppressWarnings("rawtypes")
		Map.Entry entry : tveWebPlexKey.entrySet()) {
			// System.out.println("key: " + entry.getKey() + "; value: " +
			// entry.getValue());
			if (tveWebPlexKey.size() - 1 == cnt) {
				TVEData.remove(",");
			} else {
				if (entry.getKey().toString().contains("default")) {
					if (entry.getValue().equals("TRUE") || entry.getValue().equals("FALSE")) {
						TVEData.add("" + entry.getKey().toString().substring(0, entry.getKey().toString().indexOf(" "))
								+ ": " + entry.getValue().toString().toLowerCase());
						// cnt++;
					} else {
						TVEData.add("" + entry.getKey().toString().substring(0, entry.getKey().toString().indexOf(" "))
								+ ": " + "'" + entry.getValue().toString().toLowerCase() + "'" + ",");
					}
					cnt++;
				} else {
					if (entry.getValue().equals("TRUE") || entry.getValue().equals("FALSE")) {
						TVEData.add("" + entry.getKey() + ": " + entry.getValue().toString().toLowerCase() + ",");
						// cnt++;
					} else {
						if (entry.getValue().toString().contains("{")) {
							TVEData.add("" + entry.getKey() + ": " + "`" + entry.getValue() + "`" + ",");
						} else {
							TVEData.add("" + entry.getKey() + ": " + "'" + entry.getValue() + "'" + ",");
						}

					}
					cnt++;

				}
			}
		}
		TVEData.add("}");
		TVEData.add("}");
		TVEData.add("}");
		return TVEData;
	}

	public void validateOptionsDefaultData(Map<String, String> optionsDefault) {
		status = true;
		List<String> jsonFileData = getData();
		List<String> jsonSpreadSheeteData = getOptionsDefaultSpreadsheetData(optionsDefault);

		try {
			Assert.assertEquals(jsonFileData.size(), jsonSpreadSheeteData.size());
		} catch (AssertionError e) {
			status = false;
			Reporter.log("Issue is in hosts.default.js file" + "<br>" + e.getMessage() + "<br>");
			Reporter.log("-----------------------------");
		}

		for (int data = 0; data < jsonSpreadSheeteData.size(); data++) {
			try {
				Assert.assertEquals(jsonFileData.get(data), jsonSpreadSheeteData.get(data));
			} catch (AssertionError e) {
				status = false;
				Reporter.log("Issue is in hosts.default.js file" + "<br>" + e.getMessage() + "<br>");
				Reporter.log("-----------------------------");
			}
		}
	}

	public List<String> getOptionsDefaultSpreadsheetData(Map<String, String> optionsDefault) {
		List<String> TVEData = new ArrayList<String>();
		int cnt = 0;
		TVEData.add("{");
		TVEData.add("options: {");
		TVEData.add("tve: {");
		for (@SuppressWarnings("rawtypes")
		Map.Entry entry : optionsDefault.entrySet()) {
			// System.out.println("key: " + entry.getKey() + "; value: " +
			// entry.getValue());
			if (optionsDefault.size() - 1 == cnt) {
				TVEData.remove(",");
			} else {
				if (entry.getKey().toString().contains("default")) {
					if (entry.getValue().equals("TRUE") || entry.getValue().equals("FALSE")) {
						TVEData.add("" + entry.getKey().toString().substring(0, entry.getKey().toString().indexOf(" "))
								+ ": " + entry.getValue().toString().toLowerCase());
						// cnt++;
					} else {
						TVEData.add("" + entry.getKey().toString().substring(0, entry.getKey().toString().indexOf(" "))
								+ ": " + "'" + entry.getValue().toString().toLowerCase() + "'" + ",");
					}
					cnt++;
				} else {
					TVEData.add("" + entry.getKey() + ": " + "'" + entry.getValue() + "'" + ",");
					cnt++;

				}
			}
		}
		TVEData.add("}");
		TVEData.add("}");
		TVEData.add("}");
		return TVEData;
	}

	////////////////////////////////////////////////////////////
	public ArrayList<String> hostsHeaderNames;
	public ArrayList<String> inputDataHeaderNames;
	public ArrayList<String> optionsDefaultHeaderNames;

	public String[] contexts;
	public String[] subContexts;
	public String[] files;
	public String[] webplexKey;

	public String[] branches;
	public String[] folders;
	public String[] sites;

	public String[] optDefContexts;
	public String[] optDefSubContexts;
	public String[] optDefWebplexkey;
	public String[] optDefFiles;
	public String[] optDefIsObj;
	
	public String[] repoAdsDefContexts;
	public String[] repoAdsDefSubContexts;
	public String[] repoAdsDefWebplexkey;
	public String[] repoAdsDefFiles;
	public String[] repoAdsDefIsObj;

	ArrayList<String> optionsData = new ArrayList<>();
	static HashMap<String, String> jsonData;
	Map<String, LinkedHashMap<String, String>> spreadSheetData;
	HashMap<String, String> entityFieldsDetailMap1;
	Set<String> entityKeyList;

	public void getDataFromCSV() {
		hostsHeaderNames = FileHandlingOperationUtility.getHeaderNames("./src/main/resources/hosts.csv");
		inputDataHeaderNames = FileHandlingOperationUtility.getHeaderNames("./src/main/resources/InputData.csv");
		optionsDefaultHeaderNames = FileHandlingOperationUtility
				.getHeaderNames("./src/main/resources/optionsDefaults.csv");

		contexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/hosts.csv", hostsHeaderNames.get(0));
		subContexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/hosts.csv",
				hostsHeaderNames.get(1));
		files = FileHandlingOperationUtility.getKeyValues("./src/main/resources/hosts.csv", hostsHeaderNames.get(3));
		webplexKey = FileHandlingOperationUtility.getKeyValues("./src/main/resources/hosts.csv",
				hostsHeaderNames.get(2));

		branches = FileHandlingOperationUtility.getKeyValues("./src/main/resources/InputData.csv",
				inputDataHeaderNames.get(0));
		folders = FileHandlingOperationUtility.getKeyValues("./src/main/resources/InputData.csv",
				inputDataHeaderNames.get(1));
		sites = FileHandlingOperationUtility.getKeyValues("./src/main/resources/InputData.csv",
				inputDataHeaderNames.get(2));

		optDefContexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv",
				optionsDefaultHeaderNames.get(0));
		optDefSubContexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv",
				optionsDefaultHeaderNames.get(1));
		optDefWebplexkey = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv",
				optionsDefaultHeaderNames.get(2));
		optDefFiles = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv",
				optionsDefaultHeaderNames.get(4));
		optDefIsObj = FileHandlingOperationUtility.getKeyValues("./src/main/resources/optionsDefaults.csv",
				optionsDefaultHeaderNames.get(3));
		
		repoAdsDefContexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/reportingAdsDefault.csv",
				optionsDefaultHeaderNames.get(0));
		repoAdsDefSubContexts = FileHandlingOperationUtility.getKeyValues("./src/main/resources/reportingAdsDefault.csv",
				optionsDefaultHeaderNames.get(1));
		repoAdsDefWebplexkey = FileHandlingOperationUtility.getKeyValues("./src/main/resources/reportingAdsDefault.csv",
				optionsDefaultHeaderNames.get(2));
		repoAdsDefFiles = FileHandlingOperationUtility.getKeyValues("./src/main/resources/reportingAdsDefault.csv",
				optionsDefaultHeaderNames.get(4));
		repoAdsDefIsObj = FileHandlingOperationUtility.getKeyValues("./src/main/resources/reportingAdsDefault.csv",
				optionsDefaultHeaderNames.get(3));
		

	}

	public void getSpreadsheetData() {
		try {
			spreadSheetData = new TreeMap<String, LinkedHashMap<String, String>>();
			entityFieldsDetailMap1 = new HashMap<String, String>();
			spreadSheetData = sheetMap.get("UserInfo");
			entityKeyList = spreadSheetData.keySet();
			jsonData = new HashMap<String, String>();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void verifyOptionsDefaultJSFile(int site) {
		//String[] sites1 = folders[site].split("/");
		//lastFolder = sites1[sites1.length - 1];
		boolean flag = false, isSubContext = false, isSubContext1 = false;
		String dup = "xx";
		String dup1 = "xx";
		StringBuilder builder = null;
		boolean flag1 = false;
		boolean flag2 = false;

		for (int options = 0; options < 1; options++) {

			for (int cnt = 0; cnt < optDefContexts.length; cnt++) {
				String txtContext = optDefContexts[cnt];
				String txtSubContext = optDefSubContexts[cnt];
				String txtWebplexKey = optDefWebplexkey[cnt];
				String isObj = optDefIsObj[cnt];
				
				System.out.println("---------------------" + flag2);
				if(txtContext.equals("repo")) {
					System.out.println("Stop");
				}
				
				if (cnt == 0) {
					optionsData.add("{ options: {");
				}

				if (!(dup1.equals(txtSubContext)) && !dup1.equals("")) {
					try {
						if (builder.length() != 0) {
							if (isSubContext1) {
								builder.append(" }");
							}
						}
						flag2 = true;
						isSubContext1 = false;
					} catch (Exception e) {
						System.out.println(e);
					}

				}
				
				
				if (!(dup.equals(txtContext))) {
					try {
						if (builder.length() != 0) {
							if (isSubContext) {
								builder.append(" }");
							}
							optionsData.add(builder.toString());
						}
					} catch (Exception e) {
						System.out.println(e);
					}
					flag = true;
					flag2 = false;
					builder = new StringBuilder();
				}

				for (int key2 = 1; key2 <= entityKeyList.size(); key2++) {
					entityFieldsDetailMap1 = spreadSheetData.get(Integer.toString(key2));
					dup = txtContext;
					dup1 = txtSubContext;
					
					if (entityFieldsDetailMap1.get(optionsDefaultHeaderNames.get(0)).equals(txtContext)
							&& entityFieldsDetailMap1.get(optionsDefaultHeaderNames.get(1)).equals(txtSubContext)
							&& entityFieldsDetailMap1.get(optionsDefaultHeaderNames.get(2)).equals(txtWebplexKey)) {
						String siteName = entityFieldsDetailMap1.get(sites[site]);
					
						if (!siteName.equals("")) {

							if (txtWebplexKey.equals("")) {
								if (siteName.contains("[")) {
									siteName = siteName.replace("[", "").replace("]", "");
									String[] values = siteName.split(",");
									StringBuilder build = new StringBuilder();
									build.append("[");
									for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
										if (cnt1 == values.length - 1) {
											if(values[cnt1].contains("'")) {
												build.append(values[cnt1].trim() );
											} else {
												build.append("'" + values[cnt1].trim() + "'" );
											}
										} else {
											if(values[cnt1].contains("'")) {
												build.append(values[cnt1].trim() + ", ");
											} else {
												build.append("'" + values[cnt1].trim() + "'" + ", ");
											}
											
										}
									}
									build.append("]");
									optionsData.add(txtContext + ": " + build);
								} else {
									optionsData.add(txtContext + ": '" + siteName + "'");
								}

							} else {
								if (isObj.equals("n")) {
									if (siteName.contains("[")) {
										siteName = siteName.replace("[", "").replace("]", "");
										String[] values = siteName.split(",");
										StringBuilder build = new StringBuilder();
										build.append("[");
										for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
											if (cnt1 == values.length - 1) {
												if(values[cnt1].contains("'")) {
													build.append(values[cnt1].trim());
												} else {
													build.append("'" + values[cnt1].trim() + "'");
												}
												
											} else {
												if(values[cnt1].contains("'")) {
													build.append(values[cnt1].trim()+ ", ");
												} else {
													build.append("'" + values[cnt1].trim() + "'" + ", ");
												}
												
											}

										}
										build.append("]");
										optionsData.add(txtWebplexKey + ": " + build);
									} else {
										optionsData.add(txtWebplexKey + ": '" + siteName + "'");
									}

								} else {
									if (flag) {
										if (!txtSubContext.equals("")) {
											// optionsData.add(txtContext +": {"
											// +txtSubContext+": {"
											// +txtWebplexKey+":
											// '"+siteName+"'}");
											flag = false;
											isSubContext = true;
											isSubContext1 = true;
											if (siteName.contains("[")) {
												siteName = siteName.replace("[", "").replace("]", "");
												String[] values = siteName.split(",");
												StringBuilder build = new StringBuilder();
												build.append("[");
												for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
													if (cnt1 == values.length - 1) {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1].trim());
														} else {
															build.append("'" + values[cnt1].trim() + "'");
														}
														
													} else {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1].trim() + ", ");
														} else {
															build.append("'" + values[cnt1].trim() + "'" + ", ");
														}
														
													}

												}
												build.append("]");

												builder.append(txtContext + ": { " + txtSubContext + ": { "
														+ txtWebplexKey + ": " + build + " }");
											} else {
												
												builder.append(txtContext + ": { " + txtSubContext + ": { "
														+ txtWebplexKey + ": '" + siteName + "'");
												/*if (!pretxtContext.equals(txtContext) && !pretxtSubContext.equals(txtSubContext)) {
													pretxtContext = txtContext;
													pretxtSubContext = txtSubContext;
													flag1 =true;
													builder.append(txtContext + ": { " + txtSubContext + ": { "
															+ txtWebplexKey + ": '" + siteName + "' ");
												} else {
													flag1 = false;
												}*/
											
												/*if(flag1) {
													builder.append(", " + txtWebplexKey + ": '" + siteName + "' ");
												} else {
													builder.append("} }");
												}*/
												
												/*builder.append(txtContext + ": { " + txtSubContext + ": { "
														+ txtWebplexKey + ": '" + siteName + "' }");*/
											}

										} else {
											// optionsData.add(txtContext +": {"
											// +txtWebplexKey+":
											// '"+siteName+"'}");
											flag = false;
											isSubContext = true;
											//isSubContext1 = true;
											if (siteName.contains("[")) {
												siteName = siteName.replace("[", "").replace("]", "");
												String[] values = siteName.split(",");
												StringBuilder build = new StringBuilder();
												build.append("[");
												for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
													if (cnt1 == values.length - 1) {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1].trim());
														} else {
															build.append("'" + values[cnt1].trim() + "'");
														}
													} else {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1].trim() + ", ");
														} else {
															build.append("'" + values[cnt1].trim() + "'" + ", ");
														}
														
													}

												}
												build.append("]");
												builder.append(txtContext + ": { " + txtWebplexKey + ": " + build);
											} else {

												builder.append(
														txtContext + ": { " + txtWebplexKey + ": '" + siteName + "'");
												flag2 = true;
												isSubContext1 = false;
											}

										}

									} else {
										if (!txtSubContext.equals("")) {
											// optionsData.add(","+txtSubContext
											// +": {" +txtWebplexKey+":
											// '"+siteName+"'}" );
											flag1 = true;
											isSubContext1 = true;
											if(flag2) {
												builder.append(", " + txtSubContext + ": { " + txtWebplexKey + ": '"
														+ siteName + "'");
												flag2 = false;
											} else {
												builder.append(", "+ txtWebplexKey + ": '" + siteName + "'");
											}
											
											
											/*	if (!pretxtContext.equals(txtContext)) {
													pretxtContext = txtContext;
													flag1 =true;
												} else {
													builder.append(", "+ txtWebplexKey + ": '" + siteName + "' }");
												}
											*/
												/*if(flag1) {
													builder.append(", " + txtSubContext + ": { " + txtWebplexKey + ": '"
															+ siteName + "' }");
												} else {
													builder.append(", " + txtSubContext + ": { " + txtWebplexKey + ": '"
															+ siteName + "' }");
												}*/
												
											
											
										} else {
											// optionsData.add(","+txtContext
											// +": {" +txtWebplexKey+":
											// '"+siteName+"'}");
											if (siteName.contains("[")) {
												siteName = siteName.replace("[", "").replace("]", "");
												String[] values = siteName.split(",");
												StringBuilder build = new StringBuilder();
												build.append("[");
												for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
													if (cnt1 == values.length - 1) {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1]);
														} else {
															build.append("'" + values[cnt1] + "'");
														}
														
													} else {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1] + ",");
														} else {
															build.append("'" + values[cnt1] + "'" + ",");
														}
													}

												}
												build.append("]");
												builder.append(", " + txtWebplexKey + ": " + build);
											} else {

												builder.append(", " + txtWebplexKey + ": '" + siteName + "'");
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
	
				if (cnt == optDefContexts.length - 1) {
					if (isSubContext && builder.length() != 0) {
						builder.append(" }");
						optionsData.add(builder.toString());
					}

					optionsData.add("} }");
				}

			}
			this.clickOnConfigSettingFile(optDefFiles[options]);
			this.clickOnRawViewLink();
			// webPlex.validateHostsData(siteName);
			this.validateJSData(optionsData, "option.default.js");
			this.returnToMainFolder();
			webPlexPageObject.selectBreadcrumbLink(lastFolder).click();

		}
	}

	public void verifyReportingAdsDefaultsFile(int site) {
		boolean flag = false, isSubContext = false, isSubContext1 = false;
		String dup = "xx";
		String dup1 = "xx";
		StringBuilder builder = null;
		boolean flag1 = false;
		boolean flag2 = false;
		int noOfSubContext = 0;
		
		for (int options = 0; options < 1; options++) {

			
			
			for (int cnt = 0; cnt < repoAdsDefContexts.length; cnt++) {
				String txtContext = repoAdsDefContexts[cnt];
				String txtSubContext = repoAdsDefSubContexts[cnt];
				String txtWebplexKey = repoAdsDefWebplexkey[cnt];
				String isObj = repoAdsDefIsObj[cnt];
				
				if (cnt == 0) {
					optionsData.add("{ options: {");
				}

				if (!(dup1.equals(txtSubContext)) && !dup1.equals("")) {
					try {
						if (builder.length() != 0) {
							if (isSubContext1) {
								if(noOfSubContext!=0) {
									for(int cntSubContext=0; cntSubContext < noOfSubContext; cntSubContext++) {
										builder.append(" }");
									}
									noOfSubContext = 0;
								} else {
									builder.append(" }");
								}
							}
						}
						
					} catch (Exception e) {
						System.out.println(e);
					}
					flag2 = true;
					isSubContext1 = false;
					
				}
				
				
				if (!(dup.equals(txtContext))) {
					try {
						if (builder.length() != 0) {
							if (isSubContext) {
								builder.append(" }");
							}
							optionsData.add(builder.toString());
						}
					} catch (Exception e) {
						System.out.println(e);
					}
					flag = true;
					flag2 = false;
					builder = new StringBuilder();
				}

				for (int key2 = 1; key2 <= entityKeyList.size(); key2++) {
					entityFieldsDetailMap1 = spreadSheetData.get(Integer.toString(key2));
					dup = txtContext;
					dup1 = txtSubContext;
					
					if (entityFieldsDetailMap1.get(optionsDefaultHeaderNames.get(0)).equals(txtContext)
							&& entityFieldsDetailMap1.get(optionsDefaultHeaderNames.get(1)).equals(txtSubContext)
							&& entityFieldsDetailMap1.get(optionsDefaultHeaderNames.get(2)).equals(txtWebplexKey)) {
						String siteName = entityFieldsDetailMap1.get(sites[site]);
					
						if (!siteName.equals("")) {

							if (txtWebplexKey.equals("")) {
								if (siteName.contains("[")) {
									siteName = siteName.replace("[", "").replace("]", "");
									String[] values = siteName.split(",");
									StringBuilder build = new StringBuilder();
									build.append("[");
									for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
										if (cnt1 == values.length - 1) {
											if(values[cnt1].contains("'")) {
												build.append(values[cnt1].trim() );
											} else {
												build.append("'" + values[cnt1].trim() + "'" );
											}
										} else {
											if(values[cnt1].contains("'")) {
												build.append(values[cnt1].trim() + ", ");
											} else {
												build.append("'" + values[cnt1].trim() + "'" + ", ");
											}
											
										}
									}
									build.append("]");
									optionsData.add(txtContext + ": " + build);
								} else {
									optionsData.add(txtContext + ": '" + siteName + "'");
								}

							} else {
								if (isObj.equals("n")) {
									if (siteName.contains("[")) {
										siteName = siteName.replace("[", "").replace("]", "");
										String[] values = siteName.split(",");
										StringBuilder build = new StringBuilder();
										build.append("[");
										for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
											if (cnt1 == values.length - 1) {
												if(values[cnt1].contains("'")) {
													build.append(values[cnt1].trim());
												} else {
													build.append("'" + values[cnt1].trim() + "'");
												}
												
											} else {
												if(values[cnt1].contains("'")) {
													build.append(values[cnt1].trim()+ ", ");
												} else {
													build.append("'" + values[cnt1].trim() + "'" + ", ");
												}
												
											}

										}
										build.append("]");
										optionsData.add(txtWebplexKey + ": " + build);
									} else {
										optionsData.add(txtWebplexKey + ": '" + siteName + "'");
									}

								} else {
									if (flag) {
										if (!txtSubContext.equals("")) {
											// optionsData.add(txtContext +": {"
											// +txtSubContext+": {"
											// +txtWebplexKey+":
											// '"+siteName+"'}");
											flag = false;
											isSubContext = true;
											isSubContext1 = true;
											if (siteName.contains("[")) {
												siteName = siteName.replace("[", "").replace("]", "");
												String[] values = siteName.split(",");
												StringBuilder build = new StringBuilder();
												build.append("[");
												for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
													if (cnt1 == values.length - 1) {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1].trim());
														} else {
															build.append("'" + values[cnt1].trim() + "'");
														}
														
													} else {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1].trim() + ", ");
														} else {
															build.append("'" + values[cnt1].trim() + "'" + ", ");
														}
														
													}

												}
												build.append("]");

												builder.append(txtContext + ": { " + txtSubContext + ": { "
														+ txtWebplexKey + ": " + build + " }");
											} else {
												if(txtSubContext.contains("\\.")) {
													String[] subContexts =  txtSubContext.split(".");
													builder.append(txtContext + ": { ");
													for(int sub=0; sub < subContexts.length; sub++) {
														builder.append(txtSubContext + ": { ");
														noOfSubContext++;
													}
													builder.append(txtWebplexKey + ": '" + siteName + "'");
												} else {
													builder.append(txtContext + ": { " + txtSubContext + ": { "
															+ txtWebplexKey + ": '" + siteName + "'");
												}
											}

										} else {
											flag = false;
											isSubContext = true;
											//isSubContext1 = true;
											if (siteName.contains("[")) {
												siteName = siteName.replace("[", "").replace("]", "");
												String[] values = siteName.split(",");
												StringBuilder build = new StringBuilder();
												build.append("[");
												for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
													if (cnt1 == values.length - 1) {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1].trim());
														} else {
															build.append("'" + values[cnt1].trim() + "'");
														}
													} else {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1].trim() + ", ");
														} else {
															build.append("'" + values[cnt1].trim() + "'" + ", ");
														}
														
													}

												}
												build.append("]");
												builder.append(txtContext + ": { " + txtWebplexKey + ": " + build);
											} else {

												builder.append(
														txtContext + ": { " + txtWebplexKey + ": '" + siteName + "'");
												flag2 = true;
												isSubContext1 = false;
											}

										}

									} else {
										if (!txtSubContext.equals("")) {
											// optionsData.add(","+txtSubContext
											// +": {" +txtWebplexKey+":
											// '"+siteName+"'}" );
											flag1 = true;
											isSubContext1 = true;
											if(flag2) {
												/*builder.append(", " + txtSubContext + ": { " + txtWebplexKey + ": '"
														+ siteName + "'");*/
												
												
												
												if(txtSubContext.contains(".")) {
													String[] subContexts =  txtSubContext.split("\\.");
													builder.append(", ");
													for(int sub=0; sub < subContexts.length; sub++) {
														builder.append(subContexts[sub] + ": { ");
														noOfSubContext++;
													}
													builder.append(txtWebplexKey + ": '" + siteName + "'");
												} else {
													builder.append(", " + txtSubContext + ": { " + txtWebplexKey + ": '"
															+ siteName + "'");
												}
												
												flag2 = false;
											} else {
												builder.append(", "+ txtWebplexKey + ": '" + siteName + "'");
											}
											
											
											/*	if (!pretxtContext.equals(txtContext)) {
													pretxtContext = txtContext;
													flag1 =true;
												} else {
													builder.append(", "+ txtWebplexKey + ": '" + siteName + "' }");
												}
											*/
												/*if(flag1) {
													builder.append(", " + txtSubContext + ": { " + txtWebplexKey + ": '"
															+ siteName + "' }");
												} else {
													builder.append(", " + txtSubContext + ": { " + txtWebplexKey + ": '"
															+ siteName + "' }");
												}*/
												
											
											
										} else {
											if (siteName.contains("[")) {
												siteName = siteName.replace("[", "").replace("]", "");
												String[] values = siteName.split(",");
												StringBuilder build = new StringBuilder();
												build.append("[");
												for (int cnt1 = 0; cnt1 < values.length; cnt1++) {
													if (cnt1 == values.length - 1) {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1]);
														} else {
															build.append("'" + values[cnt1] + "'");
														}
														
													} else {
														if(values[cnt1].contains("'")) {
															build.append(values[cnt1] + ",");
														} else {
															build.append("'" + values[cnt1] + "'" + ",");
														}
													}

												}
												build.append("]");
												builder.append(", " + txtWebplexKey + ": " + build);
											} else {

												builder.append(", " + txtWebplexKey + ": '" + siteName + "'");
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
	
				if (cnt == repoAdsDefContexts.length - 1) {
					if (isSubContext && builder.length() != 0) {
						builder.append(" }");
						optionsData.add(builder.toString());
					}

					optionsData.add("} }");
				}

			}
			this.clickOnConfigSettingFile(repoAdsDefFiles[options]);
			this.clickOnRawViewLink();
			// webPlex.validateHostsData(siteName);
			this.validateJSData(optionsData, "reporting-ads.default.js");
			this.returnToMainFolder();
			webPlexPageObject.selectBreadcrumbLink(lastFolder).click();

		}
	}
	
	public void verifyHostsJSFile(int site) {
		for (int cnt = 0; cnt < contexts.length; cnt++) {

			String txtContext = contexts[cnt];
			String txtSubContext = subContexts[cnt];
			String txtWebplexKey = webplexKey[cnt];

			for (int key2 = 1; key2 <= entityKeyList.size(); key2++) {
				entityFieldsDetailMap1 = spreadSheetData.get(Integer.toString(key2));
				ArrayList<String> data = new ArrayList<>();

				if (entityFieldsDetailMap1.get(hostsHeaderNames.get(0)).equals(txtContext)
						&& entityFieldsDetailMap1.get(hostsHeaderNames.get(1)).equals(txtSubContext)) {
					String siteName = entityFieldsDetailMap1.get(sites[site]);
					//String jsData = "{hosts: ";// +txtWebplexKey+":
												// '"+siteName+"'}}";
					data.add("{ hosts: {");
					data.add(txtWebplexKey + ": '" + siteName + "'");
					data.add("} }");
					this.clickOnConfigSettingFile(files[cnt]);
					this.clickOnRawViewLink();
					// webPlex.validateHostsData(siteName);
					this.validateJSData(data, files[cnt]);
					this.returnToMainFolder();
					webPlexPageObject.selectBreadcrumbLink(lastFolder).click();
					break;

				}

			}
		}
	}

}
