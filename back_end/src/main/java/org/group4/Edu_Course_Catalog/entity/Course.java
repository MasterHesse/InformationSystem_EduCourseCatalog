package org.group4.Edu_Course_Catalog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(name = "major_id") // explicitly map to the database column if needed
    private Long majorId; // Change this to camelCase

    private Long semesterId;
    private Long teacherId;
    private String courseName;
    private Integer plannedHour;
    private String briefDescription;
    private String syllabusProfileUrl;


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getPlannedHour() {
        return plannedHour;
    }

    public void setPlannedHour(Integer plannedHour) {
        this.plannedHour = plannedHour;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getSyllabusProfileUrl() {
        return syllabusProfileUrl;
    }

    public void setSyllabusProfileUrl(String syllabusProfileUrl) {
        this.syllabusProfileUrl = syllabusProfileUrl;
    }
}
