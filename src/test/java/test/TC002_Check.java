package test;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import apps.HomePage;
import utils.GlobalUtil;
import utils.ReportUtil;
import utils.WebUtil;

public class TC002_Check {

	String className = this.getClass().getSimpleName();

	@Test
	public void TC002_Check_Test() throws Exception {

		GlobalUtil.globalSetup(className);
		String execute = GlobalUtil.testDataHash.get("Execute");

		if (execute.equalsIgnoreCase("yes") || execute.equalsIgnoreCase("y")) {
			try {
				ReportUtil.startReporting(GlobalUtil.testcaseName);
				HomePage.loginApplication();
			} catch (Exception e) {
				ReportUtil.reportEvent(LogStatus.FAIL, GlobalUtil.testcaseName + " execution failed", true);
			} finally {
				WebUtil.closeBrowser();
				ReportUtil.endReporting();
			}

		} else {
			System.out.println(className + " is not selected to Execute");
		}

	}

}
