package com.example.projectwork;

public class holder {

    String Name,Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public holder(String name, String email) {
        Name = name;
        Email=email;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
