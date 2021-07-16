package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileExceptionAdvice {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exception,
                                         RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("error", true);
        redirectAttributes.addAttribute("message", "The max file size exceeds 1MB.");
        return "redirect:/home";
    }
}
