package com.example.demo.Repo;

import com.example.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email = :email")  // Corrected JPQL syntax
    User getUserByUsername(@Param("email") String email); // Renamed method to reflect query purpose
}
