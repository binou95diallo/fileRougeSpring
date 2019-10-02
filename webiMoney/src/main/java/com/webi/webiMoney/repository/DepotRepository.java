package com.webi.webiMoney.repository;

import com.webi.webiMoney.model.Compte;
import com.webi.webiMoney.model.Depot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepotRepository extends JpaRepository<Depot, Integer> {
}
