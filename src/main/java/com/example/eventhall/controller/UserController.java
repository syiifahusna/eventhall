package com.example.eventhall.controller;

import com.example.eventhall.entity.Hall;
import com.example.eventhall.entity.User;
import com.example.eventhall.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/u")
public class UserController {

    @Value("${app.user.dir}")
    private String userDir;

    private final HallService hallService;
    @Autowired
    public UserController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping({"/home","/",""})
    public String home(Model model,Authentication authentication){
        //cast principal to user object
        User authUser = (User) authentication.getPrincipal();
        model.addAttribute("name",authUser.getName());
        return userDir+"/home";
    }

    @GetMapping("/hall")
    public String hall(Model model){
        List<Hall> halls = hallService.getHallsWithoutManager();
        if(!halls.isEmpty()){
            model.addAttribute("halls",halls);
        }else{
            model.addAttribute("message","No hall Available");
        }
        return userDir+"/hall";
    }

    @GetMapping("/about")
    public String about(){
        return userDir+"/about";
    }

    @GetMapping("/news")
    public String news(){
        return userDir+"/news";
    }

}
