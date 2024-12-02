package org.group4.Edu_Course_Catalog.entity;

import jakarta.persistence.Entity;

@Entity
public class Teacher extends User {

    private String position;

    private String academicAchievements;

    private String researchDirection;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAcademicAchievements() {
        return academicAchievements;
    }

    public void setAcademicAchievements(String academicAchievements) {
        this.academicAchievements = academicAchievements;
    }

    public String getResearchDirection() {
        return researchDirection;
    }

    public void setResearchDirection(String researchDirection) {
        this.researchDirection = researchDirection;
    }
}
