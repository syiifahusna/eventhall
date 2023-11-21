package com.example.eventhall.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PublicController {

    @Value("${app.public.dir}")
    private String publicDir;

    @GetMapping({"/","/home"})
    public String index(){
        return publicDir+"/index";
    }

    @GetMapping("/about")
    public String about(){
        return publicDir+"/about";
    }

    @GetMapping("/location")
    public String location(){
        return publicDir+"/location";
    }

    @GetMapping("/news")
    public String news(){
        return publicDir+"/news";
    }

    @GetMapping("/register")
    public String register(){
        return publicDir+"/register";
    }

    @GetMapping("/login")
    public String login(){
        return publicDir+"/login";
    }


}
