package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {
    private UserService userService;
    private NoteService noteService;

    static private Logger logger = LoggerFactory.getLogger(NoteController.class);

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping()
    public String createAndUpdateNote(@ModelAttribute Note note, Authentication authentication,
                                      RedirectAttributes redirectAttributes) {
        try {
            if (note.getNoteId() == null && note.getNoteTitle() != "") {
                final User loggedInUser = userService.getUser(authentication.getName());
                note.setUserId(loggedInUser.getUserId());
                noteService.createNote(note);
                redirectAttributes.addAttribute("success", true);
                redirectAttributes.addAttribute("message", "Note created!");
            } else if (note.getNoteId() != null && note.getNoteTitle() != "") { // update note
                noteService.updateNote(note);
                redirectAttributes.addAttribute("success", true);
                redirectAttributes.addAttribute("message", "Note updated!");
            } else {
                redirectAttributes.addAttribute("error", true);
                redirectAttributes.addAttribute("message", "Your changes were not saved. Please check if title is entered.");
            }
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "Your changes were not saved. Please try again.");
            logger.error("Error: createAndUpdateNote", ex.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable int noteId, RedirectAttributes redirectAttributes) {
        try {
            noteService.deleteNote(noteId);
            redirectAttributes.addAttribute("success", true);
            redirectAttributes.addAttribute("message", "Note deleted!");
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "Your changes were not done. Please try again.");
            logger.error("Error: deleteNote", ex.getMessage());
        }

        return "redirect:/home";
    }

}
