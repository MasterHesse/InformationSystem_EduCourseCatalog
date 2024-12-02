package org.group4.Edu_Course_Catalog.repository;

import org.group4.Edu_Course_Catalog.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByMajorId(int majorId);
    List<Student> findBySemesterId(int semesterId);

    boolean existsByUserId(int userId);
}
