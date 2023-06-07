"//package com.realnet.excel.service;" + "\r\n" + 
"//" + "\r\n" + 
"//import java.io.IOException;" + "\r\n" + 
"//import java.util.ArrayList;" + "\r\n" + 
"//import java.util.List;" + "\r\n" + 
"//" + "\r\n" + 
"//import org.apache.poi.EncryptedDocumentException;" + "\r\n" + 
"//import org.apache.poi.ss.usermodel.Cell;" + "\r\n" + 
"//import org.apache.poi.ss.usermodel.DataFormatter;" + "\r\n" + 
"//import org.apache.poi.ss.usermodel.Row;" + "\r\n" + 
"//import org.apache.poi.ss.usermodel.Sheet;" + "\r\n" + 
"//import org.apache.poi.ss.usermodel.Workbook;" + "\r\n" + 
"//import org.apache.poi.ss.usermodel.WorkbookFactory;" + "\r\n" + 
"//import org.springframework.stereotype.Service;" + "\r\n" + 
"//" + "\r\n" + 
"//import com.realnet.excel.entity.ExcelFileUpload;" + "\r\n" + 
"//" + "\r\n" + 
"//@Service" + "\r\n" + 
"//public class ExcelDataService {" + "\r\n" + 
"// " + "\r\n" + 
"//	Workbook workbook;" + "\r\n" + 
"//" + "\r\n" + 
"//	public List<ExcelFileUpload> getExcelDataAsList() {" + "\r\n" + 
"//" + "\r\n" + 
"//		List<String> list = new ArrayList<String>();" + "\r\n" + 
"//" + "\r\n" + 
"//		// Create a DataFormatter to format and get each cell's value as String" + "\r\n" + 
"//		DataFormatter dataFormatter = new DataFormatter();" + "\r\n" + 
"//" + "\r\n" + 
"//		// Create the Workbook" + "\r\n" + 
"////		try {" + "\r\n" + 
"////			workbook = WorkbookFactory.create();" + "\r\n" + 
"////		} catch (EncryptedDocumentException | IOException e) {" + "\r\n" + 
"////			e.printStackTrace();" + "\r\n" + 
"////		}" + "\r\n" + 
"//" + "\r\n" + 
"//		// Retrieving the number of sheets in the Workbook" + "\r\n" + 
"//		System.out.println(\"-------Workbook has '\" + workbook.getNumberOfSheets() + \"' Sheets-----\");" + "\r\n" + 
"//" + "\r\n" + 
"//		// Getting the Sheet at index zero" + "\r\n" + 
"//		Sheet sheet = workbook.getSheetAt(0);" + "\r\n" + 
"//" + "\r\n" + 
"//		// Getting number of columns in the Sheet" + "\r\n" + 
"//		int noOfColumns = sheet.getRow(0).getLastCellNum();" + "\r\n" + 
"//		System.out.println(\"-------Sheet has '\"+noOfColumns+\"' columns------\");" + "\r\n" + 
"//" + "\r\n" + 
"//		// Using for-each loop to iterate over the rows and columns" + "\r\n" + 
"//		for (Row row : sheet) {" + "\r\n" + 
"//			for (Cell cell : row) {" + "\r\n" + 
"//				String cellValue = dataFormatter.formatCellValue(cell);" + "\r\n" + 
"//				list.add(cellValue);" + "\r\n" + 
"//			}" + "\r\n" + 
"//		}" + "\r\n" + 
"//" + "\r\n" + 
"//		// filling excel data and creating list as List<Invoice>" + "\r\n" + 
"//		List<ExcelFileUpload> invList = createList(list, noOfColumns);" + "\r\n" + 
"//" + "\r\n" + 
"//		// Closing the workbook" + "\r\n" + 
"//		try {" + "\r\n" + 
"//			workbook.close();" + "\r\n" + 
"//		} catch (IOException e) {" + "\r\n" + 
"//			// TODO Auto-generated catch block" + "\r\n" + 
"//			e.printStackTrace();" + "\r\n" + 
"//		}" + "\r\n" + 
"//" + "\r\n" + 
"//		return invList;" + "\r\n" + 
"//	}" + "\r\n" + 
"//" + "\r\n" + 
"//	private List<ExcelFileUpload> createList(List<String> excelData, int noOfColumns) {" + "\r\n" + 
"//" + "\r\n" + 
"//		ArrayList<ExcelFileUpload> invList = new ArrayList<ExcelFileUpload>();" + "\r\n" + 
"//" + "\r\n" + 
"//		int i = noOfColumns;" + "\r\n" + 
"//		do {" + "\r\n" + 
"//			ExcelFileUpload inv = new ExcelFileUpload();" + "\r\n" + 
"//" + "\r\n" + 
"////			inv.setName(excelData.get(i));" + "\r\n" + 
"////			inv.setAmount(Double.valueOf(excelData.get(i + 1)));" + "\r\n" + 
"////			inv.setNumber(excelData.get(i + 2));" + "\r\n" + 
"////			inv.setReceivedDate(excelData.get(i + 3));" + "\r\n" + 
"//" + "\r\n" + 
"//			invList.add(inv);" + "\r\n" + 
"//			i = i + (noOfColumns);" + "\r\n" + 
"//" + "\r\n" + 
"//		} while (i < excelData.size());" + "\r\n" + 
"//		return invList;" + "\r\n" + 
"//	}" + "\r\n" + 
"//" + "\r\n" + 
"////	public int saveExcelData(List<ExcelFileUpload> invoices) {" + "\r\n" + 
"////		invoices = repo.saveAll(invoices);" + "\r\n" + 
"////		return invoices.size();" + "\r\n" + 
"////	}" + "\r\n" + 
"//	" + "\r\n" + 
"	" + "\r\n" + 
"	" + "\r\n" + 
"//}" 