package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Main web controller for the SpringBoot_BullHorn project
 *
 * @author amp
 */
@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Service that allows us to upload and store images.
     */
    @Autowired
    private CloudinaryConfig cloudinaryConfig;

    @RequestMapping("/")
    public String rootPage(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "rootPage";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/message")
    public String newMessage(Model model) {
        model.addAttribute("message", new Message());
        return "message";
    }

    /**
     * Given Message object from form, send/post the message
     *
     * @return
     */
    @PostMapping("/message")
    public String postMessage(@Valid Message message, BindingResult result,
                              @RequestParam("imageFile") MultipartFile imageFile
    ) {
        if (result.hasErrors()) {
            return "redirect:/message";
        }

        // upload the image and get its URL
        message.setImageUrl(null);
        if (!imageFile.isEmpty()) {
            try {
                Map uploadResult = cloudinaryConfig.upload(imageFile.getBytes(),
                        com.cloudinary.utils.ObjectUtils.asMap("resourcetype", "auto"));
                message.setImageUrl(uploadResult.get("url").toString());
            } catch (IOException e) {
                // future: put message in the screen so user can see it
                e.printStackTrace();
                return "redirect:/message";
            }
        }

        // set other defaults
        message.setFromUser(getLoginUserId());
        message.setPostedDate(getFormattedNow());

        // save/send message
        messageRepository.save(message); // future: trap any errors

        // after the message is sent, display all messages again
        return "redirect:/";
    }

    /**
     * Get user ID (user name for now) for the currently logged in user
     *
     * @return null==no currently logged in user, else the user name (example:
     * "mary")
     */
    private static String getLoginUserId() {
        String strUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            strUser = authentication.getName();
        }
        return strUser;
    }

    /**
     * Get current date-time as a formatted timestamp.
     *
     * @return as yyyy-MM-dd HH:mm:ss format
     */
    private static String getFormattedNow() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
