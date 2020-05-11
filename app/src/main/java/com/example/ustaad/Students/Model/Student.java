package com.example.ustaad.Students.Model;

public class Student {
   String studentid;
   String studentname;
   String studentemail;
   String studentpass;
   String studentmobile;
   String studentfield;
   String studentgender;
   String studentgrade;
   String studentimage;
   int studentcheck;

    public Student() {
    }


    public Student(int studentcheck, String studentid, String studentname, String studentemail, String studentpass, String studentmobile, String studentfield, String studentgender,String studentgrade,String studentimage) {
        this.studentid = studentid;
        this.studentname = studentname;
        this.studentemail = studentemail;
        this.studentpass = studentpass;
        this.studentmobile = studentmobile;
        this.studentfield = studentfield;
        this.studentgender = studentgender;
        this.studentgrade = studentgrade;
        this.studentcheck = studentcheck;
        this.studentimage = studentimage;
    }

    public String getStudentid() {
        return studentid;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public void setStudentemail(String studentemail) {
        this.studentemail = studentemail;
    }

    public void setStudentpass(String studentpass) {
        this.studentpass = studentpass;
    }

    public void setStudentmobile(String studentmobile) {
        this.studentmobile = studentmobile;
    }

    public void setStudentfield(String studentfield) {
        this.studentfield = studentfield;
    }

    public void setStudentgender(String studentgender) {
        this.studentgender = studentgender;
    }

    public void setStudentgrade(String studentgrade) {
        this.studentgrade = studentgrade;
    }

    public String getStudentemail() {
        return studentemail;
    }

    public String getStudentpass() {
        return studentpass;
    }

    public String getStudentmobile() {
        return studentmobile;
    }

    public String getStudentfield() {
        return studentfield;
    }

    public String getStudentgender() {
        return studentgender;
    }

    public String getStudentgrade() {
        return studentgrade;
    }

    public int getStudentcheck() {
        return studentcheck;
    }

    public void setStudentcheck(int studentcheck) {
        this.studentcheck = studentcheck;
    }


    public String getStudentimage() {
        return studentimage;
    }

    public void setStudentimage(String studentimage) {
        this.studentimage = studentimage;
    }
}


