package com.example.javafinal;

public class Die {
    private int numSides;
    private int startFromOne = 0;
    private int sideUp;
    private String diceName;
    private int tens;

    public Die(int numSides, int startFromOne) {
        this.numSides = numSides;
        this.startFromOne = startFromOne;
        diceName = "d" + Integer.toString(numSides);
        tens = 1;
        sideUp = roll();
    }

    public Die(int numSides, int startFromOne, String diceName) {
        this.numSides = numSides;
        this.startFromOne = startFromOne;
        this.diceName = diceName;
        tens = 1;
        sideUp = roll();
    }

    public Die(int numSides, int startFromOne, String diceName, int tens) {
        this.numSides = numSides;
        this.startFromOne = startFromOne;
        this.diceName = diceName;
        this.tens = tens;
        sideUp = roll();
    }

    public String getSideUp() {
        return Integer.toString(sideUp);
    }

    public int roll() {
        sideUp = ((int)(Math.random() * numSides) + startFromOne) * tens;
        return sideUp;
    }

    public String getNumSides() {
        return Integer.toString(numSides);
    }

    public String getDiceName() {
        return diceName;
    }

    @Override
    public String toString() {
        return getDiceName();
    }
}
