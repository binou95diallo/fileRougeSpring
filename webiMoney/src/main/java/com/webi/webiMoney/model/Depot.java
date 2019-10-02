package com.webi.webiMoney.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Depot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
     private  int montant;
     private LocalDate dateDepot;

    @JoinColumn(name = "compte_id",referencedColumnName ="id")
    @ManyToOne(optional = false)
    @JsonIgnoreProperties
    private Compte compte;

    @JoinColumn(name = "user_id",referencedColumnName ="id")
    @ManyToOne(optional = false)
    @JsonIgnoreProperties
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public LocalDate getDateDepot(LocalDate date) {
        return dateDepot;
    }

    public void setDateDepot(LocalDate dateDepot) {
        this.dateDepot = dateDepot;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
