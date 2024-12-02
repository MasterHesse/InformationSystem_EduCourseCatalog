package org.group4.Edu_Course_Catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.group4.Edu_Course_Catalog.entity.*;
import org.group4.Edu_Course_Catalog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getAccount(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成JWT token
            String token = jwtService.generateToken(authentication);

            // 返回登录成功信息和token
            return ResponseEntity.ok()
                    .body(Map.of(
                            "token", token,
                            "message", "Login successful",
                            "role", authentication.getAuthorities().iterator().next().getAuthority()
                    ));

        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Account not activated"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials"));
        }
    }

    // 添加登出方法
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            // 获取当前的Authentication
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {
                // 清除SecurityContext
                SecurityContextHolder.clearContext();

                // 使session失效（如果使用了session）
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }

                return ResponseEntity.ok()
                        .body(Map.of("message", "Logout successful"));
            }

            return ResponseEntity.ok()
                    .body(Map.of("message", "Already logged out"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Logout failed",
                            "error", e.getMessage()));
        }
    }

    // 教师注册
    @PostMapping("/register/teacher")
    public ResponseEntity<?> registerTeacher(@RequestBody TeacherRegisterRequest request) {
        try {
            Teacher teacher = teacherService.createTeacher(request);
            return ResponseEntity.ok()
                    .body(Map.of("message", "Teacher registration successful. Waiting for approval",
                            "user_id", teacher.getUserId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Registration failed",
                            "error", e.getMessage()));
        }
    }

    // 学生注册
    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterRequest request) {
        try {
            Student student = studentService.createStudent(request);
            return ResponseEntity.ok()
                    .body(Map.of("message", "Student registration successful. Waiting for approval",
                            "user_id", student.getUserId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Registration failed",
                            "error", e.getMessage()));
        }
    }

}