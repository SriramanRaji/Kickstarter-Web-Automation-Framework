package utils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

/**
 * <i><b>WebUtil</b> is a class created and maintained for methods to interact
 * with web browsers</i>
 * 
 * @author Sriraman Raji
 * @since 24-December-2018
 */

public class WebUtil {

	static WebDriver driver;
	static WebDriverWait wait;
	static JavascriptExecutor js;
	static String browserType;

	/**
	 * <i><b>initializeBrowser</b> is a method to invoke browser with the help of
	 * browser driver</i>
	 * 
	 * @param none
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void initializeBrowser() {
		try {
			browserType = PropertyUtil.getConfig("browserType");
			if (browserType.trim().equalsIgnoreCase("chrome")) {
//				Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
				System.setProperty("webdriver.chrome.driver", GlobalUtil.chromeDriverPath);
				driver = new ChromeDriver();
			} else if (browserType.trim().equalsIgnoreCase("edge")) {
//				Runtime.getRuntime().exec("taskkill /F /IM Microsoft WebDriver.exe");
				System.setProperty("webdriver.edge.driver", GlobalUtil.edgeDriverPath);
				driver = new EdgeDriver();
			} else if (browserType.trim().equalsIgnoreCase("firefox")) {
//				Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
				System.setProperty("webdriver.gecko.driver", GlobalUtil.firefoxDriverPath);
				driver = new FirefoxDriver();
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, Integer.parseInt(PropertyUtil.getConfig("explicitTimeout")));
			js = (JavascriptExecutor) driver;
			ReportUtil.reportEvent(LogStatus.INFO, browserType.toUpperCase() + "- Browser session created", false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, browserType.toUpperCase() + "- Could not create a Browser Session",
					true);
		}
	}

	/**
	 * <i><b>loadURL</b> is a method to load the URL in active browser</i>
	 * 
	 * @param url
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void loadURL(String url) {
		try {
			initializeBrowser();
			driver.get(url);
			ReportUtil.reportEvent(LogStatus.PASS, "URL loaded successfully - <b>" + url + "</b>", true);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Could not load URL - " + url, true);
		}
	}

	/**
	 * <i><b>closeBrowser</b> is a method to close the browser</i>
	 * 
	 * @param none
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void closeBrowser() {
		try {
			driver.close();
			driver.quit();
			ReportUtil.reportEvent(LogStatus.INFO, "Browser Session closed", false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.ERROR, "Unable to close the Browser Session", false);
		}

	}

	/**
	 * <i><b>getWebElement</b> is a method to get the Web Element and returns it
	 * from the provided logical name</i>
	 * 
	 * @param objectName
	 * @return element
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static WebElement getWebElement(String objectName) {
		WebElement element = null;
		String propertyType;
		String propertyValue;
		String locator;

		try {

			locator = PropertyUtil.getObject(objectName);

			if (locator.equalsIgnoreCase("Object Not Found")) {
				ReportUtil.reportEvent(LogStatus.ERROR, "Object Not Found In Repository - " + objectName, false);
			} else {

				waitForElement(objectName);

				String[] tempLocator = locator.split("~");
				propertyType = tempLocator[0];
				propertyValue = tempLocator[1];

				switch (propertyType) {
				case "id":
					element = driver.findElement(By.id(propertyValue));
					break;
				case "name":
					element = driver.findElement(By.name(propertyValue));
					break;
				case "xpath":
					element = driver.findElement(By.xpath(propertyValue));
					break;
				case "className":
					element = driver.findElement(By.className(propertyValue));
					break;
				case "tagName":
					element = driver.findElement(By.tagName(propertyValue));
					break;
				case "linkText":
					element = driver.findElement(By.linkText(propertyValue));
					break;
				case "partialLinkText":
					element = driver.findElement(By.partialLinkText(propertyValue));
					break;
				case "cssSelector":
					element = driver.findElement(By.cssSelector(propertyValue));
					break;
				default:
					ReportUtil.reportEvent(LogStatus.ERROR, "Element Type Not Found - " + propertyType, false);
				}
			}

		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Element Not Found - " + objectName, true);
		}

		return element;
	}

	/**
	 * <i><b>getWebElements</b> is a method to get list of Web Element and returns
	 * it from the provided logical name</i>
	 * 
	 * @param objectName
	 * @return elements
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static List<WebElement> getWebElements(String objectName) {
		List<WebElement> elements = null;
		String propertyType;
		String propertyValue;
		String locator;

		try {

			locator = PropertyUtil.getObject(objectName);

			if (locator.equalsIgnoreCase("Object Not Found")) {
				ReportUtil.reportEvent(LogStatus.ERROR, "Object Not Found In Repository - " + objectName, false);
			} else {

				waitForElement(objectName);

				String[] tempLocator = locator.split("~");
				propertyType = tempLocator[0];
				propertyValue = tempLocator[1];

				switch (propertyType) {
				case "id":
					elements = driver.findElements(By.id(propertyValue));
					break;
				case "name":
					elements = driver.findElements(By.name(propertyValue));
					break;
				case "xpath":
					elements = driver.findElements(By.xpath(propertyValue));
					break;
				case "className":
					elements = driver.findElements(By.className(propertyValue));
					break;
				case "tagName":
					elements = driver.findElements(By.tagName(propertyValue));
					break;
				case "linkText":
					elements = driver.findElements(By.linkText(propertyValue));
					break;
				case "partialLinkText":
					elements = driver.findElements(By.partialLinkText(propertyValue));
					break;
				case "cssSelector":
					elements = driver.findElements(By.cssSelector(propertyValue));
					break;
				default:
					ReportUtil.reportEvent(LogStatus.ERROR, "Element Type Not Found - " + propertyType, false);
				}

			}

		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Element Not Found - " + objectName, true);
		}

		return elements;
	}

	/**
	 * <i><b>waitForElement</b> is a method wait for an element with
	 * WebElementWait</i>
	 * 
	 * @param propertyType
	 * @param propertyValue
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void waitForElement(String objectName) {

		String propertyType;
		String propertyValue;
		String locator;

		try {

			locator = PropertyUtil.getObject(objectName);

			if (locator.equalsIgnoreCase("Object Not Found")) {
				ReportUtil.reportEvent(LogStatus.ERROR, "Object Not Found In Repository - " + objectName, false);
			} else {
				String[] tempLocator = locator.split("~");
				propertyType = tempLocator[0];
				propertyValue = tempLocator[1];

				switch (propertyType) {
				case "id":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id(propertyValue)));
					break;
				case "name":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name(propertyValue)));
					break;
				case "xpath":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(propertyValue)));
					break;
				case "className":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.className(propertyValue)));
					break;
				case "tagName":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(propertyValue)));
					break;
				case "linkText":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(propertyValue)));
					break;
				case "partialLinkText":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(propertyValue)));
					break;
				case "cssSelector":
					wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(propertyValue)));
					break;
				default:
					ReportUtil.reportEvent(LogStatus.ERROR, "Element Type Not Found - " + propertyType, false);
				}
			}
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Element Not Found - " + objectName, false);
		}
	}

	/**
	 * <i><b>isWebelementDisplayedAndEnabled</b> is a method to check whether the
	 * element is Displayed & Enabled</i>
	 * 
	 * @param objectName
	 * @return elementFound
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static boolean isWebelementDisplayedAndEnabled(String objectName) {
		WebElement element = null;
		boolean elementFound = false;
		try {
			element = getWebElement(objectName);
			elementFound = element.isDisplayed() && element.isEnabled();
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Element is not Displayed & Enabled " + objectName, true);
		}

		return elementFound;
	}

	/**
	 * <i><b>isClickable</b> is a method to check whether the element is
	 * clickable</i>
	 * 
	 * @param objectName
	 * @return clickable
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static boolean isClickable(String objectName) {
		WebElement element = null;
		boolean clickable = false;

		try {
			waitForElement(objectName);
			element = getWebElement(objectName);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			clickable = true;
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Element is not Clickable " + objectName, true);
		}

		return clickable;
	}

	/**
	 * <i><b>clickElement</b> is a method to click an web element</i>
	 * 
	 * @param objectName
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */

	public static void clickElement(String objectName) {
		WebElement element = null;
		try {
			isClickable(objectName);
			element = getWebElement(objectName);
			element.click();
			ReportUtil.reportEvent(LogStatus.PASS, "Element Clicked " + objectName, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Unable To Click Element " + objectName, true);
		}
	}

	/**
	 * <i><b>jsClickElement</b> is a method to to click an web element with
	 * JavascriptExecutor</i>
	 * 
	 * @param objectName
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void jsClickElement(String objectName) {
		WebElement element = null;
		try {
			waitForElement(objectName);
			element = getWebElement(objectName);
			js.executeScript("arguments[0].click();", element);
			ReportUtil.reportEvent(LogStatus.PASS, "Element Clicked " + objectName, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Unable To Click Element " + objectName, true);
		}
	}

	/**
	 * <i><b>setText</b> is a method to enter text in an web element</i>
	 * 
	 * @param objectName
	 * @param text
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void setText(String objectName, String text) {
		WebElement element = null;
		try {
			element = getWebElement(objectName);
			element.sendKeys(text);
			ReportUtil.reportEvent(LogStatus.PASS, objectName + " Entered In Element " + text, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Failed To Enter " + text + " in Element " + objectName, true);
		}
	}

	/**
	 * <i><b>jsSetText</b> is a method to enter text in an web element with
	 * JavascriptExecutor</i>
	 * 
	 * @param objectName
	 * @param text
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void jsSetText(String objectName, String text) {
		WebElement element = null;
		try {
			element = getWebElement(objectName);
			js.executeScript("arguments[0].value='" + text + "';", element);
			ReportUtil.reportEvent(LogStatus.PASS, objectName + " Entered In Element " + text, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Failed To Enter " + text + " in Element " + objectName, true);
		}
	}

	/**
	 * <i><b>clickElementWithXpath</b> is a method to click an web element with a
	 * dynamic xpath directly passed</i>
	 * 
	 * @param xpath
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */

	public static void clickElementWithXpath(String xpath) {
		WebElement element = null;
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			element = driver.findElement(By.xpath(xpath));
			element.click();
			ReportUtil.reportEvent(LogStatus.PASS, "Element Clicked " + xpath + " with Xpath", false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Unable To Click Element " + xpath + " with Xpath", true);
		}
	}

	/**
	 * <i><b>jsClickElement</b> is a method to to click an web element with a
	 * dynamic xpath and JavascriptExecutor</i>
	 * 
	 * @param xpath
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void jsClickElementWithXpath(String xpath) {
		WebElement element = null;
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			element = driver.findElement(By.xpath(xpath));
			js.executeScript("arguments[0].click();", element);
			ReportUtil.reportEvent(LogStatus.PASS, "Element Clicked " + xpath, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Unable To Click Element " + xpath, true);
		}
	}

	/**
	 * <i><b>setTextWithXpath</b> is a method to enter text with a dynamic xpath in
	 * an web element</i>
	 * 
	 * @param xpath
	 * @param text
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void setTextWithXpath(String xpath, String text) {
		WebElement element = null;
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			element = driver.findElement(By.xpath(xpath));
			element.sendKeys(text);
			ReportUtil.reportEvent(LogStatus.PASS, xpath + " Entered In Element " + text, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Failed To Enter " + text + " in Element " + xpath, true);
		}
	}

	/**
	 * <i><b>jsSetTextWithXpath</b> is a method to enter text in an web element with
	 * a dynamic xpath and JavascriptExecutor</i>
	 * 
	 * @param xpath
	 * @param text
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void jsSetTextWithXpath(String xpath, String text) {
		WebElement element = null;
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			element = driver.findElement(By.xpath(xpath));
			js.executeScript("arguments[0].value='" + text + "';", element);
			ReportUtil.reportEvent(LogStatus.PASS, xpath + " Entered In Element " + text, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Failed To Enter " + text + " in Element " + xpath, true);
		}
	}

	/**
	 * <i><b>selectFromDropdown</b> is a method to select an option from
	 * dropdown</i>
	 * 
	 * @param objectName
	 * @param selectType
	 * @param optionToSelect
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void selectFromDropdown(String objectName, String selectType, String optionToSelect) {
		WebElement element = null;
		Select options = null;
		try {
			element = getWebElement(objectName);
			options = new Select(element);

			switch (selectType) {
			case "text":
				options.selectByVisibleText(optionToSelect);
				ReportUtil.reportEvent(LogStatus.PASS,
						"Selected option with visible text " + optionToSelect + " from the dropdown " + objectName,
						false);
				break;
			case "value":
				options.selectByValue(optionToSelect);
				ReportUtil.reportEvent(LogStatus.PASS,
						"Selected option with value " + optionToSelect + " from the dropdown " + objectName, false);
				break;
			case "index":
				options.selectByIndex(Integer.parseInt(optionToSelect));
				ReportUtil.reportEvent(LogStatus.PASS,
						"Selected option with index " + optionToSelect + " from the dropdown " + objectName, false);
				break;

			default:
				ReportUtil.reportEvent(LogStatus.ERROR, "Select type not available " + objectName, false);
				break;
			}

		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Couldn't select " + optionToSelect + " from " + objectName, true);
		}
	}

	/**
	 * <i><b>getText</b> is a method to get the visible text of an webelement</i>
	 * 
	 * @param objectName
	 * @return text
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static String getText(String objectName) {
		WebElement element = null;
		String text = null;

		try {
			element = getWebElement(objectName);
			text = element.getText();
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.ERROR, e.getMessage() + objectName, false);
		}

		return text;
	}

	/**
	 * <i><b>getAttribute</b> is a method to get the required attribute of an
	 * webelement</i>
	 * 
	 * @param objectName
	 * @return attributeValue
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static String getAttribute(String objectName, String requiredAttribute) {
		String attributeValue = null;
		WebElement element = null;
		try {
			element = getWebElement(objectName);
			attributeValue = element.getAttribute(requiredAttribute);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.ERROR, e.getMessage() + objectName, false);
		}

		return attributeValue;
	}

	/**
	 * <i><b>switchToFrameWithIndex</b> is a method to switch to a frame with frame
	 * index</i>
	 * 
	 * @param frameIndex
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void switchToFrameWithIndex(int frameIndex) {
		try {
			driver.switchTo().frame(frameIndex);
			ReportUtil.reportEvent(LogStatus.PASS, "Switched to frame with index " + frameIndex, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Unable to switch to frame with index " + frameIndex, true);
		}
	}

	/**
	 * <i><b>switchToFrameWithName</b> is a method to switch to a frame with frame
	 * Name</i>
	 * 
	 * @param frameName
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void switchToFrameWithName(String frameName) {
		try {
			driver.switchTo().frame(frameName);
			ReportUtil.reportEvent(LogStatus.PASS, "Switched to frame with name " + frameName, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Unable to switch to frame with name " + frameName, true);
		}
	}

	/**
	 * <i><b>switchToFrameWithName</b> is a method to switch to a frame with frame
	 * Name</i>
	 * 
	 * @param frameName
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void switchToFrameWithElement(String objectName) {
		WebElement frameElement = null;
		try {
			frameElement = getWebElement(objectName);
			driver.switchTo().frame(frameElement);
			ReportUtil.reportEvent(LogStatus.PASS, "Switched to frame with Element " + objectName, false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.FAIL, "Unable to switch to frame with Element " + objectName, true);
		}
	}

	/**
	 * <i><b>getCurrentWindowId</b> is a method to fetch and return the current
	 * window id</i>
	 * 
	 * @param none
	 * @return currentWindowId
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static String getCurrentWindowId() {
		String currentWindowId = null;
		try {
			currentWindowId = driver.getWindowHandle();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return currentWindowId;
	}

	/**
	 * <i><b>getAllWindowIds</b> is a method to fetch and return the all the open
	 * window ids</i>
	 * 
	 * @param none
	 * @return windowIds
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static Set<String> getAllWindowIds() {
		Set<String> windowIds = null;
		try {
			windowIds = driver.getWindowHandles();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return windowIds;
	}

	/**
	 * <i><b>switchToWindowWithTitle</b> is a method to switch to a window with
	 * window title</i>
	 * 
	 * @param windowTitle
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void switchToWindowWithTitle(String windowTitle) {
		try {
			String currentWindow = getCurrentWindowId();
			Set<String> windows = getAllWindowIds();
			boolean switched = false;

			for (String window : windows) {
				if (!window.equalsIgnoreCase(currentWindow)) {
					if (driver.getTitle().equalsIgnoreCase(windowTitle)) {
						switched = true;
						break;
					}
				}
			}

			if (switched) {
				ReportUtil.reportEvent(LogStatus.PASS, "Switched to window with title " + windowTitle, true);
			} else {
				ReportUtil.reportEvent(LogStatus.FAIL, "Unable to switch to window with title " + windowTitle, true);
			}

		} catch (Exception e) {
		}

	}

	/**
	 * <i><b>switchToMainPage</b> is a method to switch to a default content from
	 * the frame</i>
	 * 
	 * @param none
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void switchToMainPage() {
		try {
			driver.switchTo().defaultContent();
			ReportUtil.reportEvent(LogStatus.PASS, "Switched to the Main page", false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.PASS, "Switched to the Main page", true);
		}
	}

	/**
	 * <i><b>switchToParentWindow</b> is a method to switch to the parent window
	 * from the frame</i>
	 * 
	 * @param none
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void switchToParentWindow() {
		try {
			driver.switchTo().parentFrame();
			ReportUtil.reportEvent(LogStatus.PASS, "Switched to the Parent window", false);
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.PASS, "Switched to the Parent window", true);
		}
	}

	/**
	 * <i><b>waitTillPageToCompletelyLoaded</b> is a method to verify and wait with
	 * javascript till the website loads completely</i>
	 * 
	 * @param none
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void waitTillPageToCompletelyLoaded() {
		try {
			Object state = js.executeScript("return document.readyState");
			while (!state.toString().equalsIgnoreCase("complete")) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * <i><b>verifyText</b> is a method to Validate the visible text of an
	 * webelement with Expected text</i>
	 * 
	 * @param objectName
	 * @param expectedText
	 * @return flag
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static boolean verifyText(String objectName, String expectedText) {
		boolean flag = false;
		String actualText = null;
		try {
			actualText = getText(objectName);
			if (actualText.equalsIgnoreCase(expectedText)) {
				flag = true;
				ReportUtil.reportEvent(LogStatus.PASS,
						"Actual Text " + actualText + " is matching with the Expected Text " + expectedText, true);
			} else {
				ReportUtil.reportEvent(LogStatus.FAIL,
						"Actual Text " + actualText + " is not matching with the Expected Text " + expectedText, true);
			}
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.ERROR,
					"Actual Text " + actualText + " is not matching with the Expected Text " + expectedText, true);
		}

		return flag;
	}

	/**
	 * <i><b>verifyPageTitle</b> is a method to Validate the title of the page</i>
	 * 
	 * @param expectedTitle
	 * @return none
	 * @exception Exception
	 * @author Sriraman Raji
	 * @since 24-December-2018
	 */
	public static void verifyPageTitle(String expectedTitle) {
		String actualTitle = null;
		try {
			actualTitle = driver.getTitle();
			if (actualTitle.equalsIgnoreCase(expectedTitle)) {
				ReportUtil.reportEvent(LogStatus.PASS,
						"Actual Title " + actualTitle + " is matching with the Expected Text " + expectedTitle, true);
			} else {
				ReportUtil.reportEvent(LogStatus.FAIL,
						"Actual Title " + actualTitle + " is not matching with the Expected Text " + expectedTitle,
						true);
			}
		} catch (Exception e) {
			ReportUtil.reportEvent(LogStatus.ERROR,
					"Actual Title " + actualTitle + " is not matching with the Expected Text " + expectedTitle, true);
		}
	}

}