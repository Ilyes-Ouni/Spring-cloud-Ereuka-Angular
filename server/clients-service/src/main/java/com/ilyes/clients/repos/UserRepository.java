package com.ilyes.clients.repos;

import com.ilyes.clients.entities.Role;
import com.ilyes.clients.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


  @Query("select u from User as u where u.email = :email")
  Optional<User> findByEmail(String email);

  Optional<Role> findByRole(String role);

  List<User> findAll();

  boolean existsByEmail(String email);

  User findTopByOrderByIdDesc();

}
