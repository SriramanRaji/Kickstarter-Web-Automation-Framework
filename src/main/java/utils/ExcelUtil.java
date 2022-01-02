package utils;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <i><b>ExcelUtil</b> is a class created and maintained for Test Data - Excel
 * interaction methods in the Framework</i>
 * 
 * @author Sriraman Raji
 * @since 24-December-2018
 */

public class ExcelUtil {

	static File file;
	static FileInputStream fis;
	static String excelDataFile;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static int rowCount, columnCount, testCaseRow;
	static String cellValue;

	/**
	 * <i><b>connectToFile</b> is a method to initialize connection with the Excel
	 * Test data file</i>
	 * 
	 * @param none
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */

	public static void connectToFile() {

		try {
			String fileName = PropertyUtil.getConfig("dataFile");
			String sheetName = PropertyUtil.getConfig("dataSheet");
			file = new File(GlobalUtil.projectFolder + GlobalUtil.testDataFolder + fileName);
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);

		} catch (Exception e) {
			System.out.println("Test Data sheet not Found.!!");
		}

	}

	/**
	 * <i><b>disconnectFromFile</b> is a method to close connection with the Excel
	 * Test data file</i>
	 * 
	 * @param none
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */
	public static void disconnectFromFile() throws Exception {
		workbook.close();
		fis.close();
	}

	/**
	 * <i><b>getExcelData</b> is a method to retrieve data from Test data file</i>
	 * 
	 * @param testCaseName
	 * @param fieldName
	 * @return cellValue
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */
	public static String getData(String testCaseName, String fieldName) throws Exception {

		connectToFile();

		int actualRow = 0;
		int actualColumn = 0;

		rowCount = sheet.getPhysicalNumberOfRows();

		for (int row = 0; row < rowCount; row++) {
			if (sheet.getRow(row).getCell(0).getStringCellValue().equalsIgnoreCase(testCaseName)) {
				actualRow = row;
				break;
			}
		}

		int columnCount = sheet.getRow(actualRow).getPhysicalNumberOfCells();
		for (int col = 0; col < columnCount; col++) {
			if (sheet.getRow(0).getCell(col).getStringCellValue().equalsIgnoreCase(fieldName)) {
				actualColumn = col;
				break;
			}
		}

		if (sheet.getRow(actualRow).getCell(actualColumn) != null) {

			if (sheet.getRow(actualRow).getCell(actualColumn).getCellType().toString().equalsIgnoreCase("STRING")) {
				cellValue = sheet.getRow(actualRow).getCell(actualColumn).getStringCellValue().toString();
			} else if (sheet.getRow(actualRow).getCell(actualColumn).getCellType().toString()
					.equalsIgnoreCase("NUMERIC")
					|| sheet.getRow(actualRow).getCell(actualColumn).getCellType().toString()
							.equalsIgnoreCase("FORMULA")) {
				cellValue = String.valueOf(sheet.getRow(actualRow).getCell(actualColumn).getNumericCellValue());
			} else if (sheet.getRow(actualRow).getCell(actualColumn).getCellType().toString()
					.equalsIgnoreCase("BOOLEAN")) {
				cellValue = String.valueOf(sheet.getRow(actualRow).getCell(actualColumn).getBooleanCellValue());
			} else if (sheet.getRow(actualRow).getCell(actualColumn).getCellType().toString()
					.equalsIgnoreCase("DATE")) {
				cellValue = String.valueOf(sheet.getRow(actualRow).getCell(actualColumn).getDateCellValue());
			} else if (sheet.getRow(actualRow).getCell(actualColumn).getCellType().toString()
					.equalsIgnoreCase("BLANK")) {
				cellValue = "N/A".toString();
			}

		} else {
			cellValue = "N/A".toString();
		}

		disconnectFromFile();

		return cellValue;

	}

	/**
	 * <i><b>loadData</b> is a method to retrieve data from Test data file for a
	 * specific testcase and store it in a Global HashMap in a key - value |
	 * fieldName - fieldValue for the required testcase</i>
	 * 
	 * @param testCaseName
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */
	public static void loadData(String testCaseName) throws Exception {
		connectToFile();

		GlobalUtil.testDataHash.clear();

		int rowCount = sheet.getPhysicalNumberOfRows();
		int columnCount = sheet.getRow(1).getPhysicalNumberOfCells();
		int actualRow = 0;

		for (int row = 0; row < rowCount; row++) {
			if (sheet.getRow(row).getCell(0).getStringCellValue().equalsIgnoreCase(testCaseName)) {
				actualRow = row;
				break;
			}
		}

		for (int col = 0; col < columnCount; col++) {
			GlobalUtil.testDataHash.put(sheet.getRow(0).getCell(col).getStringCellValue(),
					sheet.getRow(actualRow).getCell(col).getStringCellValue());
		}

		disconnectFromFile();
	}

}