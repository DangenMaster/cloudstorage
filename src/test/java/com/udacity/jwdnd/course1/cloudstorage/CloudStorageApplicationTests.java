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
	private final String username = "dangenmaster";
	private final String password = "qwerty@123";
	private final String noteTitle = "Dummy note title";
	private final String noteDescription = "Dummy note description";

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

		Thread.sleep(2000);

		//Initialize web driver
		SignUpPage signUpPage = new SignUpPage(driver);

		for (int i = 0; i < 2; i++) {
			signUpPage.fillSignUpForm(firstName, lastName, username, password);

			Thread.sleep(2000);

			signUpPage.submitSignUpForm();

			Thread.sleep(2000);

			//Check that user has been redirected to signup page
			new WebDriverWait(driver,5).until(ExpectedConditions.titleIs("Sign Up"));
			Assertions.assertEquals("Sign Up", driver.getTitle());

			Thread.sleep(2000);

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

		Thread.sleep(2000);
		// New file upload
		filesPage.chooseFile();
		filesPage.uploadFile();

		Thread.sleep(2000);
		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, filesPage.isSuccessMessageDisplayed());
		Assertions.assertEquals(true, filesPage.isFirstFileListElementDisplayed());

		Thread.sleep(2000);
		// Existing file upload
		filesPage.chooseFile();
		filesPage.uploadFile();

		Thread.sleep(2000);
		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, filesPage.isErrorMessageDisplayed());

		Thread.sleep(2000);
		// Max file size exceeds of 1MB
		filesPage.chooseLargeFile();
		filesPage.uploadFile();

		Thread.sleep(2000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, filesPage.isErrorMessageDisplayed());

		Thread.sleep(2000);
		// download file (need to verify manually if files gets downloaded)
		filesPage.clickViewFirstFileListElement();

		Thread.sleep(2000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());

		Thread.sleep(2000);
		// delete file
		filesPage.clickDeleteFirstFileListElement();

		Thread.sleep(2000);
		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, filesPage.isSuccessMessageDisplayed());
		Assertions.assertEquals(false, filesPage.isFirstFileListElementDisplayed());
	}

	@Test
	@Order(8)
	public void allUploadNoteCases() throws InterruptedException {
		driver.get(baseURL + "/login");

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Login"));
		Assertions.assertEquals("Login", driver.getTitle());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.fillLoginForm(username, password);
		loginPage.submitLoginForm();

		Thread.sleep(2000);

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		NotesPage notesPage = new NotesPage(driver);
		notesPage.clickNotesTab();

		Thread.sleep(2000);

		// Submit note
		notesPage.clickNoteModelButton();

		Thread.sleep(2000);

		notesPage.fillNoteModel(noteTitle, noteDescription);
		notesPage.submitNote();

		Thread.sleep(2000);

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, notesPage.isSuccessMessageDisplayed());

		Thread.sleep(2000);

		notesPage.clickNotesTab();

		Thread.sleep(2000);

		Assertions.assertEquals(true, notesPage.isFirstNoteListElementDisplayed());

		Thread.sleep(2000);

		// Edit note
		notesPage.clickEditFirstNoteButton();

		Thread.sleep(2000);

		Assertions.assertEquals(noteTitle, notesPage.getNoteTitle());
		Assertions.assertEquals(noteDescription, notesPage.getNoteDescription());

		Thread.sleep(2000);

		notesPage.fillNoteModel(" new", " new");
		notesPage.submitNote();

		Thread.sleep(2000);

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, notesPage.isSuccessMessageDisplayed());

		Thread.sleep(2000);

		notesPage.clickNotesTab();

		Thread.sleep(2000);

		Assertions.assertEquals(true, notesPage.isFirstNoteListElementDisplayed());

		Thread.sleep(2000);

		// Delete note
		notesPage.clickDeleteFirstNoteButton();

		Thread.sleep(2000);

		new WebDriverWait(driver, 5).until(ExpectedConditions.titleIs("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(true, notesPage.isSuccessMessageDisplayed());

		Thread.sleep(2000);

		notesPage.clickNotesTab();

		Thread.sleep(2000);

		Assertions.assertEquals(false, notesPage.isFirstNoteListElementDisplayed());
	}
}