package pageObject.liveGuru;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.liveGuru.DashBoardPageUI;

public class MyDashboardPageObject extends BasePage {

	private WebDriver driver;

	public MyDashboardPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public void clickToAccountButton() {
		waitForElementClickable(driver, DashBoardPageUI.ACCOUNT_BUTTON);
		clickToElement(driver, DashBoardPageUI.ACCOUNT_BUTTON);
	}

	public HomePageObject clickToLogOut() {
		waitForElementClickable(driver, DashBoardPageUI.LOGIN_BUTTON);
		clickToElement(driver, DashBoardPageUI.LOGIN_BUTTON);
		return PageGeneratorManager.getHomePage(driver);
	}

	public String getLoginSuccessMessage() {
		waitForElementVisible(driver, DashBoardPageUI.LOGIN_SUCCESS_MESSAGE);
		return getElementText(driver, DashBoardPageUI.LOGIN_SUCCESS_MESSAGE);
	}

	public String getRegisterSuccessMessage() {
		waitForElementVisible(driver, DashBoardPageUI.REGISTER_SUCCESS_MESSAGE);
		return getElementText(driver, DashBoardPageUI.REGISTER_SUCCESS_MESSAGE);
	}

}
