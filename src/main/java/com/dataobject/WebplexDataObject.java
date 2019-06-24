package com.dataobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.DataProvider;

import com.utility.FileHandlingOperationUtility;

public class WebplexDataObject {

	/**
	   * Data provider for Agency
	   * 
	   * @return
	   */
	  @DataProvider(name = "getInputData")
	  public static Object[][] getAdvertiserDetailsForAgencyUser() {
	    String strTabName = "InputDataSheet";
	    Object[][] detailsObjects = getInputDataDetailsDataInObjects(strTabName);
	    return detailsObjects;
	  }
	  
	  public static Object[][] getInputDataDetailsDataInObjects(String strTabName) {
		    String strFilePath = System.getProperty("user.dir") + "/src/main/resources/InputDataSheet.csv";
		        
		    Map<String, List<String>> dataTreeMap =
		        FileHandlingOperationUtility.readFromCSVFile(strFilePath, strTabName);
		    Set<String> entityKeyList = dataTreeMap.keySet();
		    Object[][] dataEntityObject = new Object[entityKeyList.size() - 1][1];
		    List<WebplexFieldNames> objectsList = new ArrayList<WebplexFieldNames>();
		    int keyIndex = 0;
		    for (int key = 1; key < entityKeyList.size(); key++) {
		      Map<String, String> entityFieldsDetailMap =
		    		  FileHandlingOperationUtility.getEntityDetailsFromMap(Integer.toString(key), dataTreeMap);
		      WebplexFieldNames webplexFieldNames = new WebplexFieldNames();
		      webplexFieldNames.setBranchName(entityFieldsDetailMap
		          .get(WebplexFieldNames.branchNameConstant));
		      webplexFieldNames.setFolderName(entityFieldsDetailMap
		          .get(WebplexFieldNames.folderNameConstant));
		      webplexFieldNames.setSiteName(entityFieldsDetailMap
			          .get(WebplexFieldNames.siteConstant));
		      webplexFieldNames.setContext(entityFieldsDetailMap
			          .get(WebplexFieldNames.contextConstant));
		      webplexFieldNames.setSubcontext(entityFieldsDetailMap
			          .get(WebplexFieldNames.subcontextConstant));
		      webplexFieldNames.setFile(entityFieldsDetailMap
			          .get(WebplexFieldNames.fileConstant));
		      objectsList.add(webplexFieldNames);
		      dataEntityObject[keyIndex][0] = webplexFieldNames;
		      keyIndex++;
		    }
		    return dataEntityObject;
		  }
}
