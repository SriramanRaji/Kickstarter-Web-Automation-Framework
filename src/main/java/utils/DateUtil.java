package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <i><b>DateUtil</b> is a class created and maintained for date related methods
 * in the Framework</i>
 * 
 * @author Sriraman Raji
 * @since 22-December-2018
 */

public class DateUtil {

	/**
	 * <i><b>dateFormatter</b> is a method formats and returns the given date in a
	 * provided format</i>
	 * 
	 * @param givenDate
	 * @param formatRequired - as per java date format code
	 * @return formattedDate
	 * @exception none
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */

	public static String dateFormatter(Date givenDate, String formatRequired) {
		DateFormat format = new SimpleDateFormat(formatRequired);
		String formattedDate = format.format(givenDate);
		return formattedDate;
	}

	/**
	 * <i><b>getCurrentDateAsString</b> is a method that returns current date as
	 * string in a pre-determined format dd-MM-YYYY <br>
	 * <b>e.g. 22-12-2018</b></i>
	 * 
	 * @param none
	 * @return currentDateAsString
	 * @exception none
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */

	public static String getCurrentDateAsString() {
		Date currentDate = new Date();
		String currentDateAsString = dateFormatter(currentDate, "dd-MM-YYYY");
		return currentDateAsString;
	}

	/**
	 * <i><b>getCurrentDateAndTimeAsString</b> is a method that returns current date
	 * and time as string in a pre-determined format dd_MM_YYYY hh_mm_ss<br><br>
	 * <b>e.g:</b> 22_12_2018 08_55_55 am</i>
	 * 
	 * @param none
	 * @return currentDateAndTimeAsString
	 * @exception none
	 * @author Sriraman Raji
	 * @since 22-December-2018
	 */

	public static String getCurrentDateAndTimeAsString() {

		Date currentDate = new Date();
		String currentDateAsString = dateFormatter(currentDate, "dd_MM_YYYY hh_mm_ss");
		return currentDateAsString;

	}

}
