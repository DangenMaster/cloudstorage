package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FilesPage {
    private static final String workingDir = System.getProperty("user.dir");

    @FindBy(id = "fileUpload")
    private WebElement inputFileUpload;

    @FindBy(id = "fileUploadButton")
    private WebElement fileUploadButton;

    @FindBy(id = "success-message")
    private WebElement successMessage;

    @FindBy(id = "error-message")
    private WebElement errorMessage;

    @FindBy(className = "files")
    private WebElement firstFileListElement;

    @FindBy(className = "view-file")
    private WebElement viewFirstFileListElement;

    @FindBy(className = "delete-file")
    private WebElement deleteFirstFileListElement;

    public FilesPage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void chooseFile() {
        // put absolute path of image
        inputFileUpload.sendKeys(workingDir + "\\src\\main\\resources\\static\\images\\dummy.png");
    }

    public void chooseLargeFile() {
        // put absolute path of image
        inputFileUpload.sendKeys(workingDir + "\\src\\main\\resources\\static\\images\\dummyLargeFile.jpg");
    }

    public void uploadFile() {
        fileUploadButton.click();
    }

    public boolean isSuccessMessageDisplayed() {
        return successMessage.isDisplayed();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public boolean isFirstFileListElementDisplayed() {
        try {
            return firstFileListElement.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public void clickViewFirstFileListElement() {
        viewFirstFileListElement.click();
    }

    public void clickDeleteFirstFileListElement() {
        deleteFirstFileListElement.click();
    }
}
