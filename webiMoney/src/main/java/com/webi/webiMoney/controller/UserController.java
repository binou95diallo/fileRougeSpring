package com.webi.webiMoney.controller;

import com.webi.webiMoney.model.User;
import com.webi.webiMoney.repository.UserRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {
     @Autowired
    private UserRepository userRepository;
    @GetMapping(value = "/liste")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> liste(){

        return userRepository.findAll();
    }
    @Autowired
    PasswordEncoder encoder;
    @PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User add(@RequestBody(required = false) User u){
                u.setPassword((encoder.encode(u.getPassword())));
        return userRepository.save(u);
    }
    @PutMapping(value = "/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void update(@PathVariable int id, @RequestBody(required = false) User u){
        Optional<User> user;
        user=userRepository.findById(id);
        if(user!=null){
            u.setId(id);
            u.setPassword((encoder.encode(u.getPassword())));
        }
        else{
            System.out.println("utilisateur inconnu");
        }

         userRepository.save(u);
    }
    /*
    @PutMapping(value = "/update/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void update(@PathVariable int id, @RequestPayload() User u){
        Optional<User> user;
        user=userRepository.findById(id);
        if(user!=null){

            u.setId(id);
            u.setPassword((encoder.encode(u.getPassword())));
        }
        else{
            System.out.println("utilisateur inconnu");
        }

         userRepository.save(u);
    }
     */

}
