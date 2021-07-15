package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NoteService noteService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping()
    public String homePage(Authentication authentication,
                           @RequestAttribute(value = "h", required = false) String home,
                           Model model) {
        final User loggedInUser = userService.getUser(authentication.getName());

        List<File> files = fileService.getFiles(loggedInUser.getUserId());
        List<Note> notes = noteService.getNotes(loggedInUser.getUserId());

        model.addAttribute("files", files);
        model.addAttribute("notes", notes);

        return "home";
    }
}
