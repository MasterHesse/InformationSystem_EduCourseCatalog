package org.group4.Edu_Course_Catalog.service;

import org.group4.Edu_Course_Catalog.entity.Course;
import org.group4.Edu_Course_Catalog.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course getCourseById(long course_id) {
        Optional<Course> course = courseRepository.findById(course_id);
        return course.orElse(null);
    }

    public List<Course> getCoursesByMajorId(long majorId) {
        return courseRepository.findByMajorId(majorId); // 使用 major_id 查询课程
    }

    public Course updateCourse(long course_id,Course course) {
        return courseRepository.findById(course_id).map(newCourse -> {
            newCourse.setMajorId(course.getMajorId());
            newCourse.setSemesterId(course.getSemesterId());
            newCourse.setCourseName(course.getCourseName());
            newCourse.setPlannedHour(course.getPlannedHour());
            newCourse.setBriefDescription(course.getBriefDescription());
            newCourse.setSyllabusProfileUrl(course.getSyllabusProfileUrl());
            newCourse.setTeacherId(course.getTeacherId());
            return courseRepository.save(newCourse);
        }).orElseThrow(() -> new RuntimeException("Course no Found with id" + course_id));
    }

    public boolean deleteCourse(long course_id) {
        Optional<Course> course = courseRepository.findById(course_id);
        if (course.isPresent()) {
            courseRepository.delete(course.get());
            return true;
        } else {
            return false;
        }
    }

    public String getSyllabusProfileUrl(long course_id) {
        Optional<Course> course = courseRepository.findById(course_id);
        if (course.isPresent()) {
            return course.get().getSyllabusProfileUrl();
        }
        return null;
    }

    // Get course with page( teacher )
    public Page<Course> getAllCoursesPaged(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize); // 创建分页请求
        return courseRepository.findAll(pageable); // 获取所有课程的分页数据
    }

    // 获取某专业的课程并进行分页
    public Page<Course> getCoursesByMajorIdPaged(long major_id, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize); // 创建分页请求
        return courseRepository.findCourseByMajorId(major_id, pageable);
    }
}
