package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileController {
    private UserService userService;
    private FileService fileService;

    static private Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/home/file")
    public String insertFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication,
                             @ModelAttribute File file, RedirectAttributes redirectAttributes) throws IOException {
        try {
            if (fileUpload.isEmpty()) {
                redirectAttributes.addAttribute("error", true);
                redirectAttributes.addAttribute("message","File is not selected. Please select a file to upload.");
                return "redirect:/home";
            }

            if (fileUpload.getSize() > 1000000) { // Max file size is 1MB
                throw new MaxUploadSizeExceededException(fileUpload.getSize());
            }

            if (!fileService.isFilenameExist(fileUpload.getOriginalFilename())) {
                redirectAttributes.addAttribute("error", true);
                redirectAttributes.addAttribute("message", "The file with the given name already exists.");
                return "redirect:/home";
            }

            final User loggedInUser = userService.getUser(authentication.getName());
            file.setUserId(loggedInUser.getUserId());
            file.setFileData(fileUpload.getBytes());
            file.setFilename(fileUpload.getOriginalFilename());
            file.setFileSize(String.valueOf(fileUpload.getSize()));
            file.setContentType(fileUpload.getContentType());
            fileService.insertFile(file);
            redirectAttributes.addAttribute("success", true);
            redirectAttributes.addAttribute("message", "File uploaded!");

        } catch (IOException ex) {
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "Your changes were not saved. Please try again.");
            logger.error("Error: insertFile", ex.getMessage());
        }

        return "redirect:/home";
    }

    @GetMapping("/home/file/view/{fileId}")
    public ResponseEntity viewFile(@PathVariable("fileId") Integer fileId) {
        File file = fileService.getFile(fileId);
        String filename = file.getFilename();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                /*replace "attachment" with "inline" if you want another browser tab to be opened to view file
                instead of directly downloading files.*/
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\"")
                .body(file.getFileData());
    }

    @GetMapping("/home/file/delete/{fileId}")
    public String deleteFile(@PathVariable int fileId, RedirectAttributes redirectAttributes) {
        try {
            fileService.deleteFile(fileId);
            redirectAttributes.addAttribute("success", true);
            redirectAttributes.addAttribute("message", "File deleted!");
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "Your changes were not done. Please try again.");
            logger.error("Error: deleteFile", ex.getMessage());
        }

        return "redirect:/home";
    }
}
