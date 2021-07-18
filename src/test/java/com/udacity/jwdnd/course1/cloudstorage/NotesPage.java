package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesPage {
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "note-model-btn")
    private WebElement noteModelButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "submit-note")
    private WebElement submitNoteButton;

    @FindBy(id = "success-message")
    private WebElement successMessage;

    @FindBy(className = "notes")
    private WebElement firstNoteListElement;

    @FindBy(className = "edit-note")
    private WebElement editFirstNoteButton;

    @FindBy(className = "delete-note")
    private WebElement deleteFirstNoteButton;

    public NotesPage(ChromeDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickNotesTab() {
        notesTab.click();
    }

    public void clickNoteModelButton() {
        noteModelButton.click();
    }

    public String getNoteTitle() {
        return noteTitle.getAttribute("value");
    }

    public String getNoteDescription() {
        return noteDescription.getAttribute("value");
    }

    public void fillNoteModel(String noteTitle, String noteDescription) {
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
    }

    public void submitNote() {
        submitNoteButton.click();
    }

    public boolean isFirstNoteListElementDisplayed() {
        try {
            return firstNoteListElement.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public boolean isSuccessMessageDisplayed() {
        return successMessage.isDisplayed();
    }

    public void clickEditFirstNoteButton() {
        editFirstNoteButton.click();
    }

    public void clickDeleteFirstNoteButton() {
        deleteFirstNoteButton.click();
    }
}
