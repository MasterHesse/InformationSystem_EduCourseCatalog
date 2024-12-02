package org.group4.Edu_Course_Catalog.entity;

public class TeacherRegisterRequest {
    private String account;
    private String password;
    private String realName;
    private String gender;
    private String contact;

    private String position;

    private String academicAchievements;

    private String researchDirection;
    // Getters and Setters...


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

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
