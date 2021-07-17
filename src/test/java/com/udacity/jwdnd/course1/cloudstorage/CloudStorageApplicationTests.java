package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static ChromeDriver driver;

	public String baseURL;
	private final String firstName = "Apoorv";
	private final String lastName = "Gupta";
	public final String username = "dangenmaster";
	public final String password = "qwerty@123";

	@BeforeAll
	public static void beforeAll() {
		System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\static\\drivers\\chromedriver.exe");
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() throws InterruptedException {
		if (this.driver != null) {
			Thread.sleep(2000);
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void displayLoginPage() {
		driver.get(baseURL + "/login");

		//Check that user has been redirected to login page
		new WebDriverWait(driver,5).until(ExpectedConditions.titleIs("Login"));

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void  displaySignUpPage() {
		driver.get(baseURL + "/signup");

		//Check that user has been redirected to signup page
		new WebDriverWait(driver,5).until(ExpectedConditions.titleIs("Sign Up"));

		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(3)
	public void  unauthorizedAccessRestrictions() throws InterruptedException {
		driver.get(baseURL + "/home");

		//Check that user has been redirected to login page
		new WebDriverWait(driver,5).until(ExpectedConditions.titleIs("Login"));

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void  invalidCredentials() throws InterruptedException {
		driver.get(baseURL + "/login");

		//Check that user has been redirected to login page
		new WebDriverWait(driver,5).until(ExpectedConditions.titleIs("Login"));

		Assertions.assertEquals("Login", driver.getTitle());

		//Initialize web driver
		LoginPage loginPage = new LoginPage(driver);


		loginPage.fillLoginForm(username, password);
		loginPage.submitLoginForm();

		Thread.sleep(2000);

		Assertions.assertEquals(true, loginPage.isErrorMessageDisplayed());
	}

	@Test
	@Order(5)
	public void  userSignUpAndUsernameAvailability() throws InterruptedException {
		driver.get(baseURL + "/signup");

		//Check that user has been redirected to signup page
		new WebDriverWait(driver,5).until(ExpectedConditions.titleIs("Sign Up"));

		Assertions.assertEquals("Sign Up", driver.getTitle());

		//Initialize web driver
		SignUpPage signUpPage = new SignUpPage(driver);

		for (int i = 0; i < 2; i++) {
			signUpPage.fillSignUpForm(firstName, lastName, username, password);
			signUpPage.submitSignUpForm();

			Thread.sleep(2000);

			//Check that user has been redirected to signup page
			new WebDriverWait(driver,5).until(ExpectedConditions.titleIs("Sign Up"));
			Assertions.assertEquals("Sign Up", driver.getTitle());

			if (i == 0) { // SignUp successfully as username is available
				Assertions.assertEquals(true, signUpPage.isSuccessMessageDisplayed());
			} else { // username is not available
				Assertions.assertEquals(true, signUpPage.isErrorMessageDisplayed());
			}
		}
	}

	@Test
	@Order(6)
	public void userLoginAndLogout() throws InterruptedException {
		driver.get(baseURL + "/login");

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Login"));
		Assertions.assertEquals("Login", driver.getTitle());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.fillLoginForm(username, password);
		loginPage.submitLoginForm();

		Thread.sleep(2000);

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.clickLogoutButton();

		Thread.sleep(2000);

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(7)
	public void userLoginAndAllUploadFileCases() throws InterruptedException {
		driver.get(baseURL + "/login");

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Login"));
		Assertions.assertEquals("Login", driver.getTitle());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.fillLoginForm(username, password);
		loginPage.submitLoginForm();

		Thread.sleep(2000);

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		FilesPage filesPage = new FilesPage(driver);

		// No file selected
		filesPage.uploadFile();

		Thread.sleep(2000);
		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, filesPage.isErrorMessageDisplayed());
		Assertions.assertEquals(false, filesPage.isFirstFileListElementDisplayed());

		// New file upload
		filesPage.chooseFile();
		filesPage.uploadFile();

		Thread.sleep(2000);
		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, filesPage.isSuccessMessageDisplayed());
		Assertions.assertEquals(true, filesPage.isFirstFileListElementDisplayed());

		// Existing file upload
		filesPage.chooseFile();
		filesPage.uploadFile();

		Thread.sleep(2000);
		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, filesPage.isErrorMessageDisplayed());

		// Max file size exceeds of 1MB
		filesPage.chooseLargeFile();
		filesPage.uploadFile();

		Thread.sleep(2000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, filesPage.isErrorMessageDisplayed());

		// download file (need to verify manually if files gets downloaded)
		filesPage.clickViewFirstFileListElement();

		Thread.sleep(2000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());

		// delete file
		filesPage.clickDeleteFirstFileListElement();

		Thread.sleep(2000);
		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, filesPage.isSuccessMessageDisplayed());
		Assertions.assertEquals(false, filesPage.isFirstFileListElementDisplayed());
	}
}