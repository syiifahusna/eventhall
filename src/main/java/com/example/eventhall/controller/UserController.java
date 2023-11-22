package com.example.eventhall.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/u")
public class UserController {

    @Value("${app.user.dir}")
    private String userDir;

    @GetMapping({"/home","/",""})
    public String home(){
        return userDir+"/home";
    }

    @GetMapping("/hall")
    public String hall(){
        return userDir+"/hall";
    }
}
