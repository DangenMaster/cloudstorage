package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping()
    public String createAndUpdateNote(@ModelAttribute Note note, Authentication authentication) {
        if (note.getNoteId() == null) {
            final User loggedInUser = userService.getUser(authentication.getName());
            note.setUserId(loggedInUser.getUserId());
            noteService.createNote(note);
        } else { // update note
            noteService.updateNote(note);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable int noteId) {
        noteService.deleteNote(noteId);
        return "redirect:/home";
    }

}
