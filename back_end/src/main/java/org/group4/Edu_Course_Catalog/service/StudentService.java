package org.group4.Edu_Course_Catalog.service;

import org.group4.Edu_Course_Catalog.entity.Role;
import org.group4.Edu_Course_Catalog.entity.Student;
import org.group4.Edu_Course_Catalog.entity.StudentRegisterRequest;
import org.group4.Edu_Course_Catalog.repository.RoleRepository;
import org.group4.Edu_Course_Catalog.repository.StudentRepository;
import org.group4.Edu_Course_Catalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(int id, Student student) {
        return studentRepository.findById(id).map(newStudent ->{
            newStudent.setRealName(student.getRealName());
            newStudent.setGender(student.getGender());
            newStudent.setContact(student.getContact());
            newStudent.setSemesterId(student.getSemesterId());
            newStudent.setMajorId(student.getMajorId());
            return studentRepository.save(newStudent);
        }).orElseThrow(() -> new RuntimeException("Student no Found with id" + id));
    }

    public Student getStudentById(int user_id) {
        Optional<Student> student = studentRepository.findById(user_id);
        return student.orElse(null);
    }

    public Student createStudent(StudentRegisterRequest request) {
        // 检查账号是否已存在
        if (userRepository.existsByAccount(request.getAccount())) {
            throw new IllegalArgumentException("Account already exists");
        }

        // 获取学生角色
        Role studentRole = roleRepository.findByType("student")
                .orElseThrow(() -> new RuntimeException("Student role not found"));

        Student student = new Student();
        // 设置基本用户信息
        student.setAccount(request.getAccount());
        student.setPassword(request.getPassword());
        student.setRealName(request.getRealName());
        student.setGender(request.getGender());
        student.setContact(request.getContact());
        student.setRole(studentRole);
        student.setStatus("pending");

        // 设置学生特有信息
        student.setMajorId(request.getMajorId());
        student.setSemesterId(request.getSemesterId());

        return userRepository.save(student);
    }
}
