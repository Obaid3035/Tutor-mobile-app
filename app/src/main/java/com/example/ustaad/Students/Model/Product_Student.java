package com.example.ustaad.Students.Model;

public class Product_Student {
    private String studentid;
    private String studenttext, studentsubject, studenttiming;

    public Product_Student() {
    }

    public Product_Student(String studentid, String studenttext, String studentsubject, String studenttiming) {
        this.studentid = studentid;
        this.studenttext = studenttext;
        this.studentsubject = studentsubject;
        this.studenttiming = studenttiming;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getStudenttext() {
        return studenttext;
    }

    public void setStudenttext(String studenttext) {
        this.studenttext = studenttext;
    }

    public String getStudentsubject() {
        return studentsubject;
    }

    public void setStudentsubject(String studentsubject) {
        this.studentsubject = studentsubject;
    }

    public String getStudenttiming() {
        return studenttiming;
    }

    public void setStudenttiming(String studenttiming) {
        this.studenttiming = studenttiming;
    }
}
