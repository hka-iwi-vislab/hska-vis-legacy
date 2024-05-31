package com.hka.verteiltesysteme.repositories;

import com.hka.verteiltesysteme.database.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByLevel1(Integer level);
}
