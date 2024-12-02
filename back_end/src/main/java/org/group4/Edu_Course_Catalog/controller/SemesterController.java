package org.group4.Edu_Course_Catalog.controller;

import org.group4.Edu_Course_Catalog.entity.Semester;
import org.group4.Edu_Course_Catalog.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/semester")
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @GetMapping("/all")
    public List<Semester> getAllSemesters() {
        return semesterService.getAllSemesters();
    }

    @GetMapping("/{semester_id}")
    public Semester getSemesterById(@PathVariable int semester_id) {
        return semesterService.getSemesterById(semester_id);
    }
}
