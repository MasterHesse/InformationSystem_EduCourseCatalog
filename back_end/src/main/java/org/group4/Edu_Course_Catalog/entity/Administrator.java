package org.group4.Edu_Course_Catalog.entity;

import jakarta.persistence.Entity;

@Entity
public class Administrator  extends User{
    private String position;
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
