package org.group4.Edu_Course_Catalog.repository;

import org.group4.Edu_Course_Catalog.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Integer> {
}
