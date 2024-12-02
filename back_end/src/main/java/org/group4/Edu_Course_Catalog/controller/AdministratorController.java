package org.group4.Edu_Course_Catalog.controller;

import org.group4.Edu_Course_Catalog.entity.Administrator;
import org.group4.Edu_Course_Catalog.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user/admin")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/all")
    public List<Administrator> getAllAdministrator() {
        return administratorService.getAllAdministrators();
    }
}
