package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsPage {
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "credential-model-btn")
    private WebElement credentialModelButton;

    @FindBy(id = "credential-url")
    private WebElement inputCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement inputCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement inputCredentialPassword;

    @FindBy(id = "submit-credential-btn")
    private WebElement submitCredential;

    @FindBy(id = "success-message")
    private WebElement successMessage;

    @FindBy(className = "credentials")
    private WebElement firstCredentialListElement;

    @FindBy(className = "edit-credential")
    private WebElement editFirstCredentialButton;

    @FindBy(className = "delete-credential")
    private WebElement deleteFirstCredentialButton;

    public CredentialsPage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickCredentialsTab() {
        credentialsTab.click();
    }

    public void clickCredentialModelButton() {
        credentialModelButton.click();
    }

    public String getCredentialUrl() {
        return inputCredentialUrl.getAttribute("value");
    }

    public String getCredentialUsername() {
        return inputCredentialUsername.getAttribute("value");
    }

    public String getCredentialPassword() {
        return inputCredentialPassword.getAttribute("value");
    }

    public void fillCredentialModel(String credentialUrl, String credentialUsername, String credentialPassword) {
        this.inputCredentialUrl.sendKeys(credentialUrl);
        this.inputCredentialUsername.sendKeys(credentialUsername);
        this.inputCredentialPassword.sendKeys(credentialPassword);
    }

    public void submitCredential() {
        submitCredential.click();
    }

    public boolean isFirstCredentialListElementDisplayed() {
        try {
            return firstCredentialListElement.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public boolean isSuccessMessageDisplayed() {
        return successMessage.isDisplayed();
    }

    public void clickEditFirstCredentialButton() {
        editFirstCredentialButton.click();
    }

    public void clickDeleteFirstCredentialButton() {
        deleteFirstCredentialButton.click();
    }
}
