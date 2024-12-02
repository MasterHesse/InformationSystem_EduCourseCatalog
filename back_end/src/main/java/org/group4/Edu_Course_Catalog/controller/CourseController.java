package org.group4.Edu_Course_Catalog.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tika.Tika;
import org.group4.Edu_Course_Catalog.entity.Course;
import org.group4.Edu_Course_Catalog.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;


import org.springframework.data.domain.Page;


@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Get all course
    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Create new course without a syllabus file
    @PostMapping("/")
    public Course addCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    // get course by course_id
    @GetMapping("/course_id/{course_id}")
    public Course getCourse(@PathVariable long course_id) {
        return courseService.getCourseById(course_id);
    }

    // 根据 major_id 获取课程列表
    @GetMapping("/major_id/{major_id}")
    public List<Course> getCoursesByMajorId(@PathVariable long major_id) {
        return courseService.getCoursesByMajorId(major_id); // 使用 major_id 获取课程列表
    }

    // update course by course_id
    @PutMapping("/{course_id}")
    public Course updateCourse(@PathVariable long course_id, @RequestBody Course newCourse) {
        return courseService.updateCourse(course_id, newCourse);
    }

    // Delete by course_id
    @DeleteMapping("/id/{course_id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long course_id) {
        if (courseService.deleteCourse(course_id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Success 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // NO FOUND 404
        }
    }

    // download syllabus by course_id
    @GetMapping("/syllabus/download/{courseId}")
    public void downloadSyllabus(@PathVariable Long courseId, HttpServletResponse response) throws IOException {
        Course course = courseService.getCourseById(courseId);
        Tika tika = new Tika();

        // 检查课程和大纲文件是否存在
        if (course == null || course.getSyllabusProfileUrl() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course or syllabus not found");
            return;
        }

        // 获取文件名并构建文件路径
        String fileName = course.getSyllabusProfileUrl(); // 例如: "CS101 Introduction to Computer Science.pdf"
        Path path = Paths.get("src", "main", "resources", "syllabus", fileName);
        File file = path.toFile();

        System.out.println("File exists: " + file.exists());
        System.out.println("File path: " + file.getAbsolutePath());

        if (file.exists()) {
            // 使用 Tika 检测文件类型
            String mimeType = tika.detect(file);
            response.setContentType(mimeType); // 动态设置内容类型

            // 对文件名进行 URL 编码，以处理空格和特殊字符
            String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

            try (FileInputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error reading file");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Syllabus not found");
        }
    }

    // upload syllabus by course_id
    @PostMapping("/syllabus/upload/{courseId}")
    public ResponseEntity<String> uploadSyllabus(@PathVariable Long courseId, @RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            return ResponseEntity.badRequest().body("File name is empty");
        }

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        // 检查 syllabus_profile_url
        String syllabusProfileUrl = course.getSyllabusProfileUrl() ;
        if (syllabusProfileUrl != null) {
            // 如果存在，删除旧文件
            Path oldFilePath = Paths.get("src", "main", "resources", "syllabus", syllabusProfileUrl);
            try {
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete old syllabus file");
            }
        }

        // 保存新文件
        Path newFilePath = Paths.get("src", "main", "resources", "syllabus", fileName);
        try {
            file.transferTo(newFilePath);
            // 更新 syllabus_profile_url
            course.setSyllabusProfileUrl(fileName);
            courseService.updateCourse(courseId, course);
            return ResponseEntity.ok("Syllabus uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload syllabus file");
        }
    }

    // Get all course with paging
    @GetMapping("/all/{page_num}")
    public ResponseEntity<Page<Course>> getAllCoursesPaged(@PathVariable int page_num) {
        int pageSize = 3; // 每页显示 3 条课程
        Page<Course> coursePage = courseService.getAllCoursesPaged(page_num, pageSize);

        if (coursePage == null || !coursePage.hasContent()) {
            return ResponseEntity.noContent().build(); // 如果没有内容，返回 204 No Content
        }
        return ResponseEntity.ok(coursePage); // 返回分页内容
    }

    // Get course by major_id with paging
    @GetMapping("/major_id/{major_id}/{page_num}")
    public ResponseEntity<Page<Course>> getCoursesByMajorIdPaged(
            @PathVariable long major_id,
            @PathVariable int page_num) {
        int pageSize = 3; // 每页显示 3 条课程
        Page<Course> coursePage = courseService.getCoursesByMajorIdPaged(major_id, page_num, pageSize);

        if (coursePage == null || !coursePage.hasContent()) {
            return ResponseEntity.noContent().build(); // 如果没有内容，返回 204 No Content
        }
        return ResponseEntity.ok(coursePage); // 返回分页内容
    }
}
