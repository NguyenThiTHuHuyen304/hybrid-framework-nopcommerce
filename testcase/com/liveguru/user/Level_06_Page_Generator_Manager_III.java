package com.liveguru.user;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import pageObject.liveGuru.HomePageObject;
import pageObject.liveGuru.LoginPageObject;
import pageObject.liveGuru.MyDashboardPageObject;
import pageObject.liveGuru.PageGeneratorManager;
import pageObject.liveGuru.RegisterPageObject;

public class Level_06_Page_Generator_Manager_III extends BaseTest {
	private WebDriver driver;
	private HomePageObject homePage;
	private RegisterPageObject registerPage;
	private LoginPageObject loginPage;
	private MyDashboardPageObject myDashboardPage;
	private String firstName, lastName, emailAddress, password, confirmPassword;

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserDriver(browserName);
		homePage = PageGeneratorManager.getHomePage(driver);

		firstName = "Nguyen";
		lastName = "Huyen";
		emailAddress = "NguyenHuyen" + fakeNumber() + "@gmail.com";
		password = "123123";
		confirmPassword = "123123";
	}

	@Test
	public void User_01_Register_To_System() {
		loginPage = homePage.clickToMyAccountLink();
		registerPage = loginPage.clickToCreateAccountButton();
		registerPage.inputToFirstNameTextbox(firstName);
		registerPage.inputToLastNameTextbox(lastName);
		registerPage.inputToEmailTextbox(emailAddress);
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox(confirmPassword);
		myDashboardPage = registerPage.clickToRegisterButton();
		Assert.assertEquals(myDashboardPage.getRegisterSuccessMessage(), "Thank you for registering with Main Website Store.");
		myDashboardPage.clickToAccountButton();
		homePage = myDashboardPage.clickToLogOut();
	}

	@Test
	public void User_02_Login_To_System() {
		loginPage = homePage.clickToMyAccountLink();
		loginPage.inputToEmailTextbox(emailAddress);
		loginPage.inputToPasswordTextbox(password);
		myDashboardPage = loginPage.clickToLoginButton();
		Assert.assertTrue(myDashboardPage.getLoginSuccessMessage().contains("From your My Account Dashboard"));
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}

}
