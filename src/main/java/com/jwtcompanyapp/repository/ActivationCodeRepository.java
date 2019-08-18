package com.jwtcompanyapp.repository;


import com.jwtcompanyapp.model.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {
    ActivationCode findByUserId(Long id);
}
