package com.example.tutorialspointwidget;

public class User {
    private String name, number;
    private boolean isFav;
    private int profileIcon;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public boolean isFav() {
        return isFav;
    }

    public int getProfileIcon() {
        return profileIcon;
    }

    public User(String name, String number, boolean isFav, int profileIcon) {
        this.name = name;
        this.number = number;
        this.isFav = isFav;
        this.profileIcon = profileIcon;
    }
}
