package org.group4.Edu_Course_Catalog.service;

import jakarta.transaction.Transactional;
import org.group4.Edu_Course_Catalog.common.ResourceNotFoundException;
import org.group4.Edu_Course_Catalog.entity.*;
import org.group4.Edu_Course_Catalog.repository.RoleRepository;
import org.group4.Edu_Course_Catalog.repository.StudentRepository;
import org.group4.Edu_Course_Catalog.repository.TeacherRepository;
import org.group4.Edu_Course_Catalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.group4.Edu_Course_Catalog.common.JwtUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoleRepository roleRepository;

    public  final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getStatus().equals("pass")) {
            throw new DisabledException("User account is not activated");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getType().toUpperCase()));

        return new org.springframework.security.core.userdetails.User(
                user.getAccount(),
                user.getPassword(),
                authorities
        );
    }


    public User findByAccount(String account) {
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with account: " + account));
    }
    public User findById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public boolean deleteUserById(int user_id) {
        if (userRepository.existsById(user_id)) {
            userRepository.deleteById(user_id);
            return true;
        } else {
            return false;
        }
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Transactional
    public ResponseDto switchUserRole(int userId, Integer majorId, Integer semesterId) {
        // role_id == 1 为学生
        // role_id == 2 为教师
        try {
            // 1. 验证用户存在性和当前角色
            String userSql = "SELECT role_id, account, real_name, gender, contact, status FROM user WHERE user_id = ?";
            try {
                Map<String, Object> userInfo = jdbcTemplate.queryForMap(userSql, userId);
                Integer currentRoleId = ((Number) userInfo.get("role_id")).intValue();

                // 2. 检查目标表中是否已存在记录
                if (currentRoleId == 1) { // 学生转换为教师
                    // 检查教师表中是否已存在记录
                    String checkTeacherSql = "SELECT COUNT(*) FROM teacher WHERE user_id = ?";
                    int teacherCount = jdbcTemplate.queryForObject(checkTeacherSql, Integer.class, userId);
                    if (teacherCount > 0) {
                        return new ResponseDto(400, "User already exists in teacher table", null);
                    }

                    // 获取并删除学生记录
                    jdbcTemplate.update("DELETE FROM student WHERE user_id = ?", userId);

                    // 创建教师记录
                    jdbcTemplate.update(
                            "INSERT INTO teacher (user_id, position, academic_achievements) VALUES (?, NULL, NULL)",
                            userId
                    );

                } else if (currentRoleId == 2) { // 教师转换为学生
                    // 检查学生表中是否已存在记录
                    String checkStudentSql = "SELECT COUNT(*) FROM student WHERE user_id = ?";
                    int studentCount = jdbcTemplate.queryForObject(checkStudentSql, Integer.class, userId);
                    if (studentCount > 0) {
                        return new ResponseDto(400, "User already exists in student table", null);
                    }

                    // 验证 majorId 存在性
                    if (majorId == null) {
                        return new ResponseDto(400, "Major ID is required for student role", null);
                    }
                    String checkMajorSql = "SELECT COUNT(*) FROM major WHERE major_id = ?";
                    int majorCount = jdbcTemplate.queryForObject(checkMajorSql, Integer.class, majorId);
                    if (majorCount == 0) {
                        return new ResponseDto(400, "Major ID not found", null);
                    }

                    // 验证 semesterId 存在性
                    if (semesterId == null) {
                        return new ResponseDto(400, "Semester ID is required for student role", null);
                    }
                    String checkSemesterSql = "SELECT COUNT(*) FROM semester WHERE semester_id = ?";
                    int semesterCount = jdbcTemplate.queryForObject(checkSemesterSql, Integer.class, semesterId);
                    if (semesterCount == 0) {
                        return new ResponseDto(400, "Semester ID not found", null);
                    }

                    // 获取并删除教师记录
                    jdbcTemplate.update("DELETE FROM teacher WHERE user_id = ?", userId);

                    // 创建学生记录
                    jdbcTemplate.update(
                            "INSERT INTO student (user_id, major_id, semester_id) VALUES (?, ?, ?)",
                            userId, majorId, semesterId
                    );
                } else {
                    return new ResponseDto(400, "Invalid role ID", null);
                }

                // 3. 更新用户表中的角色ID
                int targetRoleId = (currentRoleId == 2) ? 1 : 2;
                jdbcTemplate.update(
                        "UPDATE user SET role_id = ? WHERE user_id = ?",
                        targetRoleId, userId
                );

                String roleType = (targetRoleId == 2) ? "teacher" : "student";
                return new ResponseDto(200, "Successfully switched to " + roleType, null);

            } catch (EmptyResultDataAccessException e) {
                return new ResponseDto(404, "User not found", null);
            }

        } catch (Exception e) {
            return new ResponseDto(500, "Error switching role: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ResponseDto updateUserStatus(int userId, String status) {
        try {
            // 验证状态值
            if (!status.equals("pass") && !status.equals("rejected")) {
                return new ResponseDto(400, "Invalid status. Must be 'pass' or 'rejected'", null);
            }

            // 检查用户是否存在
            String checkUserSql = "SELECT COUNT(*) FROM user WHERE user_id = ?";
            int userCount = jdbcTemplate.queryForObject(checkUserSql, Integer.class, userId);
            if (userCount == 0) {
                return new ResponseDto(404, "User not found", null);
            }

            // 更新用户状态
            String updateSql = "UPDATE user SET status = ? WHERE user_id = ?";
            int updatedRows = jdbcTemplate.update(updateSql, status, userId);

            if (updatedRows > 0) {
                return new ResponseDto(200, "User status updated successfully to " + status, null);
            } else {
                return new ResponseDto(500, "Failed to update user status", null);
            }

        } catch (Exception e) {
            return new ResponseDto(500, "Error updating user status: " + e.getMessage(), null);
        }
    }

    @Autowired
    private JwtUtil jwtUtil;
    @Transactional
    public ResponseDto updateUserInfo(int userId, UserUpdateRequest updateRequest, String token) {
        try {
            // 验证token并获取当前用户ID
            Integer currentUserId = jwtUtil.getUserIdFromToken(token);
            if (currentUserId == null) {
                return new ResponseDto(401, "Invalid token", null);
            }

            // 验证是否是修改自己的信息
            if (currentUserId != userId) {
                return new ResponseDto(403, "You can only update your own information", null);
            }

            // 1. 检查用户是否存在及其角色
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 2. 更新用户基本信息
            user.setRealName(updateRequest.getRealName());
            user.setGender(updateRequest.getGender());
            user.setContact(updateRequest.getContact());
            userRepository.save(user);

            // 3. 根据角色更新特定信息
            if (user.getRole().toString().toUpperCase().equals("STUDENT")) { // 学生
                Student student = studentRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Student not found"));
                student.setRealName(updateRequest.getRealName());
                student.setGender(updateRequest.getGender());
                student.setContact(updateRequest.getContact());
                student.setSemesterId(updateRequest.getSemesterId());
                student.setMajorId(updateRequest.getMajorId());
                studentRepository.save(student);
            } else if (user.getRole().toString().toUpperCase().equals("TEACHER")) { // 教师
                Teacher teacher = teacherRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Teacher not found"));
                teacher.setRealName(updateRequest.getRealName());
                teacher.setGender(updateRequest.getGender());
                teacher.setContact(updateRequest.getContact());
                teacher.setPosition(updateRequest.getPosition());
                teacher.setAcademicAchievements(updateRequest.getAcademicAchievements());
                teacherRepository.save(teacher);
            }

            return new ResponseDto(200, "User information updated successfully", null);

        } catch (Exception e) {
            return new ResponseDto(500, "Error updating user information: " + e.getMessage(), null);
        }
    }

    public Page<User> getPassUser(int pageSize, int pageNum){
        Pageable pageable = PageRequest.of(pageNum, pageSize); // 创建分页请求
        return userRepository.findByStatus("pass", pageable);
    }

    public Page<User> getPendingUser(int pageSize, int pageNum){
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userRepository.findByStatus("pending", pageable);
    }

    public List<User> getPassUser(){
        return userRepository.findByStatus("pass");
    }

    public List<User> getPendingUser(){
        return userRepository.findByStatus("pending");
    }
}
