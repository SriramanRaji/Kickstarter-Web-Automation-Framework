//package utils;
//
//import java.util.regex.Pattern;
//
//import org.sikuli.script.Screen;
//
//import com.relevantcodes.extentreports.LogStatus;
//
///**
// * <i><b>ImageUtil</b> is a class created and maintained for methods to interact
// * with web using image references</i>
// * 
// * @author Sriraman Raji
// * @since 24-December-2018
// */
//
//public class ImageUtil {
//
//	/**
//	 * <i><b>imageClick</b> is a method to click an webelement or windows element
//	 * with an image reference saved as png</i>
//	 * 
//	 * @param imageFilename
//	 * @return none
//	 * @exception Exception
//	 * @author Sriraman Raji
//	 * @since 24-December-2018
//	 */
//	public static void imageClick(String imageFilename) {
//		try {
//			String filePath = GlobalUtil.projectFolder+GlobalUtil.imageFolder+imageFilename+".png";
//			Screen screen = new Screen();
//			screen.click(new Pattern(filePath));
//			ReportUtil.reportEvent(LogStatus.PASS, "Clicked "+imageFilename+" image", false);
//		} catch (Exception e) {
//			ReportUtil.reportEvent(LogStatus.FAIL, "Couldn't click "+imageFilename+" image", true);
//		}
//	}
//	
//	/**
//	 * <i><b>imageSetText</b> is a method to enter text in an webelement or windows element
//	 * with an image reference saved as png</i>
//	 * 
//	 * @param imageFilename
//	 * @param text
//	 * @return none
//	 * @exception Exception
//	 * @author Sriraman Raji
//	 * @since 24-December-2018
//	 */
//	public static void imageSetText(String imageFilename, String text) {
//		try {
//			String filePath = GlobalUtil.projectFolder+GlobalUtil.imageFolder+imageFilename+".png";
//			Screen screen = new Screen();
//			screen.type(new Pattern(filePath), text);
//			ReportUtil.reportEvent(LogStatus.PASS, "Entered "+text+" in "+imageFilename+" image", false);
//		} catch (Exception e) {
//			ReportUtil.reportEvent(LogStatus.FAIL, "Couldn't enter "+text+" in "+imageFilename+" image", true);
//		}
//	}
//
//}
