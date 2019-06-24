package com.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;

public class FileHandlingOperationUtility {
	static String contex = null;
	static String subContex = null;

	/**
     * Get excel data and store in Linked Hash map.
     * @return Array of Linked has map
     * @throws 
     */
	public static Map<String, LinkedHashMap<String, String>> getEntityDetailsFromMap(List<List<Object>> dataList) {
		ArrayList<String> entityFieldNameKeyList = new ArrayList<String>();
		ArrayList<String> entityFieldValueList;
		List<Object> row1 = null;
		LinkedHashMap<String, String> entityFieldsDetailMap;
		String preContext = "xx";
		boolean flag= true;
		Map<String, LinkedHashMap<String, String>> dataTreeMap = new TreeMap<String, LinkedHashMap<String, String>>();
		if (dataList == null || dataList.size() == 0) {
			System.out.println("No data found.");
		} else {
			for (int cnt = 0; cnt < dataList.size(); cnt++) {
				entityFieldValueList = new ArrayList<String>();
				entityFieldsDetailMap = new LinkedHashMap<String, String>();
				if (cnt == 0) {
					row1 = dataList.get(cnt);
					for (int rowheader = 0; rowheader < row1.size(); rowheader++) {
						entityFieldNameKeyList.add(row1.get(rowheader).toString());
					}
					continue;
				}

				row1 = dataList.get(cnt);
				for (int col = 0; col < row1.size(); col++) {
					//System.out.println(col + " --" + row1.get(col));
					entityFieldValueList.add(row1.get(col).toString());
				}

				if (row1.size() < entityFieldNameKeyList.size()) {
					for (int cntt = row1.size(); cntt < entityFieldNameKeyList.size(); cntt++) {
						entityFieldValueList.add("");
					}
				}
			
				for (int arrayIndex = 0; arrayIndex < entityFieldNameKeyList.size(); arrayIndex++) {
					if (entityFieldNameKeyList.get(arrayIndex).equals("Context")) {
						if (!entityFieldValueList.get(arrayIndex).isEmpty()) {
							contex = entityFieldValueList.get(arrayIndex);
							entityFieldsDetailMap.put(entityFieldNameKeyList.get(arrayIndex),
									entityFieldValueList.get(arrayIndex));
							
							if(!preContext.equalsIgnoreCase(contex)) {
								preContext = contex;
								flag = false;
							} 
							
						} else {
							entityFieldsDetailMap.put(entityFieldNameKeyList.get(arrayIndex), contex);
						
						}
					} else if (entityFieldNameKeyList.get(arrayIndex).equals("Subcontext")) {
						
						if (!entityFieldValueList.get(arrayIndex).isEmpty()) {
							subContex = entityFieldValueList.get(arrayIndex);
							entityFieldsDetailMap.put(entityFieldNameKeyList.get(arrayIndex),
									entityFieldValueList.get(arrayIndex));
							flag = true;
						} else {
							if(flag) {
								entityFieldsDetailMap.put(entityFieldNameKeyList.get(arrayIndex), subContex);
							} else {
								entityFieldsDetailMap.put(entityFieldNameKeyList.get(arrayIndex), "");
							}
							
						}
					} else {
						//System.out.println(entityFieldValueList.get(arrayIndex));
						entityFieldsDetailMap.put(entityFieldNameKeyList.get(arrayIndex),
								entityFieldValueList.get(arrayIndex));
					}
					
					
					
				}
				dataTreeMap.put(Integer.toString(cnt), entityFieldsDetailMap);
			}
		}
		return dataTreeMap;
	}

	public static Map<String, ArrayList<String>> readFromExcelFile(String filePath, String tabName) {
		Map<String, ArrayList<String>> dataTreeMap = new TreeMap<String, ArrayList<String>>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("d-MMMM-yyyy");
		try {
			FileInputStream file = new FileInputStream(new File(filePath));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get desired name of tab in Excel sheet from the workbook
			XSSFSheet sheet = workbook.getSheet(tabName);

			// Formula evaluator - will evaluate formula in each cell.
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			int keyIndex = 0;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				ArrayList<String> campaignFieldsList = new ArrayList<String>();
				int campaignFieldsIndex = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_FORMULA:
						evaluator.evaluateFormulaCell(cell);
					case Cell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							campaignFieldsList.add(dateFormatter.format(cell.getDateCellValue()));
							// System.out.println(dateFormatter.format(cell.getDateCellValue()));
						} else {
							long cellValue = (long) (cell.getNumericCellValue());
							campaignFieldsList.add(campaignFieldsIndex, Long.toString(cellValue));
						}
						break;
					case Cell.CELL_TYPE_STRING:
						campaignFieldsList.add(campaignFieldsIndex, cell.getStringCellValue().trim());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						String cellValue = String.valueOf(cell.getBooleanCellValue());
						campaignFieldsList.add(campaignFieldsIndex, cellValue);
						break;
					case Cell.CELL_TYPE_BLANK:
						cell.setCellValue("");
						campaignFieldsList.add(campaignFieldsIndex, cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_ERROR:
						campaignFieldsList.add(campaignFieldsIndex, cell.getStringCellValue());
						break;
					default:
						campaignFieldsList.add(campaignFieldsIndex, cell.getStringCellValue());
						break;
					}
					campaignFieldsIndex++;
				}
				dataTreeMap.put(Integer.toString(keyIndex), campaignFieldsList);
				keyIndex++;
			}
			file.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return dataTreeMap;
	}

	/**
     * Read data from CSV file.
     * @return Tree map array
     * @throws 
     */
	public static Map<String, List<String>> readFromCSVFile(String filePath, String tabName) {
		Map<String, List<String>> dataTreeMap = new TreeMap<String, List<String>>();
		List<List<String>> records = new ArrayList<List<String>>();
		try (CSVReader csvReader = new CSVReader(new FileReader(filePath));) {
			String[] values = null;
			int campaignFieldsIndex = 0;
			while ((values = csvReader.readNext()) != null) {
				records.add(Arrays.asList(values));
				dataTreeMap.put(Integer.toString(campaignFieldsIndex), Arrays.asList(values));
				campaignFieldsIndex++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataTreeMap;
	}

	/**
     * This function map header with row data
     * @return Map
     * @throws 
     */
	public static Map<String, String> getEntityDetailsFromMap(String campaignKeyNumber,
			Map<String, List<String>> dataTreeMap) {
		Map<String, String> entityFieldsDetailMap = new HashMap<String, String>();
		List<String> entityFieldNameKeyList = dataTreeMap.get("0");
		List<String> entityFieldValueList = dataTreeMap.get(campaignKeyNumber);
		for (int arrayIndex = 0; arrayIndex < entityFieldNameKeyList.size(); arrayIndex++) {
			entityFieldsDetailMap.put(entityFieldNameKeyList.get(arrayIndex), entityFieldValueList.get(arrayIndex));
		}
		return entityFieldsDetailMap;
	}
	
	
	
	public static ArrayList<String> getHeaderNames(String filePath) {
		int index = -1, row = 0;
		 File file = new File(filePath);
         BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         ArrayList<String> headerList = new ArrayList<String>();
         file = new File(filePath);
         try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         String line = null;
			try {
				line = bufRdr.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         row++;
         StringTokenizer st = new StringTokenizer(line, ",");
         while (st.hasMoreTokens())
         {
             headerList.add(st.nextToken());
         }
         return headerList;
	}
	    private static String[] getDataValues(String filePath, String header)
	        
	    {
	        try
	        {
	            int index = -1, row = 0, col = 0;
	            ArrayList<String> values = new ArrayList<String>();
	            File file = new File(filePath);
	            BufferedReader bufRdr = new BufferedReader(new FileReader(file));
	            ArrayList<String> headerList = new ArrayList<String>();
	            file = new File(filePath);
	            
	            String line = null;
				try {
					line = bufRdr.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            row++;
	            StringTokenizer st = new StringTokenizer(line, ",");
	            while (st.hasMoreTokens())
	            {
	                headerList.add(st.nextToken());
	            }
	            for (int i = 0; i < headerList.size(); i++)
	            {
	                if (headerList.get(i).equals(header))
	                {
	                    index = i;
	                    break;
	                }
	            }
	            try {
					while ((line = bufRdr.readLine()) != null)
					{
					    if (row > 0)
					    {
					        st = new StringTokenizer(line, ",");
					        String[] result = line.split(",", -1);
					       /* while (st.hasMoreTokens())
					        {// get next token and store it in the array.
					            String val = st.nextElement().toString();
					            if (col == index) values.add(val);
					            col++;
					        }*/
					        
					        for(int res=0; res < result.length; res++) {
					        	String val = result[res];
					            if (col == index) values.add(val);
					            col++;
					        }
					        
					    }
					    col = 0;
					    row++;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return values.toArray(new String[values.size()]);
	        }
	        catch (FileNotFoundException e)
	        {
	            
	        }
			return null;
	       
	    }

	    public static String[] getKeyValues(String filePath, String header)
	       
	    {
	        return getDataValues(filePath, header);
	    }
	
}
