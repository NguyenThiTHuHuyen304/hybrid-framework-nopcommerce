package commons;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObject.nopCommerce.admin.AdminLoginPageObject;
import pageObjects.nopCommerce.user.UserAddressPageObject;
import pageObjects.nopCommerce.user.UserCustomerInforPageObject;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserMyProductReviewPageObject;
import pageObjects.nopCommerce.user.UserRewardPointPageObject;
import pageUIs.nopCommerce.user.BasePageUI;

public class BasePage {

	private long longtimeout = 30;

	public static BasePage getBasePageObject() {
		return new BasePage();
	}

	// Nhiệm vụ mở 1 Url bất kì
	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	public String getTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public void getPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshCurentPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void accpectAlert(WebDriver driver) {
		waitForAlertPresence(driver).accept();
	}

	public Alert waitForAlertPresence(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longtimeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void cancelAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}

	public String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}

	public void sendkeyToAlert(WebDriver driver, String valueText) {
		waitForAlertPresence(driver).sendKeys(valueText);
	}

	public void switchToWindownByID(WebDriver driver, String windownID) {
		Set<String> allWindownIDs = driver.getWindowHandles();
		for (String id : allWindownIDs) {
			if (!id.equals(windownID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}

	public void switchToWindownByTitle(WebDriver driver, String tabTitle) {
		Set<String> allWindownTitles = driver.getWindowHandles();
		for (String id : allWindownTitles) {
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle();
			if (actualTitle.equals(tabTitle)) {
				break;
			}
		}
	}

	public void closeAllTabWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindownIDs = driver.getWindowHandles();
		for (String id : allWindownIDs) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
			}

			driver.switchTo().window(parentID);
		}
	}

	private WebElement getWebElement(WebDriver driver, String locatorType) {
		return driver.findElement(getByLocator(locatorType));
	}

	private By getByLocator(String locatorType) {
		By by = null;
		System.out.println("Locator Type: " + locatorType);
		if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=")) {
			by = By.id(locatorType.substring(3));
		} else if (locatorType.startsWith("css=") || locatorType.startsWith("CSS=") || locatorType.startsWith("Css=")) {
			by = By.cssSelector(locatorType.substring(4));
		} else if (locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=")
				|| locatorType.startsWith("Xpath=") || locatorType.startsWith("XPath=")) {
			by = By.xpath(locatorType.substring(6));
		} else if (locatorType.startsWith("name=") || locatorType.startsWith("NAME=")
				|| locatorType.startsWith("Name=")) {
			by = By.name(locatorType.substring(5));
		} else if (locatorType.startsWith("class=") || locatorType.startsWith("CLASS=")
				|| locatorType.startsWith("Class=")) {
			by = By.className(locatorType.substring(6));
		} else {
			throw new RuntimeException("Locator type is not supported!!");
		}

		return by;
	}

	// Nếu truyền vào locator là xpath == đúng
	// Truyền locator khác xpath == sai
	private String getDynamicXpath(String locatorType, String... values) {
		System.out.println("Locator type before: " + locatorType);
		if (locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("Xpath=")
				|| locatorType.startsWith("XPath=")) {
			locatorType = String.format(locatorType, (Object[]) values);
		}
		for (String value : values) {
			System.out.println("Values map to locator: " + value);
		}
		System.out.println("Locator type after: " + locatorType);
		return locatorType;
	}

	private List<WebElement> getListWebElements(WebDriver driver, String locatorType) {
		return driver.findElements(getByLocator(locatorType));
	}

	public void clickToElement(WebDriver driver, String locatorType) {
		getWebElement(driver, locatorType).click();
	}

	public void clickToElement(WebDriver driver, String locatorType, String... dynamicValues) {
		getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
	}

	public void senkeyToElement(WebDriver driver, String locatorType, String valueText) {
		WebElement element = getWebElement(driver, locatorType);
		element.clear();
		element.sendKeys(valueText);
	}

	public void senkeyToElement(WebDriver driver, String locatorType, String valueText, String... dynamicValues) {
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		element.clear();
		element.sendKeys(valueText);
	}

	public void selectItemDefaultDropdown(WebDriver driver, String locatorType, String valueItem) {
		Select select = new Select(getWebElement(driver, locatorType));
		select.selectByValue(valueItem);
	}

	public void selectItemDefaultDropdown(WebDriver driver, String locatorType, String valueItem,
			String... dynamicValues) {
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		select.selectByValue(valueItem);
	}

	// Verify
	public String getSelectedItemDefaultDropdown(WebDriver driver, String locatorType) {
		Select select = new Select(getWebElement(driver, locatorType));
		return select.getFirstSelectedOption().getText();
	}

	public Boolean isDropdownMutiple(WebDriver driver, String locatorType) {
		Select select = new Select(getWebElement(driver, locatorType));
		return select.isMultiple();
	}

	public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String childXpath,
			String expectedTextItem) {
		getWebElement(driver, parentXpath).click();
		sleepInSecond(1);

		WebDriverWait explicitWait = new WebDriverWait(driver, longtimeout);
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childXpath)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedTextItem)) {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntroView(true)", item);
				sleepInSecond(1);
				item.click();
				break;
			}
		}

	}

	public String getElementAttribute(WebDriver driver, String locatorType, String attributeName) {
		return getWebElement(driver, locatorType).getAttribute(attributeName);
	}

	public String getElementText(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).getText();
	}

	public String getElementText(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getText();
	}

	public String getElementCssValue(WebDriver driver, String locatorType, String propertyName) {
		return getWebElement(driver, locatorType).getCssValue(propertyName);
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getHexaColorFromRGBA(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
	}

	public int getElementSize(WebDriver driver, String locatorType) {
		return getListWebElements(driver, locatorType).size();
	}

	public int getElementSize(WebDriver driver, String locatorType, String... dynamicValues) {
		return getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues)).size();
	}

	public void checkToDefaultCheckboxRadio(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToDefaultCheckbox(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		if (element.isSelected()) {
			element.click();
		}
	}

	public Boolean isElementDisplayed(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).isDisplayed();
	}

	public Boolean isElementDisplayed(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
	}

	public Boolean isElementEnabled(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).isEnabled();
	}

	public Boolean isElementSelected(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).isSelected();
	}

	public void switchToFrameIFrame(WebDriver driver, String locatorType) {
		driver.switchTo().frame(getWebElement(driver, locatorType));
	}

	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void houverMouseToElement(WebDriver driver, String locatorType) {
		Actions action = new Actions(driver);
		action.moveToElement(getWebElement(driver, locatorType)).perform();
	}

	public void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void hightlightElement(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, locatorType);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element,
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, locatorType));
	}

	public void scrollToElement(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
	}

	public void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				getWebElement(driver, locatorType));
	}

	public Boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebDriverWait explicitWait = new WebDriverWait(driver, longtimeout);

		ExpectedCondition<Boolean> jsQueryLoad = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return jsExecutor.executeScript("return document.readyState").toString().equals("Complete");
				} catch (Exception e) {
					return true;
				}
			}
		};
		return explicitWait.until(jsQueryLoad) && explicitWait.until(jsLoad);
	}

	public String getElementValidationMessage(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
				getWebElement(driver, locatorType));
	}

	public boolean isImageLoaded(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getWebElement(driver, locatorType));

		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void waitForElementVisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longtimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
	}

	public void waitForElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longtimeout);
		explicitWait.until(ExpectedConditions
				.visibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForAllElementVisible(WebDriver driver, String locatorType) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
	}

	public void waitForAllElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForElementInvisible(WebDriver driver, String locatorType) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
	}

	public void waitForElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForAllElementInvisible(WebDriver driver, String locatorType) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, locatorType)));
	}

	public void waitForAllElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions
				.invisibilityOfAllElements(getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues))));
	}

	public void waitForElementClickable(WebDriver driver, String locatorType) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
	}

	public void waitForElementClickable(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(
				ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}

	// Tối ưu ở bài học Level_07_Switch_Page
	public UserMyProductReviewPageObject openMyProductReviewPage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.MY_PRODUCT_REVIEW_LINK);
		clickToElement(driver, BasePageUI.MY_PRODUCT_REVIEW_LINK);
		return PageGeneratorManager.getUserMyProductReviewPage(driver);
	}

	public UserRewardPointPageObject openRewardPointPage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.REWARD_POINT_LINK);
		clickToElement(driver, BasePageUI.REWARD_POINT_LINK);
		return PageGeneratorManager.getUserRewardPointPage(driver);
	}

	public UserAddressPageObject openAddressPage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.ADDRESS_LINK);
		clickToElement(driver, BasePageUI.ADDRESS_LINK);
		return PageGeneratorManager.getUserAddressPagePage(driver);
	}

	public UserCustomerInforPageObject openCustomerInforPage(WebDriver driver) {
		waitForElementVisible(driver, BasePageUI.CUSTOMER_INFOR_LINK);
		clickToElement(driver, BasePageUI.CUSTOMER_INFOR_LINK);
		return PageGeneratorManager.getUserCustomerInforPage(driver);
	}

	// Tối ưu ở bài Level_09_Dynamic_Locator
	public BasePage openPagesAtMyAccountByName(WebDriver driver, String pageName) {
		waitForElementClickable(driver, BasePageUI.DYNAMIC_PAGES_AT_MY_ACCOUNT_AREA, pageName);
		clickToElement(driver, BasePageUI.DYNAMIC_PAGES_AT_MY_ACCOUNT_AREA, pageName);
		switch (pageName) {
		case "Customer info":
			return PageGeneratorManager.getUserCustomerInforPage(driver);
		case "Addresses":
			return PageGeneratorManager.getUserAddressPagePage(driver);
		case "My product reviews":
			return PageGeneratorManager.getUserMyProductReviewPage(driver);
		case "Reward points":
			return PageGeneratorManager.getUserRewardPointPage(driver);

		default:
			throw new RuntimeException("Invalid page name at My Account area");
		}
	}

	// Tối ưu ở bài Level_09_Dynamic_Locator
	public void openPagesAtMyAccountByPageName(WebDriver driver, String pageName) {
		waitForElementClickable(driver, BasePageUI.DYNAMIC_PAGES_AT_MY_ACCOUNT_AREA, pageName);
		clickToElement(driver, BasePageUI.DYNAMIC_PAGES_AT_MY_ACCOUNT_AREA, pageName);
	}

	// Level_08_Switch_Role
	public AdminLoginPageObject clickToLogoutLinkAtAdminPage(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.LOGOUT_LINK_ADMIN);
		clickToElement(driver, BasePageUI.LOGOUT_LINK_ADMIN);
		return PageGeneratorManager.getAdminLoginPage(driver);
	}

	public UserHomePageObject clickToLogoutLinkAtUserPage(WebDriver driver) {
		waitForElementClickable(driver, BasePageUI.LOGOUT_LINK_USER);
		clickToElement(driver, BasePageUI.LOGOUT_LINK_USER);
		return PageGeneratorManager.getUserHomePage(driver);
	}
}
