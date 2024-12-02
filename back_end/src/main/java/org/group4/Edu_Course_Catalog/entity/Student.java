package org.group4.Edu_Course_Catalog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Student extends User {

    @Column(name = "major_id")
    private int majorId;
    @Column(name = "semester_id")
    private int semesterId;

    @ManyToOne
    @JoinColumn(name = "major_id", insertable = false, updatable = false)
    private Major major;

    @ManyToOne
    @JoinColumn(name = "semester_id", insertable = false, updatable = false)
    private Semester semester;

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
