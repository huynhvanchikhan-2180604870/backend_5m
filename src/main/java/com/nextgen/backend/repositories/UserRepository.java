package com.nextgen.backend.repositories;

import com.nextgen.backend.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u where u.id = ?1")
    public User findUserById(UUID id);
    @Query("select u from User u where u.email = ?1")
    public User findByEmail(String email);
    @Query("select u from User u join fetch u.roles where u.email = ?1")
    public User findByEmailWithRole(String email);
}
