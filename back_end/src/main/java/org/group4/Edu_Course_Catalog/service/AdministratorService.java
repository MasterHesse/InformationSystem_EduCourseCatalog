package org.group4.Edu_Course_Catalog.service;

import org.group4.Edu_Course_Catalog.entity.Administrator;
import org.group4.Edu_Course_Catalog.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }
}
