package com.jwtcompanyapp.repository;


import com.jwtcompanyapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String name);
}
