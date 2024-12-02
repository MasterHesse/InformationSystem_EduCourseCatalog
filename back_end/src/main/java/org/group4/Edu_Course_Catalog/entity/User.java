package org.group4.Edu_Course_Catalog.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String account;
    private String password;
    private String status;  // "pending", "reject" or "pass"
    private String realName;
    private String gender;
    private String contact;

    // 修改role_id为Role对象的关联
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    // 构造函数
    public User() {
        this.status = "pending"; // 设置默认状态
    }

    // 原有的getter和setter方法保持不变


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    // 修改role相关的getter和setter
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setRole_id(int role_id) {
        // 这个方法可能需要通过RoleRepository来实现
        // 暂时保留为空或抛出异常
        throw new UnsupportedOperationException("Please use setRole(Role role) instead");
    }

}