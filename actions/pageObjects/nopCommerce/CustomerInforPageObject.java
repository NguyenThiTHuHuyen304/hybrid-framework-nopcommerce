package pageObjects.nopCommerce;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.nopCommerce.CustomerInforPageUI;

public class CustomerInforPageObject extends BasePage {

	private WebDriver driver;

	public CustomerInforPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isCustomerInforPageDisplayed() {
		waitForElementVisible(driver, CustomerInforPageUI.CUSTOMER_INFOR_HEADER);
		return isElementDisplayed(driver, CustomerInforPageUI.CUSTOMER_INFOR_HEADER);
	}

}
