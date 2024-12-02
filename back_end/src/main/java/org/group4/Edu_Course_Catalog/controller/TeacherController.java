package org.group4.Edu_Course_Catalog.controller;

import org.group4.Edu_Course_Catalog.entity.Teacher;
import org.group4.Edu_Course_Catalog.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PostMapping("/")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @GetMapping("/{user_id}")
    public Teacher getTeacher(@PathVariable int user_id) {
        return teacherService.getTeacherById(user_id);
    }

    @PutMapping("/{user_id}")
    public Teacher updateTeacher(@PathVariable int user_id, @RequestBody Teacher teacher) {
        return teacherService.updateTeacher(user_id, teacher);
    }
}
