package com.justdoit.sharebook.entity;

/**
 * Created by feimei.zhan on 2015/5/21.
 */
public class UserInfo {
    private String UserName;
    private String Major;
    private String PhoneNum;
    private String Academy;
    private String School;
    private String Sex;
    private String RegistDate;
    private int Id;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getAcademy() {
        return Academy;
    }

    public void setAcademy(String academy) {
        Academy = academy;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getRegistDate() {
        return RegistDate;
    }

    public void setRegistDate(String registDate) {
        RegistDate = registDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
