package com.example.eventhall.configuration;

import com.example.eventhall.entity.Admin;
import com.example.eventhall.entity.Hall;
import com.example.eventhall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {

        if(adminRepository.count() == 0){

            Admin a1 = new Admin("Ahri Fox", "ahri", "user111", "ahri@mail.com");
            Admin a2 = new Admin("Aphelios Alune", "phel", "user111", "phel@mail.com");
            Admin a3 = new Admin("Azir Sand", "azir", "user111", "azir@mail.com");
            Admin a4 = new Admin("Ryze Rune", "ryze", "user111", "ryze@mail.com");
            Admin a5 = new Admin("Settrigh Boss", "boss", "user111", "boss@mail.com");
            List<Admin> admins = new ArrayList<>(Arrays.asList(a1,a2,a3,a4,a5));
            adminRepository.saveAll(admins);

            List<Admin> admins1 = new ArrayList<>(Arrays.asList(a1,a3,a5));
            List<Admin> admins2 = new ArrayList<>(Arrays.asList(a2,a4,a5));
            List<Admin> admins3 = new ArrayList<>(Arrays.asList(a1,a2));
            List<Admin> admins4 = new ArrayList<>(Arrays.asList(a2,a3,a4));

            if (hallRepository.count()==0){
                Hall h1 = new Hall("Shuriman Hall","Ixtal","10,000sqft",2000,true,admins1);
                Hall h2 = new Hall("Noxii Hall","Noxus","5,000sqft",1000,true,admins2);
                Hall h3 = new Hall("Demacia Heart Hall","Demacia","15,000sqft",5000,true,admins3);
                Hall h4 = new Hall("Ionian Great Hall","Ionia","15,000sqft",5000,true,admins4);
                List<Hall> halls = new ArrayList<>(Arrays.asList(h1,h2,h3,h4));
                hallRepository.saveAll(halls);

            }

        }
    }
}
