package com.example.eventhall.service;

import com.example.eventhall.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserRegisterService {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private Pattern emailValidate;
    private Pattern telNoValidate;

    @Autowired
    public UserRegisterService(UserService userService, PasswordEncoder passwordEncoder, @Qualifier("emailPattern") Pattern emailValidate, @Qualifier("telNoPattern") Pattern telNoValidate) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailValidate = emailValidate;
        this.telNoValidate = telNoValidate;
    }

    public String registerUser(User registerRequest){

        //validation
        if(!emailValidate.matcher(registerRequest.getEmail()).matches()){
            return "Register fail: email is not valid";
        }

        if(userService.isEmailExist(registerRequest.getEmail())){
            return "Register fail: email already exist";
        }

        if(registerRequest.getUsername().length()<5){
            return "Register fail: username is too short";
        }

        if(userService.isUsernameExist(registerRequest.getUsername())){
            return "Register fail: username is already exist";
        }

        if(registerRequest.getPassword().length()<5){
            return "Register fail: password is too short";
        }

        if(registerRequest.getName().length()<5){
            return "Register fail: name is too short";
        }

        if(registerRequest.getAge() < 18){
            return "Register fail: you are underage";
        }

        if(!telNoValidate.matcher(registerRequest.getTel()).matches()){
            return "Register fail: tel no is not valid";
        }

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

        if(userService.insertUser(user)){
            return "Register success";
        }

        return "Register fail: error occour";

    }


}
