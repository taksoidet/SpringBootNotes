package com.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
public class RegistrationController {


    @GetMapping
    public String getRegistration() {
        return "registration";
    }

    @PostMapping
    public String login(@RequestParam(name = "name") String userName, @RequestParam(name = "password") String userPass) {

        return "redirect:/login";
    }

}
