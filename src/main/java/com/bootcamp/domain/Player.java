package com.bootcamp.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public String name;
    public List<Coordinate> spots;
    public int points;

    public boolean IsValid() { return true;}

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
        this.spots = new ArrayList<>();
    }
}
