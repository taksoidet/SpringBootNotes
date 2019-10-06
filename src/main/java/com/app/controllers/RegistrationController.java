package com.app.controllers;

import com.app.dao.NoteFolderDAO;
import com.app.dao.UserDAO;
import com.app.models.NoteFolder;
import com.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserDAO userDAO;
    private NoteFolderDAO noteFolderDAO;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public RegistrationController(UserDAO userDAO, NoteFolderDAO noteFolderDAO, BCryptPasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.noteFolderDAO = noteFolderDAO;
        this.encoder = encoder;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("user", new User());
    }


    @GetMapping
    public String getRegistration() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/main";
        }
        return "registration";
    }

    @PostMapping
    public String registration(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "registration";
        } else if (userDAO.findByName(user.getName()) != null) {
            model.addAttribute("error", "This username is exist");
            return "registration";
        } else if (!user.getPassword().equals(user.getConfPassword())) {
            model.addAttribute("error", "Passwords doesn't match");
            return "registration";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.save(user);
        NoteFolder noteFolder = new NoteFolder();
        noteFolder.setTitle("New Folder");
        noteFolder.setUser(user);
        noteFolderDAO.save(noteFolder);
        redirectAttributes.addFlashAttribute("success_message", "Account successfully created");
        return "redirect:/";
    }

}
