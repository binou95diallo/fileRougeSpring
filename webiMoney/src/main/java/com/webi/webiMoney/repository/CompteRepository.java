package com.webi.webiMoney.repository;

import com.webi.webiMoney.model.Compte;
import com.webi.webiMoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {
    Compte findByNumeroCompte(String numeroCompte);

    @Query("SELECT c FROM Compte c WHERE c.id =:x")
    public Compte compteById(@Param("x")int id);

}