package com.janith1024.data;

import java.util.List;

public class Drone {
    private String serial;
    private Model model;
    private float batteryCapacity;
    private State state;
    private List<Medication> load;

    public String getSerial() {
        return serial;
    }

    public Drone setSerial(String serial) {
        this.serial = serial;
        return this;
    }

    public Model getModel() {
        return model;
    }

    public Drone setModel(Model model) {
        this.model = model;
        return this;
    }

    public float getBatteryCapacity() {
        return batteryCapacity;
    }

    public Drone setBatteryCapacity(float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    public State getState() {
        return state;
    }

    public Drone setState(State state) {
        this.state = state;
        return this;
    }

    public List<Medication> getLoad() {
        return load;
    }

    public Drone setLoad(List<Medication> load) {
        this.load = load;
        return this;
    }
}
