package com.webi.webiMoney.controller;

import com.webi.webiMoney.model.Compte;
import com.webi.webiMoney.model.Depot;
import com.webi.webiMoney.model.User;
import com.webi.webiMoney.repository.CompteRepository;
import com.webi.webiMoney.repository.DepotRepository;
import com.webi.webiMoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping(value = "/compte")
public class CompteController {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    @Autowired
    DepotRepository depotRepository;
    @Autowired
    UserRepository userRepository;
    Compte compte;
    @Autowired
    CompteRepository compteRepository;
    @PostMapping(value = "/depot/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addDepot(@PathVariable int id,@RequestBody(required = false) Depot d, HttpServletRequest request){
            String caissier=request.getUserPrincipal().getName();//récupére le username de l'utilisateur connecté
            User user=userRepository.userByUsername(caissier);
            compte=compteRepository.compteById(id);
            if(d.getMontant()<75000){
                return new ResponseEntity<String>("le montant du dépot doit être supérieur ou égale à 75000", HttpStatus.OK);
            }
            int newSolde=compte.getSolde() + d.getMontant();
            compte.setSolde(newSolde);
            compteRepository.save(compte);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");//210902 251763
        LocalDate date=  LocalDate.now();
        d.setUser(user);
        d.setDateDepot(date);
        d.setCompte(compte);
        depotRepository.save(d);
        return new ResponseEntity<String>("Depot ajouté avec succés", HttpStatus.OK);
    }

    @PostMapping(value = "/ajoutCompte",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addCompte(@RequestBody() Compte c, HttpServletRequest request){
        String adminPartenaire=request.getUserPrincipal().getName();
        User user=userRepository.userByUsername(adminPartenaire);
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddhh");
        Date now=new Date();
        int rand= (int) Math.random();
        String numcompte=formater.format(now);
        c.setNumeroCompte(numcompte+""+rand);
        c.setPartenaire(user.getPartenaire());
        compteRepository.save(c);
        return new ResponseEntity<String>("Compte ajouté avec succés", HttpStatus.OK);
    }
}
