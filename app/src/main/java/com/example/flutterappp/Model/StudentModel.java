package com.example.flutterappp.Model;

public class StudentModel {
    String name, email, course, imgUrl;

    StudentModel(){
        // Needed for Firebase
    }
    public StudentModel(String name, String email, String course, String imgUrl) {
        this.name = name;
        this.email = email;
        this.course = course;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
