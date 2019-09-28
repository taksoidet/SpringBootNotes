package com.app.controllers;

import com.app.dao.UserDAO;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserDAO userDAO;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public RegistrationController(UserDAO userDAO, BCryptPasswordEncoder encoder){
        this.userDAO=userDAO;
        this.encoder=encoder;
    }




    @GetMapping
    public String getRegistration(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/main";
        }
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String registration(@Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "registration";
        }
        if(userDAO.findByName(user.getName())!=null){
            model.addAttribute("error", "This username is exist");
            return "registration";
        }
        if(!user.getPassword().equals(user.getConfPassword())){
            model.addAttribute("error", "Passwords doesn't match");
            return "registration";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.save(user);

        return "redirect:/";
    }

}
