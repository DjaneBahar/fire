package com.example.fire;

public class User {
    private String user; //Burde måske være singleton. Ville faktisk give god mening.

    public User(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }
}
