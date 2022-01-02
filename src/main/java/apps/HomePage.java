package apps;

import com.relevantcodes.extentreports.LogStatus;

import utils.GlobalUtil;
import utils.PropertyUtil;
import utils.ReportUtil;
import utils.WebUtil;

/**
 * <i><b>HomePage</b> is a class created and maintained for methods related to
 * Application Homepage</i>
 * 
 * @author Sriraman Raji
 * @since 24-December-2018
 */

public class HomePage {

	/**
	 * <i><b>loginApplication</b> is a method to login the application on active
	 * browser</i>
	 * 
	 * @param none
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void loginApplication() {

		try {
			WebUtil.loadURL(PropertyUtil.getConfig("url"));
			WebUtil.verifyPageTitle("Your Store");
			WebUtil.clickElement("lnkMyAccount");
			WebUtil.clickElement("lnkLogin");
			WebUtil.setText("txtUsername", GlobalUtil.testDataHash.get("Username"));
			WebUtil.setText("txtPassword", GlobalUtil.testDataHash.get("Password"));
			WebUtil.clickElement("btnLogin");
			WebUtil.verifyPageTitle("My Account");
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Login failed", true);
		}

	}

}