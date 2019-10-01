package com.webi.webiMoney.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Partenaire {
    @OneToMany(mappedBy = "partenaire")
    private List<Compte> comptes;
    @OneToMany(mappedBy = "partenaire")
    private List<User> users;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Size(min=3, max = 50)
    private String nomComplet;
    @NotBlank
    @Size(min=3, max = 50)
    private String adresse;
    @NotBlank
    @Size(min=3, max = 50)
    private String telephone;
    @NotBlank
    @Size(min=3, max = 50)
    private String email;
    @NotBlank
    @Size(min=3, max = 50)
    private  String status;
    @NotBlank
    @Size(min=3, max = 50)
    private String ninea;
    @NotBlank
    @Size(min=3, max = 50)
    private String raisonSocial;

    public  Partenaire(){}

    public  Partenaire (Integer id, String nomComplet,String status, String telephone,String adresse, String email,
                        String ninea, String raisonSocial){
        this.id=id;
        this.ninea=ninea;
        this.nomComplet=nomComplet;
        this.adresse=adresse;
        this.telephone=telephone;
        this.raisonSocial=raisonSocial;
        this.email=email;
        this.status=status;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNinea() {
        return ninea;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }
}