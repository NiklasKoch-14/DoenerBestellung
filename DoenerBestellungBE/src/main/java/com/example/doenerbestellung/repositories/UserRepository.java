package com.example.doenerbestellung.repositories;

import com.example.doenerbestellung.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findAllByDefaultOrderIsNotNull();
}
