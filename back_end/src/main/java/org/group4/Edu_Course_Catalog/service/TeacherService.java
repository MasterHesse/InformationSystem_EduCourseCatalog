package org.group4.Edu_Course_Catalog.service;

import org.group4.Edu_Course_Catalog.entity.Role; // 修改这个导入
import org.group4.Edu_Course_Catalog.entity.Student;
import org.group4.Edu_Course_Catalog.entity.Teacher;
import org.group4.Edu_Course_Catalog.entity.TeacherRegisterRequest;
import org.group4.Edu_Course_Catalog.repository.RoleRepository;
import org.group4.Edu_Course_Catalog.repository.TeacherRepository;
import org.group4.Edu_Course_Catalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(int id, Teacher teacher) {
        return teacherRepository.findById(id).map(newTeacher ->{
            newTeacher.setRealName(teacher.getRealName());
            newTeacher.setGender(teacher.getGender());
            newTeacher.setContact(teacher.getContact());
            newTeacher.setPosition(teacher.getPosition());
            newTeacher.setAcademicAchievements(teacher.getAcademicAchievements());
            newTeacher.setResearchDirection(teacher.getResearchDirection());
            return teacherRepository.save(newTeacher);
        }).orElseThrow(() -> new RuntimeException("Teacher no Found with id" + id));
    }

    public Teacher getTeacherById(int user_id) {
        Optional<Teacher> teacher = teacherRepository.findById(user_id);
        return teacher.orElse(null);
    }

    public Teacher createTeacher(TeacherRegisterRequest request) {
        if (userRepository.existsByAccount(request.getAccount())) {
            throw new IllegalArgumentException("Account already exists");
        }

        Teacher teacher = new Teacher();
        teacher.setAccount(request.getAccount());
        teacher.setPassword(passwordEncoder.encode(request.getPassword())); // 加密密码
        teacher.setRealName(request.getRealName());
        teacher.setGender(request.getGender());
        teacher.setContact(request.getContact());

        Role teacherRole = roleRepository.findByType("teacher")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        teacher.setRole(teacherRole);
        teacher.setStatus("pending");

        teacher.setPosition(request.getPosition());
        teacher.setAcademicAchievements(request.getAcademicAchievements());
        teacher.setResearchDirection(request.getResearchDirection());

        return teacherRepository.save(teacher);
    }

}
