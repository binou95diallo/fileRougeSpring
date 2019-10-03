package com.webi.webiMoney.controller;

import com.webi.webiMoney.model.Partenaire;
import com.webi.webiMoney.model.Role;
import com.webi.webiMoney.model.RoleName;
import com.webi.webiMoney.model.User;
import com.webi.webiMoney.repository.RoleRepository;
import com.webi.webiMoney.repository.UserRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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
    @Autowired
    RoleRepository roleRepository;
    @PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User add(@RequestBody(required = false) User u, HttpServletRequest request){
        Role userRole;
                u.setPassword((encoder.encode(u.getPassword())));
        String caissier=request.getUserPrincipal().getName();//récupére le username de l'utilisateur connecté
        User user=userRepository.userByUsername(caissier);
       /* Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getDetails());*/
       u.setPartenaire(user.getPartenaire());
                if(u.getProfile().equalsIgnoreCase("admin")){
                    userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new ApplicationContextException("User Role not set."));
                    u.setRoles(Collections.singleton(userRole));
                }
                else if (u.getProfile().equalsIgnoreCase("user")){
                    userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new ApplicationContextException("User Role not set."));
                    u.setRoles(Collections.singleton(userRole));
                }
                else if (u.getProfile().equalsIgnoreCase("adminPartenaire")){
                    userRole = roleRepository.findByName(RoleName.ROLE_ADMIN_PART)
                            .orElseThrow(() -> new ApplicationContextException("User Role not set."));
                    u.setRoles(Collections.singleton(userRole));
                }
                else if (u.getProfile().equalsIgnoreCase("caissier")){
                    userRole = roleRepository.findByName(RoleName.ROLE_CAISSIER)
                            .orElseThrow(() -> new ApplicationContextException("User Role not set."));
                    u.setRoles(Collections.singleton(userRole));
                }
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

    @GetMapping(value = "/bloquerUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void bloquerUser(@PathVariable int id){
        User user=userRepository.findById(id).orElseThrow(() -> new ApplicationContextException("User Role not set."));
         if(user.getStatus().equalsIgnoreCase("debloquer")){
             user.setStatus("bloquer");
         }
         else {
             user.setStatus("debloquer");
         }
         userRepository.save(user);
    }

}
