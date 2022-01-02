package utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * <i><b>PropertyUtil</b> is a class created and maintained for Property file
 * related methods in the Framework</i>
 * 
 * @author Sriraman Raji
 * @since 23-December-2018
 */

public class PropertyUtil {

	/**
	 * <i><b>connectToFile</b> is a method loads a property file and returns a
	 * property object</i>
	 * 
	 * @param propertyFilePath
	 * @return property
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 23-December-2018
	 */

	public static Properties connectToFile(String propertyFilePath) throws Exception {
		String absoluteFilePath = GlobalUtil.projectFolder + propertyFilePath;
		FileInputStream file = new FileInputStream(absoluteFilePath);
		Properties property = new Properties();
		property.load(file);
		return property;
	}

	/**
	 * <i><b>getConfig</b> is a method to retrieve configuration value</i>
	 * 
	 * @param key
	 * @return value
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 23-December-2018
	 */

	public static String getConfig(String key) throws Exception {
		Properties configProperty = connectToFile(GlobalUtil.configurationFilePath);
		String value = configProperty.getProperty(key, "Configuration Not Found");
		return value;
	}

	/**
	 * <i><b>getObject</b> is a method to retrieve object value | i.e. XPath, id,
	 * name, tagName or className </i>
	 * 
	 * @param key
	 * @return value
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 23-December-2018
	 */
	public static String getObject(String key) throws Exception {
		Properties orProperty = connectToFile(GlobalUtil.objectRepositoryFilePath);
		String value = orProperty.getProperty(key, "Object Not Found");
		return value;
	}

}
