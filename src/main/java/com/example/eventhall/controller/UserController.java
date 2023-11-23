package com.example.eventhall.controller;

import com.example.eventhall.entity.User;
import com.example.eventhall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/u")
public class UserController {

    private UserService userService;

    @Value("${app.user.dir}")
    private String userDir;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/home","/",""})
    public String home(Model model,Authentication authentication){
        //cast principal to user object
        User authUser = (User) authentication.getPrincipal();

        model.addAttribute("name",authUser.getName());
        return userDir+"/home";
    }

    @GetMapping("/hall")
    public String hall(){
        return userDir+"/hall";
    }

    @GetMapping("/about")
    public String about(){
        return userDir+"/about";
    }

    @GetMapping("/location")
    public String location(){
        return userDir+"/location";
    }

    @GetMapping("/news")
    public String news(){
        return userDir+"/news";
    }

}
