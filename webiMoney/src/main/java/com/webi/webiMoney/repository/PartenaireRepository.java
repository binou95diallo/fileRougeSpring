package com.webi.webiMoney.repository;

import com.webi.webiMoney.model.Partenaire;
import com.webi.webiMoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire, Integer> {

}
