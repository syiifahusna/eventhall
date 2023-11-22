package com.example.eventhall.service;

import com.example.eventhall.entity.User;
import com.example.eventhall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optinalUser= userRepository.findUserByUsername(username);

        if(optinalUser.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        return optinalUser.get();
    }

    public Boolean isUsernameExist(String username){
        Optional<User> optinalUser= userRepository.findUserByUsername(username);

        if(optinalUser.isEmpty()){
            return false;
        }

        return true;
    }

    public Boolean isEmailExist(String email){
        Optional<User> optinalUser= userRepository.findUserByEmail(email);

        if(optinalUser.isEmpty()){
            return false;
        }

        return true;
    }

    public Boolean insertUser(User user){
        Optional<User> optinalUser = userRepository.findUserByUsername(user.getUsername());
        if(optinalUser.isPresent()){
            return false;
        }
        userRepository.save(user);
        return true;
    }

}
