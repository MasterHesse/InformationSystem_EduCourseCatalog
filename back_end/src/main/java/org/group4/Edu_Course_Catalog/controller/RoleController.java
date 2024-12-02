package org.group4.Edu_Course_Catalog.controller;

import org.group4.Edu_Course_Catalog.entity.Role;
import org.group4.Edu_Course_Catalog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{role_id}")
    public Role getRole(@PathVariable int role_id) {
        return roleService.getRoleById(role_id);
    }

}
