package org.group4.Edu_Course_Catalog.service;

import org.group4.Edu_Course_Catalog.entity.Role;
import org.group4.Edu_Course_Catalog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(int role_id) {
        Optional<Role> role = roleRepository.findById(role_id);
        return role.orElse(null);
    }

}