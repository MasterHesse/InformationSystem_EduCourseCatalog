package org.group4.Edu_Course_Catalog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "semester")
public class Semester {
    @Id
    @GeneratedValue
    private int semesterId;
    private String semesterName;



    public String getSemesterName() {
        return semesterName;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
}
