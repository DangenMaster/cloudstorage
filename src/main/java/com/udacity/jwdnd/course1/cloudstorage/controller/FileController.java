package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping()
    public RedirectView insertFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication,
                                   @ModelAttribute File file, RedirectAttributes attr) throws IOException {
        String fileError = null;
        if (!fileService.isFilenameExist(fileUpload.getOriginalFilename())) {
            fileError = "The filename already exists";
        } else {
            final User loggedInUser = userService.getUser(authentication.getName());
            file.setUserId(loggedInUser.getUserId());
            file.setFileData(fileUpload.getBytes());
            file.setFilename(fileUpload.getOriginalFilename());
            file.setFileSize(String.valueOf(fileUpload.getSize()));
            file.setContentType(fileUpload.getContentType());
            fileService.insertFile(file);
        }

        if (fileError == null) {
            attr.addAttribute("h", "fileSuccess");
        } else {
            attr.addAttribute("h", "fileError");
        }

        return new RedirectView("/home");
    }

    @GetMapping("/delete/{fileId}")
    public RedirectView deleteFile(@PathVariable int fileId, Model model) {
        fileService.deleteFile(fileId);
        return new RedirectView("/home");
    }
}
