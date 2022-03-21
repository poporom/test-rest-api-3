package com.telros.service;

import com.telros.entity.Role;
import com.telros.entity.User;
import com.telros.repo.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRoleById(Integer id) {
        return roleRepository.findById(id);
    }
}
