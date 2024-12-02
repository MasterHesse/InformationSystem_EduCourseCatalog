package org.group4.Edu_Course_Catalog.controller;

import org.group4.Edu_Course_Catalog.entity.ResponseDto;
import org.group4.Edu_Course_Catalog.entity.User;
import org.group4.Edu_Course_Catalog.entity.UserUpdateRequest;
import org.group4.Edu_Course_Catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId, @AuthenticationPrincipal UserDetails userDetails) {
        // 获取当前登录用户
        User currentUser = userService.findByAccount(userDetails.getUsername());

        // 使用 == 比较基本类型
        if (currentUser.getUserId() == userId ||
                currentUser.getRole().getType().equals("ROLE_ADMINISTRATOR")) {
            User user = userService.findById(userId);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto("Access denied"));
        }
    }

    @DeleteMapping("/delete/id/{user_id}")
    public ResponseEntity<User> deleteUser(@PathVariable int user_id) {
        if (userService.deleteUserById(user_id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 获取当前登录用户的信息
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        try {
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDto("No authentication found"));
            }

            String username = authentication.getName();
            User user = userService.findByAccount(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto("User not found"));
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto("Error retrieving user information: " + e.getMessage()));
        }
    }

    @PostMapping("/switch-role")
    public ResponseDto switchUserRole(
            @RequestParam int userId,
            @RequestParam(required = false, defaultValue = "1") Integer majorId,
            @RequestParam(required = false, defaultValue = "1") Integer semesterId) {
        return userService.switchUserRole(userId, majorId, semesterId);
    }

    // UserController.java
    @PostMapping("/update-status")
    public ResponseDto updateUserStatus(
            @RequestParam int userId,
            @RequestParam String status) {
        return userService.updateUserStatus(userId, status);
    }

    @PutMapping("/update-info")
    public ResponseDto updateUserInfo(
            @RequestParam int userId,
            @RequestBody UserUpdateRequest updateRequest,
            @RequestHeader("Authorization") String token) {
        return userService.updateUserInfo(userId, updateRequest, token);
    }

    @GetMapping("/pass/{page_num}")
    public ResponseEntity<?> getPass(@PathVariable int page_num) {
        int pageSize = 3;
        Page<User> userPage = userService.getPassUser(pageSize, page_num);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/pending/{page_num}")
    public ResponseEntity<?> getPending(@PathVariable int page_num) {
        int pageSize = 3;
        Page<User> userPage = userService.getPendingUser(pageSize, page_num);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/pass")
    public List<User> getPass() {
        return userService.getPassUser();
    }

    @GetMapping("/pending")
    public List<User> getPending() {
        return userService.getPendingUser();
    }
}
