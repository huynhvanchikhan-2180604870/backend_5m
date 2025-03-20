package com.nextgen.backend.repositories;

import com.nextgen.backend.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("select r from Role r where r.name = ?1")
    public Role findByName(String name);
}
