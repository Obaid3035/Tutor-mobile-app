package com.example.ustaad.Teacher.Model;

import com.google.firebase.storage.StorageMetadata;

public class Teacher {
    String teacherid;
    String teachername;
    String teacheremail;
    String teacherpass;
    String teachermobile;
    String teacherfield;
    String teachergender;
    String teachergrade;
    String teacheraddress;
    String teacherimage;
    int teachercheck;
    String device_token;

    public Teacher() {
    }

    public Teacher(int teachercheck, String teacherid, String teachername, String teacheremail, String teacherpass, String teachermobile, String teacherfield, String teachergender, String teachergrade, String teacheraddress, String teacherimage,String device_token) {
        this.teacherid = teacherid;
        this.teachername = teachername;
        this.teacheremail = teacheremail;
        this.teacherpass = teacherpass;
        this.teachermobile = teachermobile;
        this.teacherfield = teacherfield;
        this.teachergender = teachergender;
        this.teachergrade = teachergrade;
        this.teachercheck = teachercheck;
        this.teacheraddress = teacheraddress;
        this.teacherimage = teacherimage;
        this.device_token = device_token;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public String getTeachername() {
        return teachername;
    }

    public String getTeacheremail() {
        return teacheremail;
    }

    public String getTeacherpass() {
        return teacherpass;
    }

    public String getTeachermobile() {
        return teachermobile;
    }

    public String getTeacherfield() {
        return teacherfield;
    }

    public String getTeachergender() {
        return teachergender;
    }

    public String getTeachergrade() {
        return teachergrade;
    }

    public int getTeachercheck() {
        return teachercheck;
    }

    public void setTeachercheck(int teachercheck) {
        this.teachercheck = teachercheck;
    }

    public String getTeacheraddress() { return teacheraddress; }

    public void setTeacheraddress(String teacheraddress) { this.teacheraddress = teacheraddress; }

    public String getTeacherimage() { return teacherimage; }

    public void setTeacherimage(String teacherimage) { this.teacherimage = teacherimage; }

    public String getdevice_token() {
        return device_token;
    }

    public void setdevice_token(String device_token) {
        this.device_token = device_token;
    }
}
