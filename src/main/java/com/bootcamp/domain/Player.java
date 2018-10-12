package com.bootcamp.domain;

public class Player {
    public String name;
    public int points;

    public boolean IsValid() { return true;}

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
    }
}
