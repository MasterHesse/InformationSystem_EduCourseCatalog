/*
 Navicat Premium Data Transfer

 Source Server         : localMySQL
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : ecc

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 05/11/2024 21:15:13
*/
-- Create
CREATE DATABASE IF NOT EXISTS `ecc` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

-- Use
USE `ecc`;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `user_id` int(0) NOT NULL,
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  CONSTRAINT `administrator_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (1, 'System Administrator');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `major_id` int(0) NOT NULL,
  `semester_id` int(0) NOT NULL,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `planned_hour` int(0) NOT NULL,
  `brief_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `syllabus_profile_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `teacher_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `MajorID`(`major_id`) USING BTREE,
  INDEX `SemesterID`(`semester_id`) USING BTREE,
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `course_ibfk_2` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 1, 1, 'CS101 - Introduction to Computer Science', 48, 'Basic concepts of computer science', 'CS101_Introduction_to_Computer_Science.pdf', 2);
INSERT INTO `course` VALUES (2, 1, 2, 'CS102 - Data Structures', 64, 'Introduction to data structures', 'CS102 - Data Structures.pdf', 3);
INSERT INTO `course` VALUES (3, 2, 1, 'AU101 - Basics of Automation', 48, 'Fundamentals of automation', 'AU101%20-%20Basics%20of%20Automation.pdf', 2);
INSERT INTO `course` VALUES (4, 2, 2, 'AU102 - Control Systems', 64, 'Introduction to control systems', 'AU102%20-%20Control%20Systems.pdf', 3);
INSERT INTO `course` VALUES (5, 3, 1, 'EE101 - Circuit Analysis', 48, 'Basics of circuit analysis', 'EE101%20-%20Circuit%20Analysis.docx', 2);
INSERT INTO `course` VALUES (6, 3, 2, 'EE102 - Electromagnetics', 64, 'Introduction to electromagnetics', 'EE102%20-%20Electromagnetics.pdf', 3);
INSERT INTO `course` VALUES (11, 1, 1, 'computer structure', 50, 'Updated basic concepts of computer science', 'Syllabus - Sample.docx', 2);

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `major_id` int(0) NOT NULL AUTO_INCREMENT,
  `major_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`major_id`) USING BTREE,
  UNIQUE INDEX `MajorName`(`major_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (2, 'Automation');
INSERT INTO `major` VALUES (1, 'Computer Science and Technology');
INSERT INTO `major` VALUES (3, 'Electrical Engineering and Automation');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'Student');
INSERT INTO `role` VALUES (2, 'Teacher');
INSERT INTO `role` VALUES (3, 'Administrator');

-- ----------------------------
-- Table structure for role_seq
-- ----------------------------
DROP TABLE IF EXISTS `role_seq`;
CREATE TABLE `role_seq`  (
  `next_val` bigint(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_seq
-- ----------------------------
INSERT INTO `role_seq` VALUES (1);

-- ----------------------------
-- Table structure for semester
-- ----------------------------
DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester`  (
  `semester_id` int(0) NOT NULL AUTO_INCREMENT,
  `semester_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`semester_id`) USING BTREE,
  UNIQUE INDEX `SemesterName`(`semester_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of semester
-- ----------------------------
INSERT INTO `semester` VALUES (2, 'Fall - Year 1');
INSERT INTO `semester` VALUES (4, 'Fall - Year 2');
INSERT INTO `semester` VALUES (6, 'Fall - Year 3');
INSERT INTO `semester` VALUES (8, 'Fall - Year 4');
INSERT INTO `semester` VALUES (1, 'Spring - Year 1');
INSERT INTO `semester` VALUES (3, 'Spring - Year 2');
INSERT INTO `semester` VALUES (5, 'Spring - Year 3');
INSERT INTO `semester` VALUES (7, 'Spring - Year 4');

-- ----------------------------
-- Table structure for semester_seq
-- ----------------------------
DROP TABLE IF EXISTS `semester_seq`;
CREATE TABLE `semester_seq`  (
  `next_val` bigint(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of semester_seq
-- ----------------------------
INSERT INTO `semester_seq` VALUES (1);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `user_id` int(0) NOT NULL,
  `major_id` int(0) NOT NULL,
  `semester_id` int(0) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `MajorID`(`major_id`) USING BTREE,
  INDEX `SemesterID`(`semester_id`) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `student_ibfk_3` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (4, 1, 1);
INSERT INTO `student` VALUES (5, 2, 2);
INSERT INTO `student` VALUES (6, 3, 3);
INSERT INTO `student` VALUES (7, 1, 4);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `user_id` int(0) NOT NULL,
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `academic_achievements` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `research_direction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (2, 'Professor', 'Published 20 papers in AI', 'Artificial Intelligence');
INSERT INTO `teacher` VALUES (3, 'Associate Professor', '10 years in Robotics', 'Industrial Automation');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NOT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `Account`(`account`) USING BTREE,
  INDEX `RoleID`(`role_id`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 3, '123', '123', 'Admin User', 'Other', 'admin@example.com', 'pass');
INSERT INTO `user` VALUES (2, 2, 'teacher1', 'teacherpass', 'Dr. Alan Smith', 'Male', 'alan@example.com', 'pass');
INSERT INTO `user` VALUES (3, 2, 'teacher2', 'teacherpass', 'Dr. Jane Doe', 'Female', 'jane@example.com', 'pending');
INSERT INTO `user` VALUES (4, 1, 'student1', 'studentpass', 'John Doe', 'Male', 'john@example.com', 'pass');
INSERT INTO `user` VALUES (5, 1, 'student2', 'studentpass', 'Alice Lee', 'Female', 'alice@example.com', 'pass');
INSERT INTO `user` VALUES (6, 1, 'student3', 'studentpass', 'Bob Chen', 'Male', 'bob@example.com', 'pending');
INSERT INTO `user` VALUES (7, 1, 'student101', 'studentpass', 'John Wick', 'Other', 'johnwick@example.com', 'pending');

SET FOREIGN_KEY_CHECKS = 1;
