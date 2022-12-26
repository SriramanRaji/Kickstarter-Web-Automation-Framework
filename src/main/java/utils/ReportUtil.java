package utils;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * <i><b>ReportUtil</b> is a class created and maintained for reporting purpose
 * for each test run with Extent Report</i>
 * 
 * @author Sriraman Raji
 * @since 22-December-2018
 */

public class ReportUtil {

	static String reportFolder;
	static String reportFile;
	static ExtentTest test;
	static ExtentReports report;

	/**
	 * <i><b>createFolder</b> is a method create a folder with current date / test
	 * run date for the first run of the day</i>
	 * 
	 * @param none
	 * @return none
	 * @exception none
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */

	public static void createFolder() {

		reportFolder = GlobalUtil.projectFolder + GlobalUtil.reportFolder + DateUtil.getCurrentDateAsString()+"/";
		File folder = new File(reportFolder);
		if (!folder.exists()) {
			if (folder.mkdir()) {
				System.out.println("Directory Created: " + reportFolder);
			}
		} else {
			System.out.println("Directory Exists: " + reportFolder);
		}

	}
	
	/**
	 * <i><b>startReporting</b> is a method to initialize the HTML Report file generation</i>
	 * 
	 * @param testCaseName
	 * @return none
	 * @exception none
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */
	public static void startReporting(String testCaseName) {
		createFolder();
		reportFile = reportFolder+testCaseName + "_" + DateUtil.getCurrentDateAndTimeAsString() + ".html";
//		reportFile = reportFolder+DateUtil.getCurrentDateAsString() + ".html";
		System.out.println("Report File : "+ reportFile);
		report = new ExtentReports(reportFile, false);
		report.loadConfig(new File(GlobalUtil.projectFolder+GlobalUtil.configFolder+"extentReportConfig.xml"));
		report.addSystemInfo("Author", "Sriraman Raji");
		report.addSystemInfo("Selenium Version", "3.141.59");
		test = report.startTest(testCaseName);
	}
	
	/**
	 * <i><b>endReporting</b> is a method to flush out data and generates the HTML Report file</i>
	 * 
	 * @param none
	 * @return none
	 * @exception none
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */
	public static void endReporting() {
		report.endTest(test);
		report.flush();
		report.close();
	}
	
	/**
	 * <i><b>captureScreenshot</b> is a method to capture screenshot of the browser and convert it into Base64 format and append on the step</i>
	 * 
	 * @param driver
	 * @return base64ImageString
	 * @exception none
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */
	public static String captureScreenshot(WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot)driver;
		String base64ImageString = ts.getScreenshotAs(OutputType.BASE64);
		return "data:img/png;base64,"+base64ImageString;
	}
	
	
	/**
	 * <i><b>reportEvent</b> is a method to report an Event which will be added to the HTML Report</i>
	 * 
	 * @param status
	 * @param description
	 * @param captureScreenshot
	 * @return none
	 * @exception none
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */
	public static void reportEvent(LogStatus status, String description, boolean captureScreenshot) {
		if(captureScreenshot) {
			test.log(status, test.addScreenCapture(captureScreenshot(WebUtil.driver))+description);
		} else {
			test.log(status, description);
		}
	}

}
