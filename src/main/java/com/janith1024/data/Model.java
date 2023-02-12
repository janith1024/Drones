package com.janith1024.data;

public enum Model {
    LIGHTWEIGHT("Lightweight", 100.0F), MIDDLEWEIGHT("Middleweight", 200.0F), CRUISER_WEIGHT("Cruiserweight", 300.0F), HEAVYWEIGHT("Heavyweight", 500.0F);

    private final String name;
    private final float weightLimit;

    Model(String name, float weightLimit) {
        this.name = name;
        this.weightLimit = weightLimit;
    }

    public String getName() {
        return name;
    }

    public float getWeightLimit() {
        return weightLimit;
    }
}
