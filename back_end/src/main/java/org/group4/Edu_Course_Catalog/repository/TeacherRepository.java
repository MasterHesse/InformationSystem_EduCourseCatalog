package org.group4.Edu_Course_Catalog.repository;

import org.group4.Edu_Course_Catalog.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByPosition(String position);

    Optional<Teacher> findByResearchDirection(String researchDirection);

    Optional<Teacher> findByAcademicAchievements(String academicAchievements);
}
