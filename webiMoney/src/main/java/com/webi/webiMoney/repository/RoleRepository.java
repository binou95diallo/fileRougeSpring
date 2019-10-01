package com.webi.webiMoney.repository;

import com.webi.webiMoney.model.Role;
import com.webi.webiMoney.model.RoleName;
import com.webi.webiMoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
