package com.jwtcompanyapp.service.impl;

import com.jwtcompanyapp.model.Role;
import com.jwtcompanyapp.repository.RoleRepository;
import com.jwtcompanyapp.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            log.warn("IN findById - no role found by id: {}", id);
            return null;
        }

        log.info("IN findById - role: {} found by id: {}", role);
        return role;
    }
}
