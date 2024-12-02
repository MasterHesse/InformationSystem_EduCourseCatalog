package org.group4.Edu_Course_Catalog.service;

import org.group4.Edu_Course_Catalog.entity.Major;
import org.group4.Edu_Course_Catalog.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MajorService {

    @Autowired
    private MajorRepository majorRepository;

    public List<Major> getAllMajor() {
        return majorRepository.findAll();
    }

    public Major getMajorById(int major_id) {
        Optional<Major> major = majorRepository.findById(major_id);
        return major.orElse(null);
    }
}
