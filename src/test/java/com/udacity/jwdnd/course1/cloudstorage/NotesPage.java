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

    @FindBy(id = "note-1")
    private WebElement firstNoteListElement;

    @FindBy(id = "edit-note-1")
    private WebElement editFirstNoteButton;

    @FindBy(id = "delete-note-1")
    private WebElement deleteFirstNoteButton;

    @FindBy(id = "success-message")
    private WebElement successMessage;

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
