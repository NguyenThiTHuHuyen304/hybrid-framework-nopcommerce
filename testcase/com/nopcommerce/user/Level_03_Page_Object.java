package com.nopcommerce.user;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import commons.BasePage;
import pageObjects.HomePageObject;
import pageObjects.RegisterPageObject;

public class Level_03_Page_Object extends BasePage {
	private WebDriver driver;
	private String emailAddress, firstName, lastName, password;
	private HomePageObject homePage;
	private RegisterPageObject registerPage;
	private String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");

		driver = new FirefoxDriver();
		homePage = new HomePageObject(driver);
		registerPage = new RegisterPageObject(driver);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://demo.nopcommerce.com/");

		firstName = "Automation";
		lastName = "FC";
		password = "123456";
		emailAddress = "afc" + fakeNumber() + "@mail.vn";
	}

	@Test
	public void TC_01_Register_Empty_Data() {
		System.out.println("Home Page - Step 01: Click to Register link ");
		homePage.clickToRegisterLink();

		System.out.println("Register Page - Step 02: Click to Register button ");
		registerPage.clickToRegisterButton();

		System.out.println("Register Page - Step 03: Verify error message displayed");
		Assert.assertEquals(registerPage.getErrorMessageAtFirstnameTextbox(), "First name is required.");
		Assert.assertEquals(registerPage.getErrorMessageAtLastnameTextbox(), "Last name is required.");
		Assert.assertEquals(registerPage.getErrorMessageAtEmailTextbox(), "Email is required.");
		Assert.assertEquals(registerPage.getErrorMessageAtPasswordTextbox(), "Password is required.");
		Assert.assertEquals(registerPage.getErrorMessageAtConfirmPasswordTextbox(), "Password is required.");

	}

	@Test
	public void TC_02_Register_Invalid_Email() {
		System.out.println("Home Page - Step 01: Click to Register link ");
		homePage.clickToRegisterLink();

		System.out.println("Home Page - Step 02: Input to required field ");
		registerPage.inputToFirstnameTextbox(firstName);
		registerPage.inputToLastnameTextbox(lastName);
		registerPage.inputToEmailTextbox("123abc@!#$");
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox(password);

		System.out.println("Register Page - Step 03: Click to Register button ");
		registerPage.clickToRegisterButton();

		System.out.println("Register Page - Step 04: Verify error message displayed");
		Assert.assertEquals(registerPage.getErrorMessageAtEmailTextbox(), "Wrong email");

	}

	@Test
	public void TC_03_Register_Success() {
		System.out.println("Home Page - Step 01: Click to Register link ");
		homePage.clickToRegisterLink();

		System.out.println("Home Page - Step 02: Input to required field ");
		registerPage.inputToFirstnameTextbox(firstName);
		registerPage.inputToLastnameTextbox(lastName);
		registerPage.inputToEmailTextbox(emailAddress);
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox(password);

		System.out.println("Register Page - Step 03: Click to Register button ");
		registerPage.clickToRegisterButton();

		System.out.println("Register Page - Step 04: Verify success message displayed");
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

		System.out.println("Register Page - Step 05: Click to Login link ");
		registerPage.clickToLoginLink();
	}

	@Test
	public void TC_04_Register_Existing_Email() {
		System.out.println("Home Page - Step 01: Click to Register link ");
		homePage.clickToRegisterLink();

		System.out.println("Home Page - Step 02: Input to required field ");
		registerPage.inputToFirstnameTextbox(firstName);
		registerPage.inputToLastnameTextbox(lastName);
		registerPage.inputToEmailTextbox(emailAddress);
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox(password);

		System.out.println("Register Page - Step 03: Click to Register button ");
		registerPage.clickToRegisterButton();

		System.out.println("Register Page - Step 04: Verify email exists message displayed");
		Assert.assertEquals(registerPage.getErrorExistingEmailMessage(), "The specified email already exists");

	}

	@Test
	public void TC_05_Register_Password_Less_Than_6_Chars() {

		System.out.println("Home Page - Step 01: Click to Register link ");
		homePage.clickToRegisterLink();

		System.out.println("Home Page - Step 02: Input to required field ");
		registerPage.inputToFirstnameTextbox(firstName);
		registerPage.inputToLastnameTextbox(lastName);
		registerPage.inputToEmailTextbox(emailAddress);
		registerPage.inputToPasswordTextbox("123");
		registerPage.inputToConfirmPasswordTextbox("123");

		System.out.println("Register Page - Step 03: Click to Register button ");
		registerPage.clickToRegisterButton();

		System.out.println("Register Page - Step 04: Verify error message displayed");
		Assert.assertEquals(registerPage.getErrorMessageAtPasswordTextbox(),
				"Password must meet the following rules:\nmust have at least 6 characters");
	}

	@Test
	public void TC_06_Register_Invalid_Confirm_Password() {

		System.out.println("Home Page - Step 01: Click to Register link ");
		homePage.clickToRegisterLink();

		System.out.println("Home Page - Step 02: Input to required field ");
		registerPage.inputToFirstnameTextbox(firstName);
		registerPage.inputToLastnameTextbox(lastName);
		registerPage.inputToEmailTextbox(emailAddress);
		registerPage.inputToPasswordTextbox(password);
		registerPage.inputToConfirmPasswordTextbox("123");

		System.out.println("Register Page - Step 03: Click to Register button ");
		registerPage.clickToRegisterButton();

		System.out.println("Register Page - Step 04: Verify error message displayed");
		Assert.assertEquals(registerPage.getErrorMessageAtConfirmPasswordTextbox(),
				"The password and confirmation password do not match.");

	}

	public int fakeNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	@AfterClass
	public void afterClass() {
//		driver.quit();
	}

}
