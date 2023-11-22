package com.example.eventhall.service;

import com.example.eventhall.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserRegisterService {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private Pattern emailValidate;

    @Autowired
    public UserRegisterService(UserService userService, PasswordEncoder passwordEncoder, Pattern emailValidate) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailValidate = emailValidate;
    }

    public String registerUser(User registerRequest){

        //validation
        if(!emailValidate.matcher(registerRequest.getEmail()).matches()){
            return "Register fail: email is not valid";
        }

        if(userService.isEmailExist(registerRequest.getEmail())){
            return "Register fail: email already exist";
        }

        if(userService.isUsernameExist(registerRequest.getUsername())){
            return "Register fail: username is already exist";
        }

        //validate tel

        //validate password length

        //validate name length

        //validate birthday *must be 18yo older

        //convert
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        User user = new User(registerRequest.getName(),
                registerRequest.getUsername(),
                encodedPassword,
                registerRequest.getEmail(),
                registerRequest.getTel(),
                registerRequest.getBirthDate(),
                true,
                true,
                true,
                true
        );

        userService.insertUser(user);
        return "Register success";
    }


}
