package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(linkText = "Back to Login")
    private WebElement backToLogin;

    public SignUpPage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillSignUpForm(String firstName, String lastName, String username, String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
    }

    public void submitSignUpForm() {
        submitButton.click();
    }

    public boolean isSuccessMessageDisplayed() {
        return successMessage.isDisplayed();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public void clickBackToLoginButton() {
        backToLogin.click();
    }
}
