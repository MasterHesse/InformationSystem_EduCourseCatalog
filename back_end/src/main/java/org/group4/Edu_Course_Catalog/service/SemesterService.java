package org.group4.Edu_Course_Catalog.service;

import org.group4.Edu_Course_Catalog.entity.Semester;
import org.group4.Edu_Course_Catalog.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    public Semester getSemesterById(int id) {
        return semesterRepository.findById(id).get();
    }
}
