package com.example.mapsi3;

public class User {
    public int score;
    public String name;

    public User() {
        score = 0;
        name = "";
    }

    public User(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
