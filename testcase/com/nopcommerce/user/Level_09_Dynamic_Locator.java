package com.nopcommerce.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.nopCommerce.user.UserAddressPageObject;
import pageObjects.nopCommerce.user.UserCustomerInforPageObject;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserLoginPageObject;
import pageObjects.nopCommerce.user.UserMyProductReviewPageObject;
import pageObjects.nopCommerce.user.UserRegisterPageObject;
import pageObjects.nopCommerce.user.UserRewardPointPageObject;

public class Level_09_Dynamic_Locator extends BaseTest {
	private WebDriver driver;
	private String emailAddress, firstName, lastName, password;
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private UserLoginPageObject loginPage;
	private UserCustomerInforPageObject customerInforPage;
	private UserAddressPageObject addressPage;
	private UserRewardPointPageObject rewardPointPage;
	private UserMyProductReviewPageObject myProductReviewPage;

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName);
		homePage = PageGeneratorManager.getUserHomePage(driver);

		firstName = "Automation";
		lastName = "FC";
		password = "123456";
		emailAddress = "afc" + fakeNumber() + "@mail.vn";

	}

	@Test
	public void User_01_Register_Login() {
		registerPage = homePage.clickToRegisterLink();
		registerPage.inputToFirstnameTextbox(firstName);
		registerPage.inputToLastnameTextbox(lastName);
		registerPage.inputToEmailTextbox(emailAddress);
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox(password);
		registerPage.clickToRegisterButton();
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

		homePage = registerPage.clickToContinueButton();

		loginPage = homePage.openLoginPage();
		loginPage.inputToEmailTextbox(emailAddress);
		loginPage.inputToPasswordTextbox(password);
		homePage = loginPage.clickToLoginButton();
		Assert.assertTrue(homePage.isMyAccountLinkDisplayed());

		customerInforPage = homePage.clickToMyAccountLink();
		Assert.assertTrue(customerInforPage.isCustomerInforPageDisplayed());
	}

	@Test
	public void User_02_Switch_Page() {
		// Customer Infor -> Address
		addressPage = customerInforPage.openAddressPage(driver);

		// Address -> My Product Review
		myProductReviewPage = addressPage.openMyProductReviewPage(driver);

		// My Product Review -> Reward Point
		rewardPointPage = myProductReviewPage.openRewardPointPage(driver);

		// Reward Point -> Address
		addressPage = rewardPointPage.openAddressPage(driver);

		// Address -> Reward Point
		rewardPointPage = addressPage.openRewardPointPage(driver);

		// Reward Point -> My Product Review
		myProductReviewPage = rewardPointPage.openMyProductReviewPage(driver);

		// My Product Review -> Address
		addressPage = myProductReviewPage.openAddressPage(driver);

		customerInforPage = addressPage.openCustomerInforPage(driver);

		myProductReviewPage = customerInforPage.openMyProductReviewPage(driver);
	}

	@Test
	public void User_03_Dynamic_Page_01() {
		// My Product Review -> Reward Point
		rewardPointPage = (UserRewardPointPageObject) myProductReviewPage.openPagesAtMyAccountByName(driver,
				"Reward points");

		// Reward Point -> Address
		addressPage = (UserAddressPageObject) rewardPointPage.openPagesAtMyAccountByName(driver, "Addresses");

		// Address -> Reward Point
		rewardPointPage = (UserRewardPointPageObject) addressPage.openPagesAtMyAccountByName(driver, "Reward points");

		// Reward Point -> My Product Review
		myProductReviewPage = (UserMyProductReviewPageObject) rewardPointPage.openPagesAtMyAccountByName(driver,
				"My product reviews");

		// My Product Review -> Address
		addressPage = (UserAddressPageObject) myProductReviewPage.openPagesAtMyAccountByName(driver, "Addresses");

		customerInforPage = (UserCustomerInforPageObject) addressPage.openPagesAtMyAccountByName(driver,
				"Customer info");

		myProductReviewPage = (UserMyProductReviewPageObject) customerInforPage.openPagesAtMyAccountByName(driver,
				"My product reviews");
	}

	@Test
	public void User_03_Dynamic_Page_02() {
		// My Product Review -> Reward Point
		myProductReviewPage.openPagesAtMyAccountByName(driver, "Reward points");
		rewardPointPage = PageGeneratorManager.getUserRewardPointPage(driver);

		// Reward Point -> Address
		rewardPointPage.openPagesAtMyAccountByName(driver, "Addresses");
		addressPage = PageGeneratorManager.getUserAddressPagePage(driver);

		// Address -> Reward Point
		addressPage.openPagesAtMyAccountByName(driver, "Reward points");
		rewardPointPage = PageGeneratorManager.getUserRewardPointPage(driver);

		// Reward Point -> My Product Review
		rewardPointPage.openPagesAtMyAccountByName(driver, "My product reviews");
		myProductReviewPage = PageGeneratorManager.getUserMyProductReviewPage(driver);

		// My Product Review -> Address
		myProductReviewPage.openPagesAtMyAccountByName(driver, "Addresses");
		addressPage = PageGeneratorManager.getUserAddressPagePage(driver);

		// Address - > Customer infor
		addressPage.openPagesAtMyAccountByName(driver, "Customer info");
		customerInforPage = PageGeneratorManager.getUserCustomerInforPage(driver);

		// Customer infor -> My product Review
		customerInforPage.openPagesAtMyAccountByName(driver, "My product reviews");
		myProductReviewPage = PageGeneratorManager.getUserMyProductReviewPage(driver);
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}

}
