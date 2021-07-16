package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private UserService userService;
    private CredentialService credentialService;

    static private Logger logger = LoggerFactory.getLogger(CredentialController.class);

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping()
    public String insertAndUpdateCredential(Authentication authentication, @ModelAttribute Credential credential,
                                            RedirectAttributes redirectAttributes) {
        try {
            if (credential.getCredentialId() == null) {
                final User loggedInUser = userService.getUser(authentication.getName());
                credential.setUserId(loggedInUser.getUserId());
                credentialService.insertCredential(credential);
                redirectAttributes.addAttribute("success", true);
                redirectAttributes.addAttribute("message", "Credentials has been saved!");
            } else { // update
                credentialService.updateCredential(credential);
                redirectAttributes.addAttribute("success", true);
                redirectAttributes.addAttribute("message", "Credentials has been updated!");
            }
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "Your changes were not saved. Please try again.");
            logger.error("Error: insertAndUpdateCredential", ex.getMessage());
        }

        return "redirect:/home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable int credentialId, RedirectAttributes redirectAttributes) {
        try {
            credentialService.deleteCredential(credentialId);
            redirectAttributes.addAttribute("success", true);
            redirectAttributes.addAttribute("message", "Credentials has been updated!");
        } catch (Exception ex) {
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "Your changes were not done. Please try again.");
            logger.error("Error: deleteCredential", ex.getMessage());
        }

        return "redirect:/home";
    }

}
