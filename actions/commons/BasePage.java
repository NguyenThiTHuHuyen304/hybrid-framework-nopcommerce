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

	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
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

	private By getByXpath(String xpathLocator) {
		return By.xpath(xpathLocator);
	}

	private WebElement getWebElement(WebDriver driver, String xpathLocator) {
		return driver.findElement(getByXpath(xpathLocator));
	}

	private List<WebElement> getListWebElements(WebDriver driver, String xpathLocator) {
		return driver.findElements(getByXpath(xpathLocator));
	}

	public void clickToElement(WebDriver driver, String xpathLocator) {
		getWebElement(driver, xpathLocator).click();
	}

	public void senkeyToElement(WebDriver driver, String xpathLocator, String valueText) {
		WebElement element = getWebElement(driver, xpathLocator);
		element.clear();
		element.sendKeys(valueText);
	}

	public void selectItemDefaultDropdown(WebDriver driver, String xpathLocator, String valueItem) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		select.selectByValue(valueItem);
	}

	// Verify
	public String getSelectedItemDefaultDropdown(WebDriver driver, String xpathLocator) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		return select.getFirstSelectedOption().getText();
	}

	public Boolean isDropdownMutiple(WebDriver driver, String xpathLocator) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		return select.isMultiple();
	}

	public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String childXpath,
			String expectedTextItem) {
		getWebElement(driver, parentXpath).click();
		sleepInSecond(1);

		WebDriverWait explicitWait = new WebDriverWait(driver, longtimeout);
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));

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

	public String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName) {
		return getWebElement(driver, xpathLocator).getAttribute(attributeName);
	}

	public String getElementText(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).getText();
	}

	public String getElementCssValue(WebDriver driver, String xpathLocator, String propertyName) {
		return getWebElement(driver, xpathLocator).getCssValue(propertyName);
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

	public int getElementSize(WebDriver driver, String xpathLocator) {
		return getListWebElements(driver, xpathLocator).size();
	}

	public void checkToDefaultCheckboxRadio(WebDriver driver, String xpathLocator) {
		WebElement element = getWebElement(driver, xpathLocator);
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToDefaultCheckbox(WebDriver driver, String xpathLocator) {
		WebElement element = getWebElement(driver, xpathLocator);
		if (element.isSelected()) {
			element.click();
		}
	}

	public Boolean isElementDisplayed(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isDisplayed();
	}

	public Boolean isElementEnabled(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isEnabled();
	}

	public Boolean isElementSelected(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isSelected();
	}

	public void switchToFrameIFrame(WebDriver driver, String xpathLocator) {
		driver.switchTo().frame(getWebElement(driver, xpathLocator));
	}

	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void houverMouseToElement(WebDriver driver, String xpathLocator) {
		Actions action = new Actions(driver);
		action.moveToElement(getWebElement(driver, xpathLocator)).perform();
	}

	public void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void hightlightElement(WebDriver driver, String xpathlocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, xpathlocator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element,
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String xpathlocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, xpathlocator));
	}

	public void scrollToElement(WebDriver driver, String xpathlocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, xpathlocator));
	}

	public void removeAttributeInDOM(WebDriver driver, String xpathlocator, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				getWebElement(driver, xpathlocator));
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

	public String getElementValidationMessage(WebDriver driver, String xpathlocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
				getWebElement(driver, xpathlocator));
	}

	public boolean isImageLoaded(WebDriver driver, String xpathlocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getWebElement(driver, xpathlocator));

		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void waitForElementVisible(WebDriver driver, String xpathLocator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longtimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));
	}

	public void waitForAllElementVisible(WebDriver driver, String xpathLocator) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathLocator)));
	}

	public void waitForElementInvisible(WebDriver driver, String xpathLocator) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathLocator)));
	}

	public void waitForAllElementInvisible(WebDriver driver, String xpathLocator) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, xpathLocator)));
	}

	public void waitForElementClickable(WebDriver driver, String xpathLocator) {
		WebDriverWait exeplicitWait = new WebDriverWait(driver, longtimeout);
		exeplicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(xpathLocator)));
	}
}
