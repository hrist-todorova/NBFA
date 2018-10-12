package com.bootcamp.domain;

public class InvalidPlayer extends Player {

    public InvalidPlayer(String name, int points) {
        super(name, points);
    }

    public boolean IsValid() { return false;}

}
