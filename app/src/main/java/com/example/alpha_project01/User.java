package com.example.alpha_project01;

public class User {
    private String mail;
    private String pass;
    public User(String mail,String pass){
        this.mail=mail;
        this.pass=pass;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
