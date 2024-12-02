package org.group4.Edu_Course_Catalog.controller;

import org.group4.Edu_Course_Catalog.entity.Major;
import org.group4.Edu_Course_Catalog.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/major")
public class majorController {

    @Autowired
    MajorService majorService;

    @GetMapping("/all")
    public List<Major> getAllMajor() {
        return majorService.getAllMajor();
    }

    @GetMapping("/{major_id}")
    public Major getMajorById(@PathVariable int major_id) {
        return majorService.getMajorById(major_id);
    }
}
