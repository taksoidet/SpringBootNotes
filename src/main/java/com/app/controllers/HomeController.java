package com.app.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getHome() {
        return "login";
    }

    @PostMapping
    public String getLogin(@RequestParam(name = "username") String userName,
                           @RequestParam(name = "password") String userPass) {

        return "redirect:/main";
    }


}
