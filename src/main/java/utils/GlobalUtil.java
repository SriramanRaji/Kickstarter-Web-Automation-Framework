package utils;

import java.util.HashMap;

/**
 * <i><b>GlobalUtil</b> is a class created and maintained for Global
 * Variables</i>
 * 
 * @author Sriraman Raji
 * @since 22-December-2018
 */

public class GlobalUtil {

	static String projectFolder = System.getProperty("user.dir");

	static String configurationFilePath = "/src/test/resource/properties/configuration.properties";
	static String objectRepositoryFilePath = "/src/test/resource/properties/objectRepository.properties";

	static String testDataFolder = "/src/test/resource/test-data/";
	static String reportFolder = "/target/reports/";
	static String configFolder = "/src/main/resource/config/";
	static String driverFolder = "/src/main/resource/driver/";

	static String chromeDriverPath = projectFolder + driverFolder + "chromedriver.exe";
	static String firefoxDriverPath = projectFolder + driverFolder + "geckodriver.exe";
	static String edgeDriverPath = projectFolder + driverFolder + "msedgedriver.exe";
	
	public static HashMap<String, String> testDataHash = new HashMap<String, String>();

	public static String testcaseName;

	public static void globalSetup(String testcaseName) throws Exception {

		GlobalUtil.testcaseName = testcaseName;
		ExcelUtil.loadData(testcaseName);

	}

}
