package com.example.erpmetales.dao;

import com.example.erpmetales.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
