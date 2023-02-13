package com.janith1024.data;

public enum Model {
    LIGHTWEIGHT("Lightweight", 100.0), MIDDLEWEIGHT("Middleweight", 300.0), CRUISER_WEIGHT("Cruiserweight", 400.0), HEAVYWEIGHT("Heavyweight", 500.0);

    private final String name;
    private final double weightLimit;

    Model(String name, double weightLimit) {
        this.name = name;
        this.weightLimit = weightLimit;
    }

    public String getName() {
        return name;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

}
