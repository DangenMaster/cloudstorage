package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping()
    public String insertAndUpdateCredential(Authentication authentication, @ModelAttribute Credential credential) {
        if (credential.getCredentialId() == null) {
            final User loggedInUser = userService.getUser(authentication.getName());
            credential.setUserId(loggedInUser.getUserId());
            credentialService.insertCredential(credential);
        } else { // update
            credentialService.updateCredential(credential);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteNote(@PathVariable int credentialId) {
        credentialService.deleteCredential(credentialId);
        return "redirect:/home";
    }

}
