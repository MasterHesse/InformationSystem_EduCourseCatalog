package org.group4.Edu_Course_Catalog.controller;

import org.group4.Edu_Course_Catalog.entity.Student;
import org.group4.Edu_Course_Catalog.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/")
    public void addStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
    }

    @GetMapping("/{user_id}")
    public Student getStudent(@PathVariable int user_id) {
        return studentService.getStudentById(user_id);
    }

    @PutMapping("/{user_id}")
    public void updateStudent(@PathVariable int user_id, @RequestBody Student student) {
        studentService.updateStudent(user_id, student);
    }
}
