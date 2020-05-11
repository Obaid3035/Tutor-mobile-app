package com.example.ustaad.Teacher.Model;

public class Product {

    private String id;
    private String text, subject, timing;

    public Product() {

    }

    public Product(String id, String text, String subject, String timing) {
        this.id = id;
        this.text = text;
        this.subject = subject;
        this.timing = timing;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
