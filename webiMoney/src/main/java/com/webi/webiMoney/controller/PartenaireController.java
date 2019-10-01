package com.webi.webiMoney.controller;

import com.webi.webiMoney.model.*;
import com.webi.webiMoney.repository.CompteRepository;
import com.webi.webiMoney.repository.PartenaireRepository;
import com.webi.webiMoney.repository.RoleRepository;
import com.webi.webiMoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/partenaire")
public class PartenaireController {
    @Autowired
    private PartenaireRepository partenaireRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  CompteRepository compteRepository;
    @Autowired
    RoleRepository roleRepository;
    @GetMapping(value = "/liste")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Partenaire> liste(){

        return partenaireRepository.findAll();
    }
    @Autowired
    PasswordEncoder encoder;
    @PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Partenaire add(@RequestBody() Partenaire partenaire){
        Partenaire part= partenaireRepository.save(partenaire);
        User user = new User();
        Compte compte= new Compte();
        compte.setPartenaire(partenaire);
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddhhss");//210902 251763
        Date now=new Date();
        double rand=Math.random() * ( 1000 - 99 );
        String numcompte=formater.format(now);
        compte.setNumeroCompte(numcompte+""+rand);
        compteRepository.save(compte);
        user.setNomComplet(partenaire.getNomComplet());
        user.setUsername(partenaire.getNomComplet());
        user.setAdresse(partenaire.getAdresse());
        user.setPassword((encoder.encode("passer")));
        user.setEmail(partenaire.getEmail());
        user.setTelephone(partenaire.getTelephone());
        user.setStatus("debloquer");
        user.setProfile("admin");
        user.setPartenaire(partenaire);
        user.setCompte(compte);
        System.out.println(user);
        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new ApplicationContextException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
        return part;
    }
    //Modifier partenaire
    @PutMapping(value = "/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void update(@PathVariable int id, @RequestBody() Partenaire p){
        Optional<Partenaire> partenaire;
        partenaire=partenaireRepository.findById(id);
        if(partenaire!=null){
            p.setId(id);
        }
        else{
            System.out.println("utilisateur inconnu");
        }

        partenaireRepository.save(p);
    }
}
