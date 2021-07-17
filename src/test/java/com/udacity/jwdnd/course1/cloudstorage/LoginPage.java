package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id = "logout-msg")
    private WebElement logoutMessage;

    @FindBy(linkText = "Click here to sign up")
    private WebElement signUpPageLink;

    public LoginPage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillLoginForm(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
    }

    public void submitLoginForm() {
        submitButton.click();
    }

    public void goToSignUpPage() {
        signUpPageLink.click();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public boolean isLogoutMessageDisplayed() {
        return logoutMessage.isDisplayed();
    }
}
