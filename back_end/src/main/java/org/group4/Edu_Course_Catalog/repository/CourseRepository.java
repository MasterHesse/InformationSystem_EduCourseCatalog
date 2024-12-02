package org.group4.Edu_Course_Catalog.repository;

import org.group4.Edu_Course_Catalog.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // 此接口继承了CRUD和分页等基本方法，无需额外定义方法

    List<Course> findByMajorId(Long majorId);
    Page<Course> findCourseByMajorId(Long majorId, Pageable pageable);


}
