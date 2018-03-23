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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Main web controller for the SpringBoot_BullHorn project
 *
 * @author amp
 */
@Controller
public class MainController {

    @Autowired
    MessageRepository messageRepository;

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

    @RequestMapping("/newMessage")
    public String newMessage(Model model) {
        model.addAttribute("message", new Message());
        return "newMessage";
    }

    /**
     * Given Message object from form, send/post the message
     *
     * @return
     */
    @PostMapping("/postMessage")
    public String postMessage(@Valid Message message, BindingResult result) {
        if (result.hasErrors()) {
            return "newMessage";
        }
        message.setFromUser(getLoginUserId());
        message.setPostedDate(getFormattedNow());
        messageRepository.save(message); // future: trap any errors

        // after the message is sent, display all message again
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
