package com.cine.cine.repository;
import com.cine.cine.entity.CinemaUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CinemaUser, Long> {
    CinemaUser findByEmail(String email);
}
