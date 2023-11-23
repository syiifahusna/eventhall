package com.example.eventhall.controller;

import com.example.eventhall.entity.User;
import com.example.eventhall.service.UserRegisterService;
import com.example.eventhall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/")
public class PublicController {
    @Value("${app.public.dir}")
    private String publicDir;

    private UserRegisterService userRegisterService;


    @Autowired
    public PublicController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

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

    @PostMapping("/register")
    public String register(@RequestParam("name") String name,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           @RequestParam("tel") String tel,
                           @RequestParam("birthdate") String birthdate,
                           Model model
    ){

        LocalDate localDateBirthdate = LocalDate.parse(birthdate);
        User  registerRequest = new User(name,username,password,email,tel,localDateBirthdate);

        String message = userRegisterService.registerUser(registerRequest);
        model.addAttribute("message",message);

        return publicDir+"/register";
    }

    @GetMapping("/login")
    public String login(){
        return publicDir+"/login";
    }

    @GetMapping("/logoutsuccess")
    public String logout(){
        return publicDir+"/logout";
    }

    @GetMapping("/forgotpassword")
    public String forgotpassword(){
        return publicDir+"/forgotpassword";
    }


}
