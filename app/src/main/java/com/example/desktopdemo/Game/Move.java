package com.example.desktopdemo.Game;

import java.util.ArrayList;

public class Move {
    private ArrayList<Integer> coordArray = new ArrayList<>(2);

    public Move(int coord) {
        coordArray.add(coord);
    }

    public Move(int cOne, int cTwo) {
        coordArray.add(cOne);
        coordArray.add(cTwo);
    }

    public ArrayList<Integer> getCoordArray() {
        return coordArray;
    }
}
